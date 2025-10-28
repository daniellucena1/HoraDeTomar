package br.upe.horaDeTomar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val am = getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
            if (!am.canScheduleExactAlarms()) {
                startActivity(
                    Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        .setData(android.net.Uri.parse("package:$packageName"))
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
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val alarmNotif = NotificationChannel(
                CHANNEL_ID,
                "Hora de Tomar – Alarme",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações de alarme de medicamento"
                enableVibration(true)
                setShowBadge(false)
            }
            nm.createNotificationChannel(alarmNotif)
            val serviceChannel = NotificationChannel(
                "alarm_channel",
                "Hora de Tomar – Serviço de Alarme",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Toque e tela do alarme"
                enableVibration(true)
            }
            nm.createNotificationChannel(serviceChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        HoraDoRemedioTheme {
            SetBarColor(color = Color(0xFF00FF00))
            MainScreen()
        }
    }
}

