package br.upe.horaDeTomar.ui

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Account
import br.upe.horaDeTomar.data.entities.User
import br.upe.horaDeTomar.data.repositories.AccountRepository
import br.upe.horaDeTomar.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: AccountRepository): ViewModel() {
    val accounts: StateFlow<List<Account>> = repository.accounts
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val hasAccount: StateFlow<Boolean> = accounts
        .map { it.isNotEmpty() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    suspend fun createAccount(name: String) {
        val account = Account(
            id = 1,
            createdAt = System.currentTimeMillis(),
            accountName = name
        )
        repository.insert(account)
    }
}