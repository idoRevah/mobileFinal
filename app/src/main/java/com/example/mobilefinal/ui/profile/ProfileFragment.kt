package com.example.mobilefinal.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobilefinal.R
import com.example.mobilefinal.databinding.FragmentProfileBinding
import com.example.mobilefinal.utils.ImageUtils

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
                binding.tvUserEmail.setText(it.email)
                binding.etDisplayName.setText(it.display_name)
                if (user.profile_picture != null && user.profile_picture != "") {
                    binding.profileImageView.setImageBitmap(ImageUtils.base64ToBitmap(it.profile_picture.toString()))
                } else {
                    binding.profileImageView.setImageResource(R.drawable.ic_user_placeholder)
                }
            }
        }

        var selectedImageBase64: String? = null

        val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                if (bitmap != null) {
                    selectedImageBase64 = ImageUtils.bitmapToBase64(bitmap)
                    binding.profileImageView.setImageBitmap(bitmap)
                }
            }
        }
        binding.profileImageView.setOnClickListener {
            imagePicker.launch("image/*")
        }

        binding.btnSaveChanges.setOnClickListener {
            val display_name = binding.etDisplayName.text.toString().trim()
            viewModel.saveChanges(display_name, selectedImageBase64)
        }

        // Sign out button
        binding.btnSignOut.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_authFragment) // Navigate after login
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
