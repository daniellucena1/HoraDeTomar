package br.upe.horaDeTomar.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.User
import br.upe.horaDeTomar.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    val users: StateFlow<List<User>> = repository.users
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}