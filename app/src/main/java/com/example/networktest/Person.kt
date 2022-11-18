package com.example.networktest

import android.os.Parcel
import android.os.Parcelable

class Person(val id: Int, val full_name: String?, val followers: Int) :Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(),parcel.readString(),parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(full_name)
        parcel.writeInt(followers)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
