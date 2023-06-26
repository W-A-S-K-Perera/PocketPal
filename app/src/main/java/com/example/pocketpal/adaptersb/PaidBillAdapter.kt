package com.example.pocketpal.adaptersb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketpal.R

import com.example.pocketpal.modelsb.PaidBillModel

class PaidBillAdapter (private val pbillList: ArrayList<PaidBillModel>):
    RecyclerView.Adapter<PaidBillAdapter.ViewHolder>(){

    private lateinit var bListener: onpBillClickListner

    interface onpBillClickListner{
        fun onBillClick(position: Int)
    }

    fun setOnBillClickListner(clickListner: onpBillClickListner){
        bListener = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.paidbill_list,parent,false)
        return ViewHolder(itemView,bListener)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentBill = pbillList[position]
        holder.tvpBillcategory.text= currentBill.category




    }

    override fun getItemCount(): Int {
        return pbillList.size
    }

    class ViewHolder(itemView: View,clickListner: onpBillClickListner) :
        RecyclerView.ViewHolder(itemView,) {

        val tvpBillcategory: TextView = itemView.findViewById(R.id.tvppBillcategory)

        init {
            itemView.setOnClickListener{
                clickListner.onBillClick(adapterPosition)
            }
        }

    }

}
