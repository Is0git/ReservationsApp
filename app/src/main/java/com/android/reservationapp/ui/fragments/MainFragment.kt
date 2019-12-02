package com.android.reservationapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.reservationapp.R
import com.android.reservationapp.data.Order
import com.android.reservationapp.data.Users
import com.android.reservationapp.databinding.MainFragmentBinding
import com.android.reservationapp.ui.adapters.OrderListAdapter
import com.android.reservationapp.ui.adapters.RecentUsersAdapter
import com.android.reservationapp.util.FirebaseConsts
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query


class MainFragment : Fragment() {
    lateinit var binding: MainFragmentBinding
    lateinit var nav: NavController

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var listener: ListenerRegistration
    lateinit var orderListener: ListenerRegistration
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = fireStore.collection(FirebaseConsts.users)
    private val orders = fireStore.collection(FirebaseConsts.orders)
    private val user = auth.currentUser

    private var restaurantChip: Chip? = null
    private var pricetChip: Chip? = null
    private var durationChip: Chip? = null
    private var otherChip: MutableList<String> = mutableListOf()

    private var oadapter = OrderListAdapter()
    private var madapter = RecentUsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.userList.adapter = madapter
        binding.orderList.adapter = oadapter
        binding.button.setOnClickListener {
            auth.signOut()
            nav.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.hour.text = p1.toString()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, id ->
            restaurantChip = if (id != ChipGroup.NO_ID) chipGroup.findViewById(id) as Chip else null

            Log.d("TAG", "MESSAGE: $restaurantChip")
        }
        binding.chipGroup2.setOnCheckedChangeListener { chipGroup, id ->
            pricetChip = if (id != ChipGroup.NO_ID) chipGroup.findViewById(id) as Chip else null
        }
        binding.chipGroup3.setOnCheckedChangeListener { chipGroup, id ->
            durationChip = if (id != ChipGroup.NO_ID) chipGroup.findViewById(id) as Chip else null
        }


        binding.chipGroup
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
                val b = value?.toObjects(Users::class.java)
                madapter.submitList(b)
            }

        orderListener = orders.limit(20).orderBy("ordered_time", Query.Direction.DESCENDING)
            .addSnapshotListener { value, _ ->
                val result = value?.toObjects(Order::class.java)
                oadapter.submitList(result)
            }

    }

    override fun onStop() {
        super.onStop()
        listener.remove()
    }

    fun setOrder() {

        if (restaurantChip != null && pricetChip != null && durationChip != null) {
            getOtherChips()
            orders.add(
                Order(
                    binding.calendar.date + binding.seekBar.progress * 3600000,
                    restaurantChip?.text.toString(),
                    pricetChip?.text.toString(),
                    durationChip?.text.toString(),
                    user?.email,
                    System.currentTimeMillis(),
                    otherChip

                )
            )
                .addOnSuccessListener {
                    Toast.makeText(context, "ORDER ADDED", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context, "FILL EVERYTHING", Toast.LENGTH_LONG).show()
        }
    }


    fun getOtherChips() {
        otherChip.clear()
        var chip: Chip
        for (it in 0 until binding.chipGroup4.size) {
            Log.d("TAG", "MESSAG: $it")
            if (binding.chipGroup4[it].isSelected) {
                chip = binding.chipGroup[it] as Chip
                if (chip.isChecked) otherChip.add(chip.text.toString())

            }
        }
    }
}


