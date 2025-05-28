package com.example.idosodev.presentation.features.telainicial

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.idosodev.R
import com.example.idosodev.databinding.ActivityTelaInicialBinding
import com.example.idosodev.presentation.features.HomeFragment
import com.example.idosodev.presentation.features.main.MainActivity

class TelaInicialActivity : MainActivity() {

    private lateinit var binding: ActivityTelaInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir o nome do usuário na barra superior (dinamicamente)
        updateTopBarUserName("Gabriel") // Exemplo: Substitua pelo nome do usuário logado

        // Inicializa o fragment da Home
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        setupBottomNavigation()
    }

    // Função para atualizar o nome do usuário na barra superior
    fun updateTopBarUserName(userName: String) {
        binding.topBar.tvGreeting.text = getString(R.string.hello_user, userName)
        // Você pode alterar o ícone da esquerda aqui também se necessário
        // binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_new_icon)
    }

    // Função para substituir o fragment no container
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
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_user) // Altera ícone para o de pessoa na Home
                    updateTopBarUserName("Gabriel") // Re-atualiza o nome
                    true
                }
                R.id.navigation_medications -> {
                    // Exemplo: Substitua por um novo fragment de Medicamentos
                    // replaceFragment(MedicationsFragment())
                    showToast("Navegar para Medicamentos")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_pill) // Altera ícone para o de remédio
                    binding.topBar.tvGreeting.text = "Meus Remédios" // Altera o texto
                    true
                }
                R.id.navigation_schedule -> {
                    // replaceFragment(ScheduleFragment())
                    showToast("Navegar para Agenda")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_calendar)
                    binding.topBar.tvGreeting.text = "Minha Agenda"
                    true
                }
                R.id.navigation_profile -> {
                    // replaceFragment(ProfileFragment())
                    showToast("Navegar para Perfil")
                    binding.topBar.ivLeftIcon.setImageResource(R.drawable.ic_user)
                    binding.topBar.tvGreeting.text = "Meu Perfil"
                    true
                }
                R.id.navigation_settings -> {
                    // replaceFragment(SettingsFragment())
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