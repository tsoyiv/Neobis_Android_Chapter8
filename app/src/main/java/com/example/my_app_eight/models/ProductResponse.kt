package com.example.my_app_eight.models

import android.os.Parcel
import android.os.Parcelable

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val photo: String,
    val owner: Int,
    val like_count: String
) : Parcelable {
    // Implement the Parcelable interface methods here

    // For example:
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(price)
        dest.writeString(description)
        dest.writeString(photo)
        dest.writeInt(owner)
        dest.writeString(like_count)
    }

    companion object CREATOR : Parcelable.Creator<ProductResponse> {
        override fun createFromParcel(parcel: Parcel): ProductResponse {
            return ProductResponse(
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readInt(),
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<ProductResponse?> {
            return arrayOfNulls(size)
        }
    }
}

