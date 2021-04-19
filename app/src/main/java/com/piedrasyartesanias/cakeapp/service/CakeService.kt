package com.piedrasyartesanias.cakeapp.service

import com.piedrasyartesanias.cakeapp.models.LoginModel
import com.piedrasyartesanias.cakeapp.models.LoginResponseModel
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import com.piedrasyartesanias.cakeapp.models.SignUpModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CakeService {
    @POST("users/login")
    suspend fun login(@Body loginModel: LoginModel) : Response<LoginResponseModel>

    @POST("users/signup")
    suspend fun signUp(@Body signUpModel: SignUpModel) : Response<LoginResponseModel>

    @GET("products")
    suspend fun getAllProducts() : Response<List<ProductResponse>>

    @POST("users/autologin")
    suspend fun autoLogin(@Header("Authorization") token:String) :Response<Unit>

    @GET("products")
    suspend fun getDrinks(@Query("category")category: Int): Response<List<ProductResponse>>
}
