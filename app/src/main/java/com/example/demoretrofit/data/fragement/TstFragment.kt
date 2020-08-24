package com.example.demoretrofit.data.fragement;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.demoretrofit.R
import com.example.demoretrofit.view.activities.MainActivity
import com.example.demoretrofit.view.interfaces.ShareDataFragment
import javax.xml.transform.ErrorListener

class TstFragment() : Fragment() {

    var view: LinearLayout? = null
    var contex: Context? = null
    var tvFragment: TextView? = null
    var listener: ShareDataFragment? = null

    constructor(mainActivity: MainActivity, listener: ShareDataFragment) : this() {
        this.contex = mainActivity
        this.listener = listener
    }

    companion object {

        fun newInstace(mainActivity: MainActivity, listener: ShareDataFragment): TstFragment {
            return TstFragment(mainActivity, listener)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        view = layoutInflater.inflate(R.layout.temp_fragment, container, false) as LinearLayout?

        tvFragment = view?.findViewById(R.id.tvFragment) as TextView

        tvFragment?.setOnClickListener { v ->
            listener?.shareData(5)

        }


//        var bundle = Bundle
        Toast.makeText(
            activity?.applicationContext,
            arguments!!.getInt("flagFromRightMenu").toString(), Toast.LENGTH_SHORT
        ).show()
        return view
    }


}
