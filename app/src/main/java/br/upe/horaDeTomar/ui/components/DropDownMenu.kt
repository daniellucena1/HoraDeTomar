package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.gray_dark
import br.upe.horaDeTomar.ui.themes.gray_light
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.text_balck
import br.upe.horaDeTomar.ui.themes.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    options: List<String>,
    label: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean,
    contentPadding: PaddingValues
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var stroke by remember { mutableStateOf(1) }

    Column(
        modifier = modifier.padding(contentPadding)
    ) {
        ExposedDropdownMenuBox (
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = it },
        ) {
            Box(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .heightIn(56.dp)
                    .border(
                        border = BorderStroke(
                            stroke.dp,
                            if (isDropDownExpanded) green_primary else text_balck
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        isDropDownExpanded = true
                        stroke = if (isDropDownExpanded) 2 else 1
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = selectedIndex?.let { options[it] } ?: label,
                    color = text_balck,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            ExposedDropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = {
                    isDropDownExpanded = false
                    stroke = 1
                }
            ) {
                options.forEachIndexed { index, value ->
                    DropdownMenuItem(
                        text = { Text(value) },
                        onClick = {
                            selectedIndex = index
                            isDropDownExpanded = false
                            onSelect(value)
                            stroke = 1
                        }
                    )
                }
            }
        }

        if (isError) {
            Text(
                text = "Campo Obrigatório",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}