package com.example.networktest

import retrofit2.Call
import retrofit2.http.GET

interface PersonService {
    @GET("android10/Sample-Data/master/Android-CleanArchitecture/users.json")
    fun getPersonData():Call<List<Person>>
}