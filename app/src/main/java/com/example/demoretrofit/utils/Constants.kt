package com.example.demoretrofit.utils

class Constants {

    companion object {
        val BASE_URL: String = "http://dummy.restapiexample.com/api/v1/"
        val EMPDETAILS_URL: String = "create"
        val EMPDETAILS_GET_URL: String = "employees"

        /*For the Retorfit  N/W purpose*/
        var NETWORK_ERROR: String = "Please check your network connection"
        val TIMEOUTCONNECTION: Long = 60000
        val TIMEOUTSOCKET: Long = 60000
        val CHAT_SOCKET_TIMEOUT: Long = 30000

    }


}