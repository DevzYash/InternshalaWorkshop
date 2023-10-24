package com.internshalaworkshop

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internshalaworkshop.Utils.PreferenceManager
import com.internshalaworkshop.database.MyDatabase
import com.jokerhdwallpaper.internshalaworkshop.databinding.FragmentRegisterBinding
import com.internshalaworkshop.models.UsersEntity
import com.jokerhdwallpaper.internshalaworkshop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Register : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                signup()
            }
        }

    }

    private suspend fun signup() {
        val database = MyDatabase.getInstance(requireContext())
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val user = UsersEntity(email = email, password = password)
                database.userDao().insertUser(user)
                val pref = PreferenceManager(requireContext()).apply {
                    this.setEmail(email)
                    this.setPassword(password)
                    this.setLoggedIn(true)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Signup Successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_register_to_dashboard)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Enter valid email", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Fill all details", Toast.LENGTH_LONG).show()

            }
        }


    }

}