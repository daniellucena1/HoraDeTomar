package br.upe.horaDeTomar.ui.users

import android.annotation.SuppressLint
import br.upe.horaDeTomar.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.upe.horaDeTomar.navigation.TopLevelsDestinations
import br.upe.horaDeTomar.ui.AccountViewModel
import br.upe.horaDeTomar.ui.components.DatePickerModal
import br.upe.horaDeTomar.ui.components.FieldTextOutlined
import br.upe.horaDeTomar.ui.components.RegisterButton
import br.upe.horaDeTomar.ui.components.SelectPhotoButton
import br.upe.horaDeTomar.ui.components.TakePhotoButton
import br.upe.horaDeTomar.ui.config.OutlinedInputConfig
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_secondary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserRegisterScreen(
    onUserRegistered: () -> Unit,
    userViewModel: UsersViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavController,
    isFirstTime: Boolean
) {
    var userName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedDate: String? by remember {
        mutableStateOf<String?>(null)
    }
    var showModal by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var isErrorOnUserName by remember { mutableStateOf(false) }
    var isErrorOnAddress by remember { mutableStateOf(false) }
    var isErrorOnDate by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .background(
                    color = green_secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Ícone de usuário",
                modifier = Modifier.fillMaxWidth(),
                tint = black
            )
        }

        FieldTextOutlined(
            value = userName,
            onChange = {
                userName = it
                isErrorOnUserName = it.isBlank()
           },
            config = OutlinedInputConfig(
                label = "Nome do Usuário",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            isError =  isErrorOnUserName
        )

        OutlinedTextField(
            value = selectedDate?.let { selectedDate } ?: "" ,
            onValueChange = {
                selectedDate = it
                isErrorOnDate = it.isBlank()
            },
            label = { Text("Data de Nascimento") },
            placeholder = { Text("Selecione a data") },
            isError = isErrorOnDate,
            supportingText = {
                if (
                    isErrorOnDate
                ) {
                    Text(
                        text = "Campo obrigatório",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Selecionar data",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showModal = true
                        }
                    }
                }
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 0.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        if ( showModal ) {
            DatePickerModal(
                onDateSelected = { millis ->
                    millis?.let {
                        selectedDate = millis.toBrazilianDateFormat()
                    }
                },
                onDismiss = { showModal = false }
            )
        }

        FieldTextOutlined(
            value = address,
            onChange = {
                address = it
                isErrorOnAddress = it.isBlank()
            },
            isError = isErrorOnAddress,
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
            config = OutlinedInputConfig(
                label = "Endereço",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectPhotoButton(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            TakePhotoButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }

        RegisterButton(
            onClick = {
                if (userName.isNotBlank() && address.isNotBlank() && selectedDate != null) {
                    coroutineScope.launch {
                        if (isFirstTime) {
                            accountViewModel.createAccount(userName)
                            userViewModel.createUser(userName, address, selectedDate.toString())
                        } else {
                            userViewModel.createUser(userName, address, selectedDate.toString())
                        }
                        onUserRegistered()
                    }
                } else {
                    isErrorOnUserName = userName.isBlank()
                    isErrorOnAddress = address.isBlank()
                    isErrorOnDate = selectedDate.isNullOrBlank()
                }
            },
            label = "Cadastrar-se",
            )
    }
}

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}
