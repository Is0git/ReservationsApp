package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.reservationapp.databinding.ForgotPasswordFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : DialogFragment() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ForgotPasswordFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = ForgotPasswordFragmentBinding.inflate(inflater, container, false)
        binding.cancel.setOnClickListener { this@ForgotPassword.dismiss() }
        binding.send.setOnClickListener {
            if (binding.email.text.toString().isNotBlank()) {
                auth.sendPasswordResetEmail(binding.email.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        this.dismiss()
                        Toast.makeText(
                            context,
                            "Check your email in order to reset password",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val regex = Regex(":\\s.*")
                        val message = regex.find(it.exception.toString())

                    }
                }
            }

        }
        return binding.root
    }

}