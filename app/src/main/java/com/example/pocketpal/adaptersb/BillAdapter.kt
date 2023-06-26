package com.example.pocketpal.adaptersb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketpal.R
import com.example.pocketpal.modelsb.BillModel
import kotlin.collections.ArrayList



class BillAdapter (private val billList: ArrayList<BillModel>):
    RecyclerView.Adapter<BillAdapter.ViewHolder>(){

    private lateinit var bListener: onBillClickListner

    interface onBillClickListner{
        fun onBillClick(position: Int)
    }

    fun setOnBillClickListner(clickListner:  onBillClickListner){
        bListener = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bill_list,parent,false)
        return ViewHolder(itemView,bListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = billList[position]
        holder.tvBillcategory.text = bill.category
       // val currentDate = Calendar.getInstance()

       // val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
       // val dueDate = Calendar.getInstance()
       // dueDate.time = dateFormat.parse(bill.dueDate.toString())

       // if (dueDate.timeInMillis > currentDate.timeInMillis) {

           // holder.itemView.visibility = View.VISIBLE
       // } else {
        //    holder.itemView.visibility = View.GONE
     //   }


    }

    override fun getItemCount(): Int {
        return billList.size
    }



    class ViewHolder(itemView: View, clickListner: onBillClickListner) : RecyclerView.ViewHolder(itemView) {

        val tvBillcategory : TextView = itemView.findViewById(R.id.tvppBillcategory)

        init {
            itemView.setOnClickListener{
                clickListner.onBillClick(adapterPosition)
            }
        }
    }

}


