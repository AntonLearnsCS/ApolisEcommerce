package com.example.ecommerceproject.data

import android.os.Parcel
import android.os.Parcelable

data class RegisterUser(val full_name : String, val mobile_no : String,
                        val email_id: String, val password: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(full_name)
        parcel.writeString(mobile_no)
        parcel.writeString(email_id)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterUser> {
        override fun createFromParcel(parcel: Parcel): RegisterUser {
            return RegisterUser(parcel)
        }

        override fun newArray(size: Int): Array<RegisterUser?> {
            return arrayOfNulls(size)
        }
    }
}
