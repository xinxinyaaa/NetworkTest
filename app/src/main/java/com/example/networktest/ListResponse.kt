package com.example.networktest

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListResponse(var id: Int,
                        @SerializedName("full_name") var name: String, var followers: Int) :Parcelable {



    constructor(parcel: Parcel) : this(parcel.readInt(),parcel.toString(),parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(followers)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListResponse> {
        override fun createFromParcel(parcel: Parcel): ListResponse {
            return ListResponse(parcel)
        }

        override fun newArray(size: Int): Array<ListResponse?> {
            return arrayOfNulls(size)
        }
    }
}
