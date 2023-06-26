package com.example.pocketpal.modelsb

data class PaidBillModel (
    var billId: String? = null,
    var amount: Double?=null,
    var category: String? = null,
    var month: String?= null,
    var dueDate:String?= null ,
        )
