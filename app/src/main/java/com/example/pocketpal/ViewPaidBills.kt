package com.example.pocketpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ViewPaidBills : AppCompatActivity() {
    private lateinit var ptvBillId: TextView
    private lateinit var ptvBillAmount: TextView
    private lateinit var ptvBillCategory: TextView
    private lateinit var ptvBillMonth: TextView
    private lateinit var ptvBillDueDate: TextView

    private lateinit var pbtDelete: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_paid_bills)


       pbtDelete = findViewById(R.id.btpDelete)

        pbtDelete.setOnClickListener {
            deleteRecordBP(
                intent.getStringExtra("billId").toString()
            )
        }

        initView()
        setValuesToViews()
    }

    private fun deleteRecordBP(
        billId: String
    ){
        val dbRef =  FirebaseDatabase.getInstance().getReference("PaidBills").child(billId)

        val bTask = dbRef.removeValue()

        bTask.addOnSuccessListener {
            Toast.makeText(this, "Bill data deleted succefully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, PaidBills::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{err->
            Toast.makeText(this, "dleting error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setValuesToViews() {
        ptvBillId.text = intent.getStringExtra("billId")
        val amount = intent.getDoubleExtra("amount", 0.0)
        ptvBillAmount.text = amount.toString()
        ptvBillCategory.text=intent.getStringExtra("category")
        ptvBillMonth.text=intent.getStringExtra("month")
        ptvBillDueDate.text=intent.getStringExtra("dueDate")
    }

    private fun initView() {
        ptvBillId = findViewById(R.id.tvpBillId)
        ptvBillAmount = findViewById(R.id.tvpBillAmount)
        ptvBillCategory=findViewById(R.id.tvpBillcategory)
        ptvBillMonth = findViewById(R.id.tvpBillMonth)
        ptvBillDueDate = findViewById(R.id.tvpBillDueDate)
    }
}