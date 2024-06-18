package com.compfest16.sea_salon.features.data.mapper

import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.google.firebase.firestore.QueryDocumentSnapshot

fun ReviewModel.toHashMap(): HashMap<String, Any>{
    var map = HashMap<String, Any>()
    map = hashMapOf(
        "review_id" to this.reviewID,
        "user_id" to this.userID,
        "branch_id" to this.branchID,
        "star" to this.star,
        "comment" to this.comment
    )
    return map
}

fun QueryDocumentSnapshot.toReviewModel(): ReviewModel{
    return ReviewModel(
        reviewID = this["review_id"] as String,
        userID = this["user_id"] as String,
        branchID = this["branch_id"] as String,
        star = this["star"] as Int,
        comment = this["comment"] as String
    )
}