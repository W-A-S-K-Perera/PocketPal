package com.example.pocketpal

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketpal.adaptersb.BillAdapter
import com.example.pocketpal.modelsb.BillModel
import com.example.pocketpal.modelsb.PaidBillModel
import com.google.firebase.database.*
import io.mockk.*
import org.hamcrest.CoreMatchers.any
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Mock
    //lateinit var onBillClickListener: BillAdapter.onBillClickListener

    private lateinit var billList: ArrayList<BillModel>
    private lateinit var billAdapter: BillAdapter

    @Before
    fun setUp() {
        billList = ArrayList()
        billList.add(BillModel("Electricity", 200.0, "2023-05-10"))
        billList.add(BillModel("Internet", 100.0, "2023-05-20"))
        billAdapter = BillAdapter(billList)
    }

    @Test
    fun getItemCount() {
        assertEquals(billList.size, billAdapter.itemCount)
    }

    @Test
    fun testCalculateTotalAmount() {
        val billList = arrayListOf(
            BillModel("bill1", 100.00, "category1", "January", "2023-01-01"),
            BillModel("bill2", 50.00, "category2", "January", "2023-01-05"),
            BillModel("bill3", 75.00, "category1", "February", "2023-02-01")
        )

        var totalAmount = 0.00
        for (bill in billList) {
            totalAmount += bill.amount ?: 0.00
        }

        assertEquals(225.00, totalAmount, 0.001)
    }

    @Test
    fun calculateTotalAmount() {
        val pbillList = arrayListOf<PaidBillModel>()
        pbillList.add(PaidBillModel("bill1", 100.00, "electricity", "Jan", "2022-01-01"))
        pbillList.add(PaidBillModel("bill2", 50.00, "gas", "Jan", "2022-01-09"))
        pbillList.add(PaidBillModel("bill3", 75.00, "water", "Feb", "2022-02-07"))

        var totalAmount = 0.00
        for (pbill in pbillList) {
            totalAmount += pbill.amount ?: 0.00
        }

        assertEquals(225.00, totalAmount, 0.001)
    }



}


