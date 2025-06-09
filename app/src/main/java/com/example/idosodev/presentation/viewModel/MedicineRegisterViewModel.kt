package com.example.idosodev.presentation.features.medicineregister

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar // Necessário para o TimePicker

// Data class para representar o estado da UI
data class MedicineRegisterUiState(
    var name: String = "",
    var via: String = "",
    var dosage: String = "", // Usando 'dosage' em vez de 'dose' para clareza
    var time: String = "", // Usando 'time' em vez de 'horario'
    var period: String = "", // Usando 'period' em vez de 'periodo'
    var showTimePicker: Boolean = false // Estado para controlar a exibição do TimePicker
)

// Eventos que o ViewModel pode disparar para a UI (ex: para exibir um Toast, navegar)
sealed class MedicineRegisterEvent {
    data class ShowToast(val message: String) : MedicineRegisterEvent()
    data class LaunchGalleryPicker(val intent: Intent) : MedicineRegisterEvent()
    data class LaunchCameraCapture(val intent: Intent) : MedicineRegisterEvent()
    data class RequestPermission(val permission: String) : MedicineRegisterEvent()
    object NavigateToUserRegister : MedicineRegisterEvent() // Exemplo de navegação para outra tela
    data class NavigateTo(val route: String) : MedicineRegisterEvent() // Mais genérico
}

class MedicineRegisterViewModel(application: Application) : AndroidViewModel(application) {

    var uiState by mutableStateOf(MedicineRegisterUiState())
        private set

    private val _events = MutableSharedFlow<MedicineRegisterEvent>()
    val events = _events.asSharedFlow()

    // --- Funções de atualização de estado (chamadas pela UI) ---
    fun onNameChange(newValue: String) {
        uiState = uiState.copy(name = newValue)
    }

    fun onViaChange(newValue: String) {
        uiState = uiState.copy(via = newValue)
    }

    fun onDosageChange(newValue: String) {
        uiState = uiState.copy(dosage = newValue)
    }

    fun onTimeChange(newValue: String) {
        uiState = uiState.copy(time = newValue)
    }

    fun onPeriodChange(newValue: String) {
        uiState = uiState.copy(period = newValue)
    }

    // --- Funções para controlar a exibição do TimePicker ---
    fun onTimeFieldClick() {
        uiState = uiState.copy(showTimePicker = true)
    }

    fun onTimePickerDismissed() {
        uiState = uiState.copy(showTimePicker = false)
    }

    // --- Lógica de Negócio (chamadas pela UI) ---

    fun onRegisterMedicineClick() {
        val name = uiState.name.trim()
        val via = uiState.via.trim()
        val dosage = uiState.dosage.trim()
        val time = uiState.time.trim()
        val period = uiState.period.trim()

        if (name.isEmpty() || via.isEmpty() || dosage.isEmpty() || time.isEmpty() || period.isEmpty()) {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.ShowToast("Por favor, preencha todos os campos."))
            }
            return
        }

        // Simulação da lógica de salvamento
        val message = "Medicamento cadastrado:\n" +
                "Nome: $name\n" +
                "Via: $via\n" +
                "Dose: $dosage\n" +
                "Horário: $time\n" +
                "Período: $period"

        viewModelScope.launch {
            _events.emit(MedicineRegisterEvent.ShowToast(message))
            uiState = MedicineRegisterUiState() // Reseta o estado para limpar os campos
            _events.emit(MedicineRegisterEvent.NavigateTo(AppDestinations.CADASTRO_USUARIO_ROUTE)) // Exemplo de navegação após sucesso
        }
    }

    fun onSelectPhotoClick() {
        checkGalleryPermissionAndOpen()
    }

    fun onTakePhotoClick() {
        checkCameraPermissionAndOpen()
    }

    // --- Lógica de Permissões e Ações de Imagem ---

    private var lastPermissionRequested: String? = null // Para saber qual permissão foi solicitada por último

    private fun checkGalleryPermissionAndOpen() {
        val context = getApplication<Application>().applicationContext
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        lastPermissionRequested = permission // Armazena a permissão solicitada
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            openGalleryForImage()
        } else {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.RequestPermission(permission))
            }
        }
    }

    private fun checkCameraPermissionAndOpen() {
        val context = getApplication<Application>().applicationContext
        val permission = android.Manifest.permission.CAMERA
        lastPermissionRequested = permission // Armazena a permissão solicitada
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            openCameraForPhoto()
        } else {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.RequestPermission(permission))
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        viewModelScope.launch {
            _events.emit(MedicineRegisterEvent.LaunchGalleryPicker(intent))
        }
    }

    private fun openCameraForPhoto() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(context.packageManager) != null) {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.LaunchCameraCapture(intent))
            }
        } else {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.ShowToast("Nenhum aplicativo de câmera encontrado."))
            }
        }
    }

    // Função a ser chamada pela Activity após o resultado da permissão
    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (isGranted) {
            when (permission) {
                android.Manifest.permission.CAMERA -> openCameraForPhoto()
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_EXTERNAL_STORAGE -> openGalleryForImage()
            }
        } else {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.ShowToast("Permissão negada."))
            }
        }
        lastPermissionRequested = null // Limpa a permissão após o resultado
    }

    // Função a ser chamada pela Activity após o resultado da seleção de imagem
    fun onImagePicked(uri: Uri?) {
        if (uri != null) {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.ShowToast("Imagem selecionada: $uri"))
            }
        }
    }

    // Função a ser chamada pela Activity após o resultado da captura de foto
    fun onPhotoTaken(bitmap: Bitmap?) {
        if (bitmap != null) {
            viewModelScope.launch {
                _events.emit(MedicineRegisterEvent.ShowToast("Foto tirada com sucesso!"))
            }
        }
    }
}