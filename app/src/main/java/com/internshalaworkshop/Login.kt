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
import com.jokerhdwallpaper.internshalaworkshop.R
import com.internshalaworkshop.Utils.PreferenceManager
import com.internshalaworkshop.database.MyDatabase
import com.jokerhdwallpaper.internshalaworkshop.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : Fragment() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var database: MyDatabase
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        database = MyDatabase.getInstance(requireContext())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
        preferenceManager = PreferenceManager(requireContext())
        binding.signInButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                login()
            }
        }


    }

    private suspend fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val user = database.userDao().getUser(email, password)
                if (user != null && user.password == password) {
                    preferenceManager.setEmail(email)
                    preferenceManager.setPassword(password)
                    preferenceManager.setLoggedIn(true)
                    withContext(Dispatchers.Main){
                        findNavController().navigate(R.id.action_login_to_dashboard2)
                        Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Invalid Details", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Enter valid email", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(), "Fill all details", Toast.LENGTH_LONG).show()
            }
        }

    }

}