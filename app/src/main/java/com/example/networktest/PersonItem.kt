package com.example.networktest

import android.os.Parcel
import android.os.Parcelable

class PersonItem(val id: Int, val cover_url: String?, val full_name: String?, val description: String?, val followers: Int, val email: String?) :Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readInt(),parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cover_url)
        parcel.writeString(full_name)
        parcel.writeString(description)
        parcel.writeInt(followers)
        parcel.writeString(email)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonItem> {
        override fun createFromParcel(parcel: Parcel): PersonItem {
            return PersonItem(parcel)
        }

        override fun newArray(size: Int): Array<PersonItem?> {
            return arrayOfNulls(size)
        }
    }
}