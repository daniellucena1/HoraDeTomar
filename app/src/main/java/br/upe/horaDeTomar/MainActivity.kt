package br.upe.horaDeTomar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.upe.horaDeTomar.ui.MainScreen
import br.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme
import br.upe.horaDeTomar.ui.themes.green_primary
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        HoraDoRemedioTheme {
            SetBarColor(color = Color(0xFF00FF00))
            MainScreen()
        }
    }
}

