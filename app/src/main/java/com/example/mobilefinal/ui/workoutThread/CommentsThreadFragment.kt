package com.example.mobilefinal.ui.workoutThread

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilefinal.data.model.Comment
import com.example.mobilefinal.databinding.FragmentWorkoutThreadBinding
import com.example.mobilefinal.utils.ImageUtils
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class CommentsThreadFragment : Fragment() {

    private var _binding: FragmentWorkoutThreadBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommentViewModel by viewModels()

    private val adapter = CommentAdapter()
    private var base64Image: String? = null

    // ✅ Modern Image Picker
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap != null) {
                base64Image = ImageUtils.bitmapToBase64(bitmap)
                binding.imagePreview.setImageBitmap(bitmap)
                binding.imagePreview.visibility = View.VISIBLE

                Log.d("WorkoutThread", "Selected image (base64): ${base64Image?.take(40)}...")
            } else {
                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutThreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getWorkoutIdFromArgs(): String {
        val args = CommentsThreadFragmentArgs.fromBundle(requireArguments())
        return args.workoutId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerViewOpinions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewOpinions.adapter = adapter

        val workoutId = getWorkoutIdFromArgs()
        Log.d("CommentsThreadFragment", "Workout ID: $workoutId")

        viewModel.getComments(workoutId).observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.buttonUploadImage.setOnClickListener {
            pickImageFromGallery()
        }

        binding.buttonSendOpinion.setOnClickListener {
            val text = binding.editTextOpinion.text.toString().trim()
            if (text.isNotEmpty()) {
                val user = FirebaseAuth.getInstance().currentUser ?: return@setOnClickListener

                val comment = Comment(
                    id = UUID.randomUUID().toString(),
                    workoutId = workoutId,
                    content = text,
                    image = base64Image,
                    authorUserId = user.uid,
                    authorNickname = user.email ?: "Unknown user",
                    createdAt = System.currentTimeMillis()
                )

                viewModel.addComment(comment)

                // Reset UI
                binding.editTextOpinion.setText("")
                base64Image = null
                binding.imagePreview.setImageDrawable(null)
                binding.imagePreview.visibility = View.GONE
            }
        }
    }

    private fun pickImageFromGallery() {
        imagePickerLauncher.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
