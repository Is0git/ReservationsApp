package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(){
    lateinit var mauth: FirebaseAuth
    lateinit var binding: LoginFragmentBinding
    lateinit var nav:NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        mauth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            mauth.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(activity!!) {
                if (it.isSuccessful) nav.navigate(R.id.mainFragment)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
    }
}