package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.databinding.CheckFragmentBinding
import kotlinx.coroutines.*

class LaunchFragment : Fragment() {
lateinit var binding:CheckFragmentBinding
    lateinit var nav:NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = CheckFragmentBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            nav.navigate(R.id.action_launchFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
    }
}