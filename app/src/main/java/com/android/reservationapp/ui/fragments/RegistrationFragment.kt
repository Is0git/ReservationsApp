package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.databinding.RegistrationFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

lateinit var mauth: FirebaseAuth
class RegistrationFragment : Fragment() {
    lateinit var binding: RegistrationFragmentBinding
    lateinit var mauth:FirebaseAuth
    lateinit var nav:NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        mauth = FirebaseAuth.getInstance()
        binding.RegisterButton.setOnClickListener {
            if (binding.passwordText.text.toString().trim() == binding.passwordText.text.toString()
                && binding.passwordText.text.toString() == binding.passwordRepeatText.text.toString()
            ) {
                mauth.createUserWithEmailAndPassword(binding.emailText.text.toString(),
                    binding.passwordText.text.toString()).addOnCompleteListener(this.activity!!) {
                    if(it.isSuccessful) {
                        nav.navigate(R.id.action_registrationFragment_to_loginFragment3)
                        Toast.makeText(context, "SUCCESS: ${it.exception}", Toast.LENGTH_LONG).show()
                    } else {
                        val regex = Regex(":\\s.*")
                        val message = regex.find(it.exception.toString())
                        Toast.makeText(context, message?.value?.trimStart(':', ' '), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)

    }
}