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
import com.android.reservationapp.util.FirebaseConsts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchFragment : Fragment() {
    lateinit var binding: CheckFragmentBinding
    lateinit var nav: NavController
    lateinit var mauth: FirebaseAuth

    val firestore = FirebaseFirestore.getInstance()
    var collection: CollectionReference = firestore.collection(FirebaseConsts.users)
    lateinit var documentReference: DocumentReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckFragmentBinding.inflate(inflater, container, false)
        mauth = FirebaseAuth.getInstance()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            if (mauth.currentUser != null) {
                collection.whereEqualTo(FirebaseConsts.email, "${mauth.currentUser?.email}").get()
                    .addOnSuccessListener {
                        documentReference = collection.document(it.documents[0].id)
                        documentReference.update(
                            FirebaseConsts.seen_recently,
                            System.currentTimeMillis()
                        )
                            .continueWith { nav.navigate(R.id.action_launchFragment_to_mainFragment) }

                    }
            } else {
                nav.navigate(R.id.action_launchFragment_to_loginFragment)
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(activity!!, R.id.main_fragment_container)
    }
}

