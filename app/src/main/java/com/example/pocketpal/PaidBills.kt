package com.example.pocketpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketpal.adaptersb.PaidBillAdapter
import com.example.pocketpal.modelsb.PaidBillModel
import com.google.firebase.database.*

class PaidBills : AppCompatActivity() {


    private lateinit var pbillRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var pbillList: ArrayList<PaidBillModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var bpDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paid_bills)



        pbillRecyclerView = findViewById(R.id.rvpBill)

        pbillRecyclerView.layoutManager = LinearLayoutManager(this)
        pbillRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)




        pbillList = arrayListOf<PaidBillModel>()

        pgetBillData()
    }



    private fun pgetBillData() {
        pbillRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("PaidBills")

       dbRef.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               pbillList.clear()
               var totalAmount = 0.00
               if(snapshot.exists()){
                   for(pbillSanp in snapshot.children){
                       val pBillData = pbillSanp.getValue(PaidBillModel::class.java)
                       pbillList.add(pBillData!!)

                       totalAmount += pBillData?.amount ?: 0.00
                   }
                   val pBAdapter = PaidBillAdapter(pbillList)
                   pbillRecyclerView.adapter= pBAdapter



                   pBAdapter.setOnBillClickListner(object : PaidBillAdapter.onpBillClickListner {
                       override fun onBillClick(position: Int) {
                           val intent = Intent(this@PaidBills, ViewPaidBills::class.java)
                           //put extra data
                           intent.putExtra("billId", pbillList[position].billId)
                           intent.putExtra("amount", pbillList[position].amount)
                           intent.putExtra("category",pbillList[position].category)
                           intent.putExtra("month", pbillList[position].month)
                           intent.putExtra("dueDate", pbillList[position].dueDate)
                           startActivity(intent)
                       }


               })

                   val textViewTotalAmount = findViewById<TextView>(R.id.textViewpTotalAmount)
                   textViewTotalAmount.text = "Rs.$totalAmount"

                   pbillRecyclerView.visibility = View.VISIBLE
                   tvLoadingData.visibility= View.GONE
               }
           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })
    }


}