package com.example.idosodev.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idosodev.databinding.ItemMedicineBinding
import com.example.idosodev.domain.model.Medicine

class MedicineAdapter(private val medicineList: List<Medicine>) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int = medicineList.size

    class MedicineViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine) {
            binding.tvMedicineName.text = medicine.name
            binding.tvMedicineDose.text = medicine.dosage
            //binding.ivMedicineIcon.setImageResource(R.drawable.ic_pill) // Se o ícone for dinâmico
        }
    }
}