package com.example.idosodev.presentation.features.cadastrousuario

import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DatePickerDialog
import java.util.Locale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosodev.R
import com.google.android.material.textfield.TextInputEditText

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var etNomeCompleto: TextInputEditText
    private lateinit var etEndereco: TextInputEditText
    private lateinit var etDataDeNascimento: TextInputEditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        etNomeCompleto = findViewById<TextInputEditText>(R.id.et_nome_usuario)
        etEndereco = findViewById<TextInputEditText>(R.id.et_endereco)
        etDataDeNascimento = findViewById<TextInputEditText>(R.id.et_data_nascimento)
        btnCadastrar = findViewById<Button>(R.id.btn_cadastrar)

        etDataDeNascimento.setOnClickListener {
            showDatePickerDialog()
        }
        etDataDeNascimento.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }

        btnCadastrar.setOnClickListener {
            cadastrarUsuario()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            etDataDeNascimento.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }

    private fun cadastrarUsuario() {
        val nome = etNomeCompleto.text.toString().trim()
        val endereco = etEndereco.text.toString().trim()
        val dataNascimento = etDataDeNascimento.text.toString().trim()

        if (nome.isEmpty() || endereco.isEmpty() || dataNascimento.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val mensagem = "Medicamento cadastrado:\n" +
                "Nome: $nome\n" +
                "Endereço: $endereco\n" +
                "Data de Nascimento: $dataNascimento\n"

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

        etNomeCompleto.text?.clear()
        etEndereco.text?.clear()
        etDataDeNascimento.text?.clear()
    }
}