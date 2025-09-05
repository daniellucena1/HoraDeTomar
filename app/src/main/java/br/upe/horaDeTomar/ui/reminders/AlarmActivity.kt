package br.upe.horaDeTomar.ui.reminders

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import br.upe.horaDeTomar.CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.manager.AlarmScheduler
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.ui.medications.MedicationsViewModel
import br.upe.horaDeTomar.ui.themes.alarm_button_green
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.button_green_primary
import br.upe.horaDeTomar.ui.themes.gray_light
import br.upe.horaDeTomar.ui.themes.green_alarm_background
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.white
import br.upe.horaDeTomar.ui.themes.white_background
import coil.compose.AsyncImage
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class AlarmActivity: ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmId = intent.getIntExtra("alarmId", -1)

        mediaPlayer = MediaPlayer.create(this, R.raw.gran_vals )
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        setContent {
            AlarmScreen(
                alarmId = alarmId,
                onDismiss = {dismissAlarm()},
                onSnooze = {snoozeAlarm()}
            )
        }
    }

    private fun dismissAlarm() {
        mediaPlayer.stop()
        finish()
    }

    private fun snoozeAlarm() {
        mediaPlayer.stop()

        val alarmId = intent.getIntExtra("alarmId", -1)
        val medicationId = intent.getIntExtra("MEDICATION_ID", -1)
        if ( alarmId != -1 ) {
            val scheduler = AlarmScheduler(this, getSystemService(Context.ALARM_SERVICE) as AlarmManager)
            val cal = Calendar.getInstance().apply { add(Calendar.MINUTE, 1) }
            scheduler.snooze(alarmId, medicationId, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }
}

@Composable
fun AlarmScreen(
    alarmId: Int,
    onDismiss: () -> Unit,
    onSnooze: () -> Unit,
    viewModel: MedicationsViewModel = hiltViewModel()
) {

    val medications by viewModel.medications.collectAsState()
    val alarmListState by viewModel.alarmListState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = green_alarm_background)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            alarmListState.forEach {
                alarm ->
                if (alarm.id == alarmId) {
                    medications.forEach {
                        medication ->
                        if (medication.id == alarm.medicationId) {
                            AlarmInfoFormat(
                                name = medication.name,
                                dose = medication.dose,
                                via = medication.via,
                                imageUri = medication.imageUri,
                                hour = alarm.hour,
                                minute = alarm.minute
                            )
                        }
                    }
                }
            }
            Row {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                    containerColor = alarm_button_green,
                    contentColor = white),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f)
                ) { Text("Cancelar") }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = onSnooze,
                    colors = ButtonDefaults.buttonColors(
                    contentColor = white,
                    containerColor = Color.Gray),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f)
                ) { Text("Adiar") }
            }

        }
    }
}

@Composable
fun AlarmInfoFormat(
    name: String,
    dose: String,
    via: String,
    imageUri: String,
    hour: String,
    minute: String
) {
    AsyncImage(
       model = imageUri.toUri(),
       contentDescription = null,
       modifier = Modifier
           .size(180.dp)
           .clip(RoundedCornerShape(16.dp)),
       contentScale = ContentScale.Crop
    )
    Text(
       text = name,
       fontSize = 32.sp,
       color = black,
       fontWeight = FontWeight.Bold
    )
    Text(
        text = "$via • $dose",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(24.dp))
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$hour:$minute",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = alarm_button_green // verde
        )
    }

    Spacer(modifier = Modifier.height(32.dp))
}