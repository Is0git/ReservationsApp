package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.MainActivity
import com.android.reservationapp.R
import com.android.reservationapp.databinding.RegistrationFragmentBinding
import com.android.reservationapp.util.showSnackBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrationFragment : Fragment() {
    lateinit var binding: RegistrationFragmentBinding
    lateinit var mauth:FirebaseAuth
    lateinit var nav:NavController
    lateinit var firebaseUser: FirebaseUser
    lateinit var fragmentsListener: FragmentsListener
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
                mauth.createUserWithEmailAndPassword(binding.emailText.text.toString(),
                    binding.passwordText.text.toString()).addOnCompleteListener(this.activity!!) {
                    if(it.isSuccessful) {
                        firebaseUser = mauth.currentUser!!
                            firebaseUser.sendEmailVerification()
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