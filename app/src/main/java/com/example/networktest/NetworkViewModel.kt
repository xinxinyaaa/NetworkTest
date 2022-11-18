package com.example.networktest

import androidx.lifecycle.ViewModel

class NetworkViewModel: ViewModel() {
    var id = ""
    var name = ""
    var followers = 0

    val contactList = ArrayList<Person>()
}