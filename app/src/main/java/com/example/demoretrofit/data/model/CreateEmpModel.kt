package com.example.demoretrofit.data.model

data class CreateEmpModel(var name: String, var salary: String, var age: String, var id: Integer)

data class CreateEmpDetails(var status: String, var createEmpModel: CreateEmpModel)