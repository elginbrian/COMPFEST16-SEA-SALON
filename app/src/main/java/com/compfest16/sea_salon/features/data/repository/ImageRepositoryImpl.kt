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

class ImageRepositoryImpl: ImageRepository {
    val db  = Firebase.storage.reference
    val metadata = storageMetadata {
        contentType = "image/jpeg"
    }
    override suspend fun GetImageById(id: String, affiliateID: String, role: String): Flow<ImageModel> {
        return flow {
            try {
                var image = ImageDummy.notFound
                db.child("$role/$id").downloadUrl.addOnSuccessListener {
                    image = ImageModel(
                        imageID = id,
                        affiliateID = affiliateID,
                        src = it,
                        role = role.toImageRole()
                    )
                    Log.d("Image", image.toString())
                }.addOnFailureListener {
                    Log.d("Image", it.message.toString())
                }
            } catch (e: Exception){
                Log.d("Image", e.message.toString())
                emit(ImageDummy.notFound)
                return@flow
            }
        }
    }

    override suspend fun GetImagesByAffiliateId(id: String, role: String): Flow<List<ImageModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun PostImage(image: ImageModel): Flow<String> {
        return flow {
            try {
                var messege = ""
                db.child("${image.getRole()}/${image.affiliateID}/${image.imageID}").putFile(image.src, metadata).addOnProgressListener {
                    messege = "Uploading " + (100 * it.bytesTransferred / it.totalByteCount).toInt().toString() + "%"
                }.addOnSuccessListener {
                    messege = "Image Uploaded Successfully"
                }.addOnFailureListener{
                    messege = it.message.toString()
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Image", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun DeleteImage(imageID: String, affiliateID: String, role: String): Flow<String> {
        return flow {
            try {
                var messege = ""
                db.child("$role/$affiliateID/$imageID").delete().addOnSuccessListener {
                    messege = "Image Deleted Successfully"
                }.addOnFailureListener{
                    messege = it.message.toString()
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Image", e.message.toString())
                return@flow
            }
        }
    }
}