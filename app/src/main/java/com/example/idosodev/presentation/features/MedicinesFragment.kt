package com.example.idosodev.presentation.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idosodev.R
import com.example.idosodev.databinding.FragmentMedicinesBinding
import com.example.idosodev.domain.model.Medicine
import com.example.idosodev.presentation.adapter.MedicineAdapter

class MedicinesFragment : Fragment() {
    private var _binding: FragmentMedicinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMedicineRecyclerView()

        binding.btnAddMedicine.setOnClickListener {
            (activity as? MedicineScreenActivity)?.showToast("Adicionar Remédio")
        }
    }

    private fun setupMedicineRecyclerView() {
        val medicines = listOf(
            Medicine("m1", "Paracetamol", "Dosagem: 1 comprimido","Oral", " 2x ao dia", "Próxima dose: 10:00"),
            Medicine("m2", "Omeprazol", "Dosagem: 20mg", "Oral", " 1x ao dia antes do café", "Próxima dose: 08:00")
        )
        binding.rvMedicines.layoutManager = LinearLayoutManager(context)
        binding.rvMedicines.adapter = MedicineAdapter(medicines)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}