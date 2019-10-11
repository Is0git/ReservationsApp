package com.android.reservationapp.data

data class Order(val time:Long? = null, val restaraunt:String? = null, val price:String? = null, val duration:String? = null, val email:String? = null, val ordered_time:Long? = null, val other:List<String>? = null )