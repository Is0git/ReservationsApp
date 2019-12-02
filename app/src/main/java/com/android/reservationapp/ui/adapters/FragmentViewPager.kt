package com.android.reservationapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.reservationapp.ui.fragments.LoginFragment
import com.android.reservationapp.ui.fragments.RegistrationFragment

class FragmentViewPager(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    lateinit var fragment: Fragment
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> fragment = LoginFragment()
            1 -> fragment = RegistrationFragment()
        }
        return fragment
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? =
        if (position == 0) "LOGIN" else "REGISTRATION"
}