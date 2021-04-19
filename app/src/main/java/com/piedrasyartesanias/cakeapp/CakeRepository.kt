package com.piedrasyartesanias.cakeapp

import com.piedrasyartesanias.cakeapp.models.LoginModel
import com.piedrasyartesanias.cakeapp.models.LoginResponseModel
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import com.piedrasyartesanias.cakeapp.models.SignUpModel
import com.piedrasyartesanias.cakeapp.models.SignUpResponseModel
import com.piedrasyartesanias.cakeapp.service.CakeService
import com.piedrasyartesanias.cakeapp.service.ServiceFactory

class CakeRepository {
    private var cakeService: CakeService

    init {
        val serviceFactory = ServiceFactory()
        cakeService = serviceFactory.getInstanceCakeService()
    }

    suspend fun login(loginModel: LoginModel): LoginResponseModel {
        val response = cakeService.login(loginModel)
        if (response.isSuccessful){
            return response.body()!!
        }else{
            throw Exception(response.message())
        }
    }

    suspend fun signUp(signUpModel: SignUpModel): LoginResponseModel {
        val response = cakeService.signUp(signUpModel)
        if (response.isSuccessful){
            return response.body()!!
        }else{
            throw Exception(response.message())
        }
    }

    suspend fun getAllProducts(): List<ProductResponse> {
        val response = cakeService.getAllProducts()
        if (response.isSuccessful){
            return response.body()!!
        }else{
            throw Exception(response.message())
        }
    }

    suspend fun autoLogin(token: String) : Boolean {
        val response = cakeService.autoLogin("Bearer $token")
        return response.isSuccessful
            }

    suspend fun getDrinks(category: Int): List<ProductResponse> {
        val response = cakeService.getDrinks(category)
        if (response.isSuccessful){
            return response.body()!!
        }else{
            throw Exception(response.message())
        }
    }
}