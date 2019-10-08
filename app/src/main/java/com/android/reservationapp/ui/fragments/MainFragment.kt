package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.android.reservationapp.databinding.MainFragmentBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class MainFragment : Fragment(){
    lateinit var binding:MainFragmentBinding
    lateinit var nav:NavController

    val auth:FirebaseAuth = FirebaseAuth.getInstance()

    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = fireStore.collection("users")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        users.get()
            .addOnCompleteListener{

            for(document in it.result!!) {
                Log.d("TAG", document.getString("email"))
            }}

        binding.button.setOnClickListener { auth.signOut() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
    }


}

