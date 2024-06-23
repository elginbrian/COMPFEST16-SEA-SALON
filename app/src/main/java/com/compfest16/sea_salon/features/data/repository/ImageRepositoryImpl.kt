package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toImageRole
import com.compfest16.sea_salon.features.domain.dummy.ImageDummy
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ImageRepositoryImpl: ImageRepository {
    val db  = Firebase.storage.reference
    val metadata = storageMetadata {
        contentType = "image/jpeg"
    }
    override suspend fun GetImageById(id: String, affiliateID: String, role: String): Flow<ImageModel> {
        return flow {
            try {
                val image = suspendCancellableCoroutine<ImageModel> { continuation ->
                    db.child("$role/$id").downloadUrl.addOnSuccessListener { uri ->
                        val imageModel = ImageModel(
                            imageID = id,
                            affiliateID = affiliateID,
                            src = uri,
                            role = role.toImageRole()
                        )
                        Log.d("Image", imageModel.toString())
                        continuation.resume(imageModel)
                    }.addOnFailureListener { exception ->
                        Log.e("Image", exception.message.toString(), exception)
                        continuation.resume(ImageDummy.notFound)
                    }
                }
                emit(image)
            } catch (e: Exception) {
                Log.e("Image", e.message.toString(), e)
                emit(ImageDummy.notFound)
            }
        }
    }


    override suspend fun GetImagesByAffiliateId(id: String, role: String): Flow<List<ImageModel>> {
        TODO("Not yet implemented")
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