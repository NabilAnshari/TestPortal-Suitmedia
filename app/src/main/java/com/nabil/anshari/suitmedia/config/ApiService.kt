package com.nabil.anshari.suitmedia.config

import com.nabil.anshari.suitmedia.response.ResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/users")
    fun getUser(
        @QueryMap parameters: HashMap<String, String>
    ): Call<ResponseApi>
}