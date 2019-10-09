package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.data.Users
import com.android.reservationapp.databinding.MainFragmentBinding
import com.android.reservationapp.ui.adapters.RecentUsersAdapter
import com.android.reservationapp.util.FirebaseConsts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query


class MainFragment : Fragment(){
    lateinit var binding:MainFragmentBinding
    lateinit var nav: NavController

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var listener:ListenerRegistration
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = fireStore.collection(FirebaseConsts.users)
    private var madapter = RecentUsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.userList.adapter = madapter
        binding.button.setOnClickListener { auth.signOut()
        nav.navigate(R.id.loginFragment)}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()
        listener = users.limit(20).orderBy(FirebaseConsts.seen_recently, Query.Direction.DESCENDING)
            .addSnapshotListener { value, _ ->
                var b = value?.toObjects(Users::class.java)
                madapter.submitList(b)
            }
    }
    override fun onStop() {
        super.onStop()
        listener.remove()
    }
}

