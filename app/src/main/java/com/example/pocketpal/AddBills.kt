package com.example.pocketpal

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketpal.modelsb.BillModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class AddBills : AppCompatActivity() {

    private lateinit var selectCategory: TextView
    private lateinit var  dialog: BottomSheetDialog
    private lateinit var etAmount: EditText
    private lateinit var etMonth: EditText
    private lateinit var dueDateText: TextView
    private lateinit var btnSaveBill: Button

    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bills)

        selectCategory = findViewById(R.id.selectCategory)
        dueDateText=findViewById(R.id.dueDateText)
        etAmount = findViewById(R.id.etAmount)
        etMonth = findViewById(R.id.etMonth)
       btnSaveBill=findViewById(R.id.btnSaveBill)
        //showCategoryBottomSheet()
       selectCategory.setOnClickListener {
            showCategoryBottomSheet()
        }
        dueDateText.setOnClickListener {
            showDatePickerDialog()
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        btnSaveBill.setOnClickListener {
            saveBill()
        }


    }

    private fun saveBill() {
        //getting values

        val amountString = etAmount.text.toString()
        val amount = amountString.toDouble()
        val category = selectCategory.text.toString()
        val month = etMonth.text.toString()

        val dueDate = dueDateText.text.toString()
       // val dueDate = dueDateText.text.




       if(category.isEmpty()){
           selectCategory.error = "Please select category"
       }
        if(month.isEmpty()){
            etMonth.error = "Please enter month"
        }
       if(dueDate.isEmpty()){
           dueDateText.error = "Please enter month"
       }

        //generate id
        val billId = dbRef.push().key!!

        val bill = BillModel(billId, amount, category, month, dueDate)

        dbRef.child(billId).setValue(bill)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etAmount.text.clear()
                etMonth.text.clear()
                selectCategory.text=""
                dueDateText.text=""



            }.addOnFailureListener{err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun showCategoryBottomSheet() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        var selectedCategory = ""

        view.findViewById<RadioGroup>(R.id.category_radio_group)
            .setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.category1_radio_button -> selectedCategory = "Electricity"
                    R.id.category2_radio_button -> selectedCategory = "Telephone"
                    R.id.category3_radio_button -> selectedCategory = "Water"
                    R.id.category4_radio_button -> selectedCategory = "Mobile Phone"
                    R.id.category5_radio_button -> selectedCategory = "Internet"
                }
            }

        view.findViewById<Button>(R.id.ok_button)
            .setOnClickListener {
                // Update the text view with the selected category
                selectCategory.text = selectedCategory

                // Dismiss the dialog
                dialog.dismiss()
            }

        dialog.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                dueDateText.text = selectedDate
            }, year, month, day)

        datePickerDialog.setOnCancelListener {

        }

        datePickerDialog.show()
    }


}