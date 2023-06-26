package com.example.pocketpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketpal.modelsb.BillModel
import com.example.pocketpal.modelsb.PaidBillModel
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {


    lateinit var textViewTotalAmountPaid: TextView
    lateinit var textViewTotalAmountUpcoming:TextView
    private lateinit var btnAdd: Button
    private lateinit var btnUpcoming: Button
    private lateinit var btnPaid: Button
    lateinit var dbRefBills: DatabaseReference
    lateinit var dbRefPaidBills: DatabaseReference

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        dbRefBills = FirebaseDatabase.getInstance().getReference("Bills")
        dbRefPaidBills = FirebaseDatabase.getInstance().getReference("PaidBills")

        btnAdd = findViewById(R.id.btnAdd)
        btnUpcoming= findViewById(R.id.btnUpcoming)
        btnPaid = findViewById(R.id.btnPaid)
        val textViewTotalAmountUpcoming = findViewById<TextView>(R.id.textViewTotalAmountUpcoming)
        val textViewTotalAmountPaid = findViewById<TextView>(R.id.textViewTotalAmountPaid)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddBills::class.java)
            startActivity(intent)
        }

        btnUpcoming.setOnClickListener {
            val intent = Intent(this, UpcomingBills::class.java)
            startActivity(intent)
        }
        btnPaid.setOnClickListener {
            val intent = Intent(this, PaidBills::class.java)
            startActivity(intent)
        }

        // Calculate the total amount of upcoming bills and display it in the UI
        dbRefBills.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalAmount = 0.00 // Initialize the total amount to 0
                if(snapshot.exists()){
                    for(billSnap in snapshot.children){
                        val billData = billSnap.getValue(BillModel::class.java)
                        totalAmount += billData?.amount ?: 0.00  // Add the amount of each bill to the total amount
                    }
                    textViewTotalAmountUpcoming.text = "Rs $totalAmount"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // Calculate the total amount of paid bills and display it in the UI
        dbRefPaidBills.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var totalAmount = 0.00 // Initialize the total amount to 0
                if(snapshot.exists()){
                    for(billSnap in snapshot.children){
                        val billData = billSnap.getValue(PaidBillModel::class.java)
                        totalAmount += billData?.amount ?: 0.00  // Add the amount of each bill to the total amount
                    }
                    textViewTotalAmountPaid.text = "Rs $totalAmount"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }




}