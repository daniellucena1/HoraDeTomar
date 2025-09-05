package br.upe.horaDeTomar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import br.upe.horaDeTomar.ui.MainScreen
import br.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme
import br.upe.horaDeTomar.ui.themes.green_primary
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannels()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setContent {
            HoraDoRemedioTheme {
                SetBarColor(color = green_primary)
//                Surface (
//                    modifier = Modifier.fillMaxWidth(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    RegisterMedicineScreen()
//                }
                MainScreen()
            }
        }
    }

    @Composable
    fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = true
            )
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hora de Tomar"
            val descriptionText = "Alarme de Medicamento"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channelId = "Alarme de Medicamento 222"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        HoraDoRemedioTheme {
            SetBarColor(color = Color(0xFF00FF00))
            MainScreen()
        }
    }
}

