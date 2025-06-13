package com.example.idosodev.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.idosodev.R
import com.example.idosodev.ui.themes.black
import com.example.idosodev.ui.themes.green_primary
import com.example.idosodev.ui.themes.green_secondary

@Composable
fun TakePhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button (
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = green_secondary,
            contentColor = black
        ),
        border = BorderStroke(1.dp, green_primary),
        contentPadding = PaddingValues(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cam),
            contentDescription = "Ícone de câmera",
            tint = black
        )
    }
}