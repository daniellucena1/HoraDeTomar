package br.upe.horaDeTomar.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.gson.internal.GsonBuildConfig
import java.io.File

fun Context.createTempPictureUri(
    provider: String = "br.upe.horaDeTomar.provider",
    fileName: String = "picture_${System.currentTimeMillis()}",
    fileExtension: String = ".png"
): Uri {
    val tempFile = File.createTempFile(
        fileName, fileExtension, cacheDir
    ).apply {
        createNewFile()
    }

    return FileProvider.getUriForFile(applicationContext, provider, tempFile)
}

fun Context.persistImage(uri: Uri): String {
    val inputStream = contentResolver.openInputStream(uri) ?: return ""
    val file = File(filesDir, "image${System.currentTimeMillis()}.jpg")
    file.outputStream().use {
            outputStream ->
        inputStream.copyTo(outputStream)
    }
    return file.absolutePath
}