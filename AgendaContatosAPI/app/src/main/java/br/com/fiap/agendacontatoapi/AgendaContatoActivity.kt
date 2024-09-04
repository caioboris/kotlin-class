package br.com.fiap.agendacontatoapi

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class AgendaContatoActivity : Activity() {
    private val URL_FIREBASE = "https://mobile-cboris-default-rtdb.firebaseio.com";

    override fun onCreate(bundle: Bundle?){
        super.onCreate(bundle)
        setContentView(R.layout.agenda_contato)

        val client = OkHttpClient()

        val email = findViewById<EditText>(R.id.edtEmail)
        val nome = findViewById<EditText>(R.id.edtNome)
        val telefone = findViewById<EditText>(R.id.edtTelefone)
        val botaoGravar = findViewById<Button>(R.id.btnGravar)
        val botaoPesquisar = findViewById<Button>(R.id.btnPesquisar)

        botaoGravar.setOnClickListener {
            val contatoJson = """
                {
                    "nome": "${nome.text}",
                    "email": "${email.text}",
                    "telefone: "${telefone.text}"
                }
            """.trimIndent()

            val request = Request.Builder()
                .url("$URL_FIREBASE/contatos.json")
                .post(contatoJson.toRequestBody("application/json".toMediaType()))
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity,
                            "Falha ao gravar contato.", Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity,
                            "Contato gravado com sucesso.", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            client.newCall(request).enqueue(response)
        }
    }
}