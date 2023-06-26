package com.example.pocketpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketpal.adaptersb.BillAdapter
import com.example.pocketpal.modelsb.BillModel
import com.google.firebase.database.*

class UpcomingBills : AppCompatActivity() {

    private lateinit var billRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var billList: ArrayList<BillModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_bills)

        billRecyclerView = findViewById(R.id.rvBill)
        billRecyclerView.layoutManager = LinearLayoutManager(this)
        billRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        billList = arrayListOf<BillModel>()

        getBillData()
    }

    private fun getBillData() {
        billRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billList.clear()
                var totalAmount = 0.00 // Initialize the total amount to 0
                if(snapshot.exists()){
                    for(billSnap in snapshot.children){
                        val billData = billSnap.getValue(BillModel::class.java)
                        billList.add(billData!!)



                        totalAmount += billData?.amount ?: 0.00 // Add the amount of each bill to the total amount
                    }
                    val bAdapter = BillAdapter(billList)
                    billRecyclerView.adapter = bAdapter

                    bAdapter.setOnBillClickListner(object : BillAdapter.onBillClickListner {
                        override fun onBillClick(position: Int) {
                            val intent = Intent(this@UpcomingBills, ViewBill::class.java)
                            //put extra data
                            intent.putExtra("billId", billList[position].billId)
                            intent.putExtra("amount", billList[position].amount)
                            intent.putExtra("category",billList[position].category)
                            intent.putExtra("month", billList[position].month)
                            intent.putExtra("dueDate", billList[position].dueDate)
                            startActivity(intent)
                        }

                    })

                    // Display the total amount in the UI
                    val textViewTotalAmount = findViewById<TextView>(R.id.textViewTotalAmount)
                    textViewTotalAmount.text = "Rs.$totalAmount"

                    billRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}