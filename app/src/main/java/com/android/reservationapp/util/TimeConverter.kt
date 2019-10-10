package com.android.reservationapp.util

import android.util.Log

class TConverter {
    companion object {
        @JvmStatic
        fun convert(time:Long) : String {
            val currentTime = System.currentTimeMillis()
            val timeDiff = currentTime - time
            val hours  = timeDiff/3600000
            if(hours < 23) {
                val minutes = timeDiff / 60000 % 60
                return "$hours hours $minutes minutes ago"
            }
            else {
                val days = timeDiff/86400000
                val hours = hours % 24
                return "$days days $hours hours ago"
            }
        }
    }

}

