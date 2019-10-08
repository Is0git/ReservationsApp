package com.android.reservationapp

import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.ui.fragments.FragmentsListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
 lateinit var fragmentsListener: FragmentsListener
    lateinit var auth:FirebaseAuth
    lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }



}


