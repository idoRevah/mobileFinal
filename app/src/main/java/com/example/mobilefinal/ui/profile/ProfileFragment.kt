package com.example.mobilefinal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobilefinal.R
import com.example.mobilefinal.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the current user
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.tvUserEmail.text = it.email

                // Optional: Load profile image if you have one
//                Glide.with(this)
//                    .load(it.profilePictureUrl) // Make sure your model has this if needed
//                    .placeholder(com.example.mobilefinal.R.drawable.ic_user_placeholder)
//                    .into(binding.profileImageView)
            }
        }

        // Sign out button
        binding.btnSignOut.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_authFragment) // Navigate after login
        }

        // Load user from repo (from Room or Firestore)
//        viewModel.fetchCurrentUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
