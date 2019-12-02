package com.android.reservationapp.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.android.reservationapp.MainActivity
import com.android.reservationapp.R
import com.android.reservationapp.databinding.AuthFragmentBinding
import com.android.reservationapp.databinding.LoginFragmentBinding
import com.android.reservationapp.ui.adapters.FragmentViewPager
import kotlinx.android.synthetic.main.activity_main.*

class AuthFragment : Fragment(), FragmentsListener {
    lateinit var loginBinding: AuthFragmentBinding
    lateinit var fragmentViewPager: FragmentViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = AuthFragmentBinding.inflate(inflater, container, false)
        fragmentViewPager = FragmentViewPager(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        loginBinding.tabLayout.setupWithViewPager(loginBinding.viewPager)
        loginBinding.viewPager.adapter = fragmentViewPager
        (activity as MainActivity).fragmentsListener = this

        return loginBinding.root
    }

    override fun swapFragment() {
        loginBinding.viewPager.arrowScroll(View.FOCUS_LEFT)
    }
}

interface FragmentsListener {
    fun swapFragment()
}