package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toImageRole
import com.compfest16.sea_salon.features.domain.dummy.ImageDummy
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.storage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ImageRepositoryImpl: ImageRepository {
    val db  = Firebase.storage.reference
    val metadata = storageMetadata {
        contentType = "image/jpeg"
    }
    override suspend fun GetImageById(affiliateID: String, role: String): Flow<List<ImageModel>> {
        return flow {
            val folderRef = db.child("$role/$affiliateID")
            try {
                val listResult = folderRef.listAll().await()
                val images = listResult.items.map { item ->
                    val downloadUrl = item.downloadUrl.await()

                    ImageModel(
                        imageID = UUID.randomUUID().toString(),
                        affiliateID = affiliateID,
                        src = downloadUrl,
                        role = role.toImageRole()
                    )
                }
                Log.d("Image", "Get Image: $images")
                emit(images)
            } catch (e: Exception) {
                Log.e("Image", "Failed to fetch images from Firebase Storage", e)
                throw e
            }
        }
    }


    override suspend fun PostImage(image: ImageModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.child("${image.getRole()}/${image.affiliateID}/${image.imageID}").putFile(image.src, metadata)
                        .addOnProgressListener {
                            val progress = "Uploading " + (100 * it.bytesTransferred / it.totalByteCount).toInt().toString() + "%"
                            Log.d("Image", "Upload Image: $progress%")
                        }
                        .addOnSuccessListener {
                            continuation.resume("Image Uploaded Successfully")
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                emit(e.message.toString())
                Log.d("Image", e.message.toString())
            }
        }
    }


    override suspend fun DeleteImage(imageID: String, affiliateID: String, role: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.child("$role/$affiliateID/$imageID").delete()
                        .addOnSuccessListener {
                            continuation.resume("Image Deleted Successfully")
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                emit(e.message.toString())
                Log.d("Image", e.message.toString())
            }
        }
    }
}