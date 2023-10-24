package com.internshalaworkshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.internshalaworkshop.Adapters.AppliedWorkshopAdapter
import com.internshalaworkshop.Utils.PreferenceManager
import com.internshalaworkshop.database.MyDatabase
import com.internshalaworkshop.models.WorkshopEntity
import com.jokerhdwallpaper.internshalaworkshop.R
import com.jokerhdwallpaper.internshalaworkshop.databinding.BottomSheetWorkshopBinding
import com.jokerhdwallpaper.internshalaworkshop.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class dashboard : Fragment() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var workshopAdapter: AppliedWorkshopAdapter
    private lateinit var database: MyDatabase
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        preferenceManager = PreferenceManager(requireContext())
        database = MyDatabase.getInstance(requireContext())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!preferenceManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_dashboard_to_workshops2)
        }

        binding.workshopFab.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_workshops2)
        }

        if (preferenceManager.getEmail() != null) {
            getWorkshops()
        }

        workshopAdapter = AppliedWorkshopAdapter { selectedWorkshop ->
            val bottomSheetBinding = BottomSheetWorkshopBinding.inflate(layoutInflater)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(bottomSheetBinding.root)
            bottomSheetBinding.workshopNameTextView.text =
                selectedWorkshop.workshopName
            bottomSheetBinding.applyButton.visibility = View.INVISIBLE
            dialog.show()

        }
        binding.workshopRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workshopAdapter
        }


    }

    private fun getWorkshops() {
        CoroutineScope(Dispatchers.IO).launch {
            val appliedWorkshops =
                database.appliedWorkshopDao()
                    .getAppliedWorkshopsByUserEmail(preferenceManager.getEmail()!!)
            withContext(Dispatchers.Main) {
                workshopAdapter.submitList(appliedWorkshops)
            }
        }
    }





}

