package com.example.demoretrofit.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.demoretrofit.R
import com.example.demoretrofit.data.model.CreateEmpModel
import com.example.demoretrofit.data.model.Data
import com.example.demoretrofit.data.model.Datum
import com.example.demoretrofit.databinding.ActivityMainBinding
import com.example.demoretrofit.view.adapter.DataAdapter
import com.example.demoretrofit.view.interfaces.OnItemClickListener
import com.example.demoretrofit.view.interfaces.ShareDataFragment
import com.example.demoretrofit.viewmodel.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer, LifecycleOwner, OnItemClickListener, ShareDataFragment {
    override fun shareData(data: Int) {
        Toast.makeText(this, "" + data, Toast.LENGTH_SHORT).show()
    }

    private lateinit var lifecycleRegistry: LifecycleRegistry
    lateinit var activityBinding: ActivityMainBinding
    internal var viewModel: MainViewModel = MainViewModel()
    internal var ft: FragmentTransaction? = null
    lateinit var adapter: DataAdapter
//    var ll: Stack<String> = Stack()

    companion object {
        var data: Datum? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityBinding.viewmodel = viewModel
        viewModel.addObserver(this)
        lifecycle.addObserver(viewModel)
        viewModel.getFragment(this@MainActivity, this)

//        ll.push("1")
//        ll.push("2")
//        ll.push("3")
//        ll.push("4")
//        ll.push("5")
//        ll.push("6")

//        var iterator: Iterator<String> = ll.iterator()
//        var i = 0
//        while (i < ll.size) {
//            println(ll.peek())
//           i= i+1
//        }


        // viewModel.getFragment(this@MainActivity)
        //  viewModel.number.set("343343")
        var data: ArrayList<Data> = ArrayList()
        for (i in 0 until 5)
            data.add(
                Data("" + i, "selva" + i, "22", "25", "44")
            )


        activityBinding.rv.setHasFixedSize(true)
        adapter = DataAdapter(this@MainActivity, Datum("true", data), this)
        activityBinding.rv.adapter = adapter
//        DataAdapter(this@MainActivity)
//        activityBinding.rv.adapter = adapter
        /*setContentView(R.layout.activity_main)
        var bundle = Bundle()
        bundle.putInt("flagFromRightMenu", 1)
        ft = supportFragmentManager.beginTransaction()
        var tstFragment: TstFragment = TstFragment()
        tstFragment.arguments = bundle
        ft?.addToBackStack(null)
        ft?.replace(R.id.frameLayout, tstFragment)
        ft?.commit()*/
    }


    override fun update(p0: Observable?, p1: Any?) {

        if (p1 is View) {

            when (p1.id) {

                R.id.btnOnclick -> {
                    activityBinding.btnOnclick.visibility = View.GONE
                    viewModel.getEmployeeGETInfo()
                }

            }

        } else if (p1 is Datum) {
            activityBinding.frameLayout.visibility = View.VISIBLE
            activityBinding.rv.setHasFixedSize(true)
            adapter = DataAdapter(this@MainActivity, p1, this)
            activityBinding.rv.adapter = adapter

        } else if (p1 is CreateEmpModel) {

            Log.i("MainActivity", "API response Id:" + p1.age)

        } else {
            Log.i("MainActivity", "API response Error")
        }


    }


    override fun onItemClick(pos: Int, item: View) {

        when (item.id) {
            R.id.btnAdapter -> {
                Toast.makeText(this, "btnAdapter", Toast.LENGTH_SHORT).show()
            }
        }

    }


}
