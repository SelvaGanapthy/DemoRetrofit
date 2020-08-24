package com.example.demoretrofit.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoretrofit.R
import com.example.demoretrofit.data.model.Data
import com.example.demoretrofit.data.model.Datum
import com.example.demoretrofit.databinding.DataAdapterBinding
import com.example.demoretrofit.view.interfaces.OnItemClickListener

class DataAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var layoutInflater: LayoutInflater
    var mRecyclerView: RecyclerView? = null
    var view: View? = null
    lateinit var dataList: Datum
    lateinit var listener: OnItemClickListener

    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0) {
            return 0
        } else
            return 1

    }


    constructor(context: Context, dataList: Datum, listener: OnItemClickListener) : this(context) {
        this.dataList = dataList
        this.listener = listener
    }

    override fun getItemId(position: Int): Long = super.getItemId(position)


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        Log.i("rv", "rv1")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (::layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return when (viewType) {

            0 -> ViewHolder1(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.data_adapter,
                    parent,
                    false
                )
            )


            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.data_adapter1, parent, false)
                return ViewHolder2(view!!)
            }

            else -> null!!
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

            0 -> {

                val model: Data = dataList.data[position]
                val holde = holder as ViewHolder1
//                holde.tvadapter?.setText(model.employee_name)
                Toast.makeText(context, model.employee_name, Toast.LENGTH_SHORT).show()
            }

            1 -> {
                (holder as ViewHolder2).tvadapter?.setText("1")
            }

        }

        if (holder is ViewHolder1) {
            holder.view.model = dataList.data[position]
            holder.view.clkListener = listener
        }


    }


    override fun getItemCount(): Int = dataList.data.size


    class ViewHolder1(val view: DataAdapterBinding) : RecyclerView.ViewHolder(view.root)


    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvadapter: TextView? = null

        init {

            try {
                tvadapter = itemView.findViewById<View>(R.id.tvadapter) as TextView
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}