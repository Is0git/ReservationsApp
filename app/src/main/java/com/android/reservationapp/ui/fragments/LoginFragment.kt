package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_RIGHT
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.databinding.LoginFragmentBinding
import com.android.reservationapp.util.showSnackBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.auth_fragment.*

class LoginFragment : Fragment() {
    lateinit var mauth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var binding: LoginFragmentBinding
    lateinit var nav: NavController
    private val dialog:DialogFragment by lazy { ForgotPassword() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        mauth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            if (binding.email.text.toString().isNotBlank() && binding.password.text.toString().isNotBlank())
                mauth.signInWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener(activity!!) {

                    when {
                        it.isSuccessful -> {
                            user = mauth.currentUser!!
                            if (user.isEmailVerified) nav.navigate(R.id.action_loginFragment_to_mainFragment) else {
                                showSnackBar(
                                    binding.root,
                                    "You need to verify your email"
                                )
                                mauth.signOut()
                            }
                        }

                        else -> showSnackBar(binding.root, it.exception.toString())
                    }
                } else showSnackBar(binding.root, "Ooops! Something went wrong!")
        }

        binding.forgotButton.setOnClickListener {
        dialog.show(fragmentManager!!, "DIALOG")}
        binding.registerButton.setOnClickListener { activity!!.view_pager.arrowScroll(FOCUS_RIGHT) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(activity!!, R.id.main_fragment_container)
    }
}