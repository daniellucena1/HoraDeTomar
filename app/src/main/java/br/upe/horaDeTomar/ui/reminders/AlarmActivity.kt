package br.upe.horaDeTomar.ui.reminders

import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.data.manager.AlarmScheduler

@AndroidEntryPoint
class AlarmActivity: ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(this, R.raw.gran_vals )
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        setContent {
            AlarmScreen(
                onDismiss = {dismissAlarm()},
                onSnooze = {}
            )
        }
    }

    private fun dismissAlarm() {
        mediaPlayer.stop()
        finish()
    }

//    @RequiresApi(Build.VERSION_CODES.S)
//    private fun snoozeAlarm() {
//        mediaPlayer.stop()
//
//        val alarmId = intent.getIntExtra("alarmId", -1)
//        if ( alarmId != -1 ) {
//            val scheduler = AlarmScheduler(this)
//            val cal = Calendar.getInstance().apply { add(Calendar.MINUTE, 1) }
//            scheduler.schedule(alarmId, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
//        }
//        finish()
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }
}

@Composable
fun AlarmScreen(
    onDismiss: () -> Unit,
    onSnooze: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
            Text("Hora do Remédio!", color = Color.White, fontSize = 32.sp)
            Spacer(Modifier.height(32.dp))
            Row {
                Button(onClick = onDismiss) { Text("Cancelar") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = onSnooze) { Text("Soneca") }
            }
        }
    }
}