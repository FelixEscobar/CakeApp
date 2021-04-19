package com.piedrasyartesanias.cakeapp.models

import com.google.gson.annotations.SerializedName

data class SignUpModel (
    @SerializedName("name")
    val fullname: String,
    val email: String,
    val password: String
)