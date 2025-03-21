package com.example.mobilefinal.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobilefinal.R
import com.example.mobilefinal.data.MobileFinalDatabase
import com.example.mobilefinal.data.repository.UserRepository
import com.example.mobilefinal.ui.userdetails.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var displayNameEditText: EditText
    private lateinit var profileImageUrlEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDao = MobileFinalDatabase.getDatabase().userDao()
        val userRepository = UserRepository(userDao)
        val viewModelFactory = UserViewModelFactory(userRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        displayNameEditText = view.findViewById(R.id.edit_displayName)
        profileImageUrlEditText = view.findViewById(R.id.edit_profileImageUrl)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModel.getUser(uid).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                view.findViewById<TextView>(R.id.text_email).text = user.email
                displayNameEditText.setText(user.username)
                profileImageUrlEditText.setText(user.profilePictureUrl)
            }
        }

        view.findViewById<Button>(R.id.save_button).setOnClickListener {
            viewModel.updateUserProfile(displayNameEditText.text.toString(), profileImageUrlEditText.text.toString())
        }
    }
}