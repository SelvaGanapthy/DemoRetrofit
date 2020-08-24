package com.example.demoretrofit.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleObserver
import com.example.demoretrofit.R
import com.example.demoretrofit.data.api.ApiRequestListener
import com.example.demoretrofit.data.api.ApiServices
import com.example.demoretrofit.data.api.RetrofitConnect
import com.example.demoretrofit.data.fragement.TstFragment
import com.example.demoretrofit.data.model.CreateEmpModel
import com.example.demoretrofit.data.model.Datum
import com.example.demoretrofit.utils.Constants
import com.example.demoretrofit.view.activities.MainActivity
import com.example.demoretrofit.view.interfaces.ShareDataFragment
import retrofit2.Call
import retrofit2.Response
import java.util.*

class MainViewModel : Observable(), ApiRequestListener, LifecycleObserver {

    var status: ObservableField<String> = ObservableField<String>()
    var number = ObservableField<String>()
    private lateinit var apiType: String
    internal var apiServices: ApiServices? = null
    internal var retrofitConnect: RetrofitConnect? = null


    internal var ft: FragmentTransaction? = null
    fun getApiType() = apiType

    init {
        retrofitConnect = RetrofitConnect()
        apiServices = retrofitConnect?.retrofit(Constants.BASE_URL)
    }

    fun onClick(v: View): Unit {
        setChanged()
        notifyObservers(v)
    }


    fun getFragment(context: MainActivity, listener: ShareDataFragment): Unit {
        var bundle = Bundle()
        bundle.putInt("flagFromRightMenu", 1)
        ft = context.supportFragmentManager.beginTransaction()
        var tstFragment: TstFragment = TstFragment.newInstace(context, listener)
        tstFragment.arguments = bundle
        ft?.addToBackStack(null)
        ft?.replace(R.id.frameLayout, tstFragment)
        ft?.commit()
    }


    fun getEmployeeGETInfo() {
        val retroReq: Call<List<Datum>> =
            apiServices?.getEmployeesDetails(Constants.EMPDETAILS_GET_URL) as Call<List<Datum>>
        retrofitConnect?.AddToEnqueue(retroReq, this, Constants.EMPDETAILS_GET_URL)
    }


    override fun onReceiveResult(request: String, response: Response<*>) {

        when (request) {

            Constants.EMPDETAILS_GET_URL -> {
                status.set("GET")
                setChanged()
                notifyObservers(response.body() as Datum)
                Log.i("res", response.isSuccessful.toString())
            }

            Constants.EMPDETAILS_URL -> {
                status.set("POST")
                setChanged()
                notifyObservers(response.body() as CreateEmpModel)
                Log.i("res", response.isSuccessful.toString())
            }

            else -> {
                Log.i("MainActivity", "API condition Failes")
            }

        }

    }

    override fun onReceiveError(request: String, error: String) {
        Log.i("ApiReq", "API Error:" + error)
    }


}