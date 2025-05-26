package com.example.idosodev.presentation.features.cadastroremedio

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.idosodev.R
import com.example.idosodev.presentation.features.cadastrousuario.CadastroUsuarioActivity
import com.example.idosodev.presentation.features.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.Locale

class CadastroRemedioActivity : MainActivity() {

    private lateinit var etNomeMedicamento: TextInputEditText
    private lateinit var etVia: TextInputEditText
    private lateinit var etDose: TextInputEditText
    private lateinit var etHorario: TextInputEditText
    private lateinit var etPeriodo: TextInputEditText
    private lateinit var ivSelecionarImagem: Button
    private lateinit var ivTirarFoto: Button
    private lateinit var btnCadastrarMedicamento: Button

    private val PERMISSION_REQUEST_CODE_CAMERA = 100
    private val PERMISSION_REQUEST_CODE_GALLERY = 101

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                Toast.makeText(this, "Imagem selecionada: $selectedImageUri", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val photoBitmap = result.data?.extras?.get("data") as? Bitmap
            if (photoBitmap != null) {
                Toast.makeText(this, "Foto tirada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_remedio)

        etNomeMedicamento = findViewById(R.id.et_nome_medicamento)
        etVia = findViewById(R.id.et_via)
        etDose = findViewById(R.id.et_dose)
        etHorario = findViewById(R.id.et_horario)
        etPeriodo = findViewById(R.id.et_periodo)
        ivSelecionarImagem = findViewById(R.id.btn_selecionar_foto)
        ivTirarFoto = findViewById(R.id.btn_tirar_foto)
        btnCadastrarMedicamento = findViewById(R.id.btn_cadastrar_medicamento)

        etHorario.setOnClickListener {
            showTimePickerDialog()
        }
        etHorario.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showTimePickerDialog()
            }
        }

        ivSelecionarImagem.setOnClickListener {
            checkGalleryPermissionAndOpen()
        }

        ivTirarFoto.setOnClickListener {
            checkCameraPermissionAndOpen()
        }

        btnCadastrarMedicamento.setOnClickListener {
            cadastrarMedicamento()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, R.style.MyTimePickerDialogTheme, { _, selectedHour, selectedMinute ->
            val selectedTime = String.Companion.format(
                Locale.getDefault(),
                "%02d:%02d",
                selectedHour,
                selectedMinute
            )
            etHorario.setText(selectedTime)
        }, hour, minute, true)

        timePickerDialog.setOnShowListener { dialogInterface ->
            val dialog = dialogInterface as TimePickerDialog

            val positiveButton = dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.verde_escuro))
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.verde_escuro))
        }

        timePickerDialog.show()
    }

    private fun checkGalleryPermissionAndOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                openGalleryForImage()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE_GALLERY)
            }
        } else {
            openGalleryForImage()
        }
    }

    private fun checkCameraPermissionAndOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCameraForPhoto()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE_CAMERA)
            }
        } else {
            openCameraForPhoto()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun openCameraForPhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            takePhotoLauncher.launch(intent)
        } else {
            Toast.makeText(this, "Nenhum aplicativo de câmera encontrado.", Toast.LENGTH_SHORT).show()
        }
    }

     override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGalleryForImage()
                } else {
                    Toast.makeText(this, "Permissão de galeria negada.", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraForPhoto()
                } else {
                    Toast.makeText(this, "Permissão de câmera negada.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cadastrarMedicamento() {
        val nome = etNomeMedicamento.text.toString().trim()
        val via = etVia.text.toString().trim()
        val dose = etDose.text.toString().trim()
        val horario = etHorario.text.toString().trim()
        val periodo = etPeriodo.text.toString().trim()

        if (nome.isEmpty() || via.isEmpty() || dose.isEmpty() || horario.isEmpty() || periodo.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Aqui você faria a lógica para salvar o medicamento
        val mensagem = "Medicamento cadastrado:\n" +
                "Nome: $nome\n" +
                "Via: $via\n" +
                "Dose: $dose\n" +
                "Horário: $horario\n" +
                "Período: $periodo"

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

        etNomeMedicamento.text?.clear()
        etVia.text?.clear()
        etDose.text?.clear()
        etHorario.text?.clear()
        etPeriodo.text?.clear()

        val intent = Intent(this, CadastroUsuarioActivity::class.java)

        startActivity(intent)

        finish()
    }
}