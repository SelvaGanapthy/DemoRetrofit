package com.example.demoretrofit.data.model

data class Data(
    var id: String,
    var employee_name: String,
    var employee_salary: String,
    var employee_age: String,
    var profile_image: String
)

data class Datum(var status: String, var data: List<Data>)
