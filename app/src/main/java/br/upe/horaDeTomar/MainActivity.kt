package br.upe.horaDeTomar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.upe.horaDeTomar.ui.MainScreen
import br.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoraDoRemedioTheme {
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
}