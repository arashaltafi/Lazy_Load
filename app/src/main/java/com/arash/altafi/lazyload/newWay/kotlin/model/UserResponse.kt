package com.arash.altafi.lazyload.newWay.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class UserResponse : Parcelable {

    @Parcelize
    class NewsData : Parcelable {

        @Parcelize
        data class UserData(
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("avatar")
            val avatar: String,
            @SerializedName("family")
            val family: String
        ) : Parcelable

    }

}