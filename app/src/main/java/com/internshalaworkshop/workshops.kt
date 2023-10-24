package com.internshalaworkshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.internshalaworkshop.Adapters.WorkshopAdapter
import com.internshalaworkshop.Utils.PreferenceManager
import com.internshalaworkshop.database.AppliedWorkshopDao
import com.internshalaworkshop.database.MyDatabase
import com.internshalaworkshop.database.WorkshopDao
import com.internshalaworkshop.models.AppliedWorkshopEntity
import com.internshalaworkshop.models.WorkshopEntity
import com.jokerhdwallpaper.internshalaworkshop.R
import com.jokerhdwallpaper.internshalaworkshop.databinding.BottomSheetWorkshopBinding
import com.jokerhdwallpaper.internshalaworkshop.databinding.FragmentWorkshopsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class workshops : Fragment() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var existingWorkshops: List<WorkshopEntity>
    private lateinit var binding: FragmentWorkshopsBinding
    private lateinit var workshopDao: WorkshopDao
    private lateinit var appliedWorkshopDao: AppliedWorkshopDao
    private lateinit var database: MyDatabase
    private lateinit var workshopAdapter: WorkshopAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkshopsBinding.inflate(layoutInflater, container, false)
        database = MyDatabase.getInstance(requireContext())
        appliedWorkshopDao = database.appliedWorkshopDao()
        workshopDao = database.workshopDao()
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workshopAdapter = WorkshopAdapter { selectedWorkshop ->
            val bottomSheetBinding = BottomSheetWorkshopBinding.inflate(layoutInflater)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(bottomSheetBinding.root)
            bottomSheetBinding.workshopNameTextView.text = selectedWorkshop.workshopName
            bottomSheetBinding.descriptionTextView.text = selectedWorkshop.description
            bottomSheetBinding.dateTextView.text = selectedWorkshop.date

            CoroutineScope(Dispatchers.IO).launch {
                if (preferenceManager.getEmail() != null) {
                    val applied =
                        appliedWorkshopDao.getAppliedWorkshopsByUserEmail(preferenceManager.getEmail()!!)
                    applied.forEach {
                        if (it.id == selectedWorkshop.id) {
                            withContext(Dispatchers.Main) {
                                bottomSheetBinding.applyButton.text = "Already Applied"
                                bottomSheetBinding.applyButton.isClickable = false
                            }
                        }
                    }
                }
            }

            bottomSheetBinding.applyButton.setOnClickListener {
                if (!preferenceManager.isLoggedIn()) {
                    Toast.makeText(
                        requireContext(),
                        "You need to login to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_workshops_to_login)
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        appliedWorkshopDao.insertAppliedWorkshop(
                            AppliedWorkshopEntity(
                                workshopName = selectedWorkshop.workshopName,
                                userEmail = preferenceManager.getEmail()!!
                            )
                        )
                    }
                    Toast.makeText(requireContext(), "Applied Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                dialog.dismiss()

            }

            dialog.show()
        }

        binding.workshopRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workshopAdapter
        }

        addWorkshopData()

    }

    private fun addWorkshopData() {
        val demoWorkshops = listOf(
            WorkshopEntity(
                workshopName = "Android Basics Workshop",
                description = "Learn Android fundamentals",
                date = "2023-11-10"
            ),
            WorkshopEntity(
                workshopName = "Advanced Android Workshop",
                description = "Deep dive into advanced Android concepts",
                date = "2023-11-15"
            ),
            WorkshopEntity(
                workshopName = "Kotlin Programming Workshop",
                description = "Master Kotlin programming language",
                date = "2023-11-20"
            )
        )
        CoroutineScope(Dispatchers.IO).launch {
            existingWorkshops = workshopDao.getAllWorkshops()
            withContext(Dispatchers.Main) {
                workshopAdapter.submitList(existingWorkshops)
            }
            if (existingWorkshops.isEmpty()) {
                demoWorkshops.forEach {
                    workshopDao.insertWorkshop(it)
                }
            }


        }
    }


}