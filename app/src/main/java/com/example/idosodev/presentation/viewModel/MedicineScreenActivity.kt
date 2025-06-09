package com.example.idosodev.presentation.viewModel

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.idosodev.R
import com.example.idosodev.databinding.ActivityMedicineScreenBinding
import com.example.idosodev.presentation.view.HomeFragment
import com.example.idosodev.presentation.view.MedicinesFragment

class MedicineScreenActivity : MainActivity() {

    private lateinit var binding: ActivityMedicineScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(MedicinesFragment())
        }

        setupBottomNavigation()
    }

    fun updateTopBarUserName(userName: String) {
        binding.topBar.tvGreeting.text = getString(R.string.hello_user, userName)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_pill)
                    updateTopBarUserName("")
                    true
                }
                R.id.navigation_medications -> {
                    showToast("Navegar para Medicamentos")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_pill)
                    binding.topBar.tvGreeting.text = "Meus Remédios"
                    true
                }
                R.id.navigation_schedule -> {

                    showToast("Navegar para Agenda")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_calendar)
                    binding.topBar.tvGreeting.text = "Minha Agenda"
                    true
                }
                R.id.navigation_profile -> {
                    showToast("Navegar para Perfil")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_user)
                    binding.topBar.tvGreeting.text = "Meu Perfil"
                    true
                }
                R.id.navigation_settings -> {
                    showToast("Navegar para Configurações")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_settings)
                    binding.topBar.tvGreeting.text = "Configurações"
                    true
                }
                else -> false
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}