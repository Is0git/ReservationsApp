package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.MainActivity
import com.android.reservationapp.R
import com.android.reservationapp.data.Users
import com.android.reservationapp.databinding.RegistrationFragmentBinding
import com.android.reservationapp.util.showSnackBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationFragment : Fragment() {
    lateinit var binding: RegistrationFragmentBinding
    lateinit var mauth: FirebaseAuth
    lateinit var nav: NavController
    lateinit var firebaseUser: FirebaseUser
    lateinit var fragmentsListener: FragmentsListener
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val collectionReference = firestore.collection("users")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentsListener = (activity as MainActivity).fragmentsListener
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        mauth = FirebaseAuth.getInstance()
        binding.RegisterButton.setOnClickListener {
            if (binding.passwordText.text.toString().trim() == binding.passwordText.text.toString()
                && binding.passwordText.text.toString() == binding.passwordRepeatText.text.toString()
                && binding.passwordText.text.toString().isNotBlank() && binding.passwordRepeatText.text.toString().isNotBlank() && binding.emailText.text.toString().isNotBlank()
            ) {
                mauth.createUserWithEmailAndPassword(
                    binding.emailText.text.toString(),
                    binding.passwordText.text.toString()
                ).addOnCompleteListener(this.activity!!) { it ->
                    if (it.isSuccessful) {
                        firebaseUser = mauth.currentUser!!
//                        firebaseUser.sendEmailVerification()
                        collectionReference.add(Users(firebaseUser.email!!)).addOnCompleteListener {if(it.isSuccessful) Log.d("TAG", "SUC ${it.exception}") else Log.d("TAG", "FAIL ${it.exception}")}
                        Log.d("TAG", "RES ${firebaseUser.email}")
                        mauth.signOut()
                        fragmentsListener.swapFragment()
                        clearFragmentContent()
                        showSnackBar(binding.root, "SUCCESSFULLY REGISTERED")
                    } else {
                        val regex = Regex(":\\s.*")
                        val message = regex.find(it.exception.toString())
                        showSnackBar(binding.root, message?.value?.trimStart(':', ' '))
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(activity!!, R.id.main_fragment_container)

    }

    fun clearFragmentContent() {
        binding.passwordRepeatText.text?.clear()
        binding.passwordText.text?.clear()
        binding.emailText.text?.clear()
    }
}