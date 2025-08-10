package com.example.omnitek.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.databinding.FragmentDashBoardBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    @Inject
    lateinit var jAuth: FirebaseAuth

    private lateinit var binding: FragmentDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        binding.apply {
            btnLogout.setOnClickListener {
                jAuth.signOut()
                findNavController().navigate(R.id.action_dashBoardFragment_to_startFragment)
            }
        }

        return binding.root
    }
}