package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.data.Order
import com.android.reservationapp.data.Users
import com.android.reservationapp.databinding.MainFragmentBinding
import com.android.reservationapp.ui.adapters.RecentUsersAdapter
import com.android.reservationapp.util.FirebaseConsts
import com.google.android.material.chip.Chip
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
    private val orders = fireStore.collection(FirebaseConsts.orders)
    private val user = auth.currentUser

    private var madapter = RecentUsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.userList.adapter = madapter
        binding.button.setOnClickListener { auth.signOut()
        nav.navigate(R.id.action_mainFragment_to_loginFragment)}

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.hour.text = p1.toString()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.order.setOnClickListener { setOrder() }
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

    fun setOrder() {
        Log.d("TAG", "SOM5")

            Log.d("TAG", "SOM6")
            val id = binding.chipGroup.checkedChipId - 1
            val firstChip = binding.chipGroup[1] as Chip

            val id2 = binding.chipGroup2.checkedChipId - 1
            val secondChip = binding.chipGroup2[1] as Chip

            val id3 = binding.chipGroup3.checkedChipId - 1
            val thirdChip = binding.chipGroup3[1] as Chip

            val id4 = binding.chipGroup4.checkedChipId - 1
            val fourthChip = binding.chipGroup4[1] as Chip

            Log.d("TAG", "SOM")
            orders.add(
                Order(
                    System.currentTimeMillis() + binding.seekBar.progress * 3600000,
                    firstChip.text.toString(),
                    secondChip.text.toString(),
                    thirdChip.text.toString(),
                    user?.email, null
                )
            )
                .addOnSuccessListener {
                    Toast.makeText(context, "ORDER ADDED", Toast.LENGTH_LONG).show()
                }

    }
}

