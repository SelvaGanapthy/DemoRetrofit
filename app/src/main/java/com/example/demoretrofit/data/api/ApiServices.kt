package com.example.demoretrofit.data.api

import com.example.demoretrofit.data.model.CreateEmpDetails
import com.example.demoretrofit.data.model.Datum
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiServices {

    @GET
    fun getEmployeesDetails(@Url baseurl: String): Call<Datum>

    @POST
    fun getCreateDetails(@Url url: String): Call<CreateEmpDetails>

}