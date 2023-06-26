package com.example.pocketpal

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketpal.modelsb.BillModel
import com.example.pocketpal.modelsb.PaidBillModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewBill : AppCompatActivity() {

    private lateinit var tvBillId: TextView
    private lateinit var tvBillAmount: TextView
    private lateinit var tvBillCategory: TextView
    private lateinit var tvBillMonth: TextView
    private lateinit var tvBillDueDate: TextView
    private lateinit var btUpdate: Button
    private lateinit var btDelete: Button
    private lateinit var btPaid: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bill)

        btUpdate = findViewById(R.id.btUpdate)
        btDelete = findViewById(R.id.btDelete)
        btPaid= findViewById(R.id.btPaid)
        initView()
        setValuesToViews()
        btUpdate.setOnClickListener {
            openUpdateBDialog(
                intent.getStringExtra("billId").toString(),
               intent.getStringExtra("category").toString(),
                intent.getStringExtra("month").toString(),
                intent.getStringExtra("dueDate").toString()

            )
        }
        btDelete.setOnClickListener {
            deleteRecordB(
                intent.getStringExtra("billId").toString()
            )
        }

        btPaid.setOnClickListener {
            val billId = intent.getStringExtra("billId")
            addBillToPaidBills(
                intent.getStringExtra("billId").toString()
            )
        }
    }

    private fun deleteRecordB(
        billId: String
    ){
        val dbRef =  FirebaseDatabase.getInstance().getReference("Bills").child(billId)

        val bTask = dbRef.removeValue()

        bTask.addOnSuccessListener {
            Toast.makeText(this, "Bill data deleted succefully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, UpcomingBills::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{err->
            Toast.makeText(this, "dleting error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun openUpdateBDialog(
        billId : String,
        category: String,
        month: String,
        dueDate: String

    ) {
        val bDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val bDialogView = inflater.inflate(R.layout.activity_update_bills, null)

        bDialog.setView(bDialogView)

        val uAmount = bDialogView.findViewById<EditText>(R.id.uAmount)

        val uButton = bDialogView.findViewById<Button>(R.id.uButton)

        val amount = intent.getDoubleExtra("amount", 0.0)
        uAmount.setText(amount.toString())


        val balertDialog = bDialog.create()
        balertDialog.show()

        uButton.setOnClickListener {
            updateBillData(
                billId,
                uAmount.text.toString().toDouble(),
                category,
                month,
                dueDate

                )
            Toast.makeText(applicationContext, "Bill data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to text view
            tvBillAmount.text = uAmount.text.toString().toDouble().toString()

            balertDialog.dismiss()
        }

    }

    private fun updateBillData(
        billId:String,
        amount:Double,
        category: String,
        month:String,
        dueDate:String

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Bills").child(billId)
        val billinfo = BillModel(billId, amount, category, month, dueDate)
        dbRef.setValue(billinfo)
    }


    private fun initView() {
        tvBillId = findViewById(R.id.tvBillId)
        tvBillAmount = findViewById(R.id.tvBillAmount)
        tvBillCategory=findViewById(R.id.tvBillcategory)
        tvBillMonth = findViewById(R.id.tvBillMonth)
        tvBillDueDate = findViewById(R.id.tvBillDueDate)

    }
    private fun setValuesToViews(){
        tvBillId.text = intent.getStringExtra("billId")
        val amount = intent.getDoubleExtra("amount", 0.0)
        tvBillAmount.text = amount.toString()
        tvBillCategory.text=intent.getStringExtra("category")
        tvBillMonth.text=intent.getStringExtra("month")
        tvBillDueDate.text=intent.getStringExtra("dueDate")
    }

    private fun addBillToPaidBills(billId: String) {
        val dbBillsRef = FirebaseDatabase.getInstance().getReference("Bills").child(billId)
        val dbPaidBillsRef = FirebaseDatabase.getInstance().getReference("PaidBills").child(billId)

        dbBillsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val billModel = dataSnapshot.getValue(BillModel::class.java)
                    if (billModel != null) {
                        val paidBill = PaidBillModel(
                            billModel.billId,
                            billModel.amount,
                            billModel.category,
                            billModel.month,
                            billModel.dueDate
                        )
                        dbPaidBillsRef.setValue(paidBill)
                        dbBillsRef.removeValue()
                        Toast.makeText(
                            applicationContext,
                            "Bill paid successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "onCancelled: $error")
            }
        })
    }

}