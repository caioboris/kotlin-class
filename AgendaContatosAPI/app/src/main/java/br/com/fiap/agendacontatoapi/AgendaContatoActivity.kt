package br.com.fiap.agendacontatoapi

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.agendacontatoapi.model.Contato
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class AgendaContatoActivity : Activity() {
    private val BASE_URL = "https://mobile-cboris-default-rtdb.firebaseio.com";

    override fun onCreate(bundle: Bundle?){
        super.onCreate(bundle)
        setContentView(R.layout.agenda_contato)

        val cliente = OkHttpClient()
        val gson = Gson()

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
                    "telefone": "${telefone.text}"
                }
            """.trimIndent()
            val request = Request.Builder()
                .url("$BASE_URL/contatos.json")
                .post(
                    contatoJson.toRequestBody(
                        "application/json".toMediaType()
                    )
                )
                .build()
            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity,
                            "Erro: ${e.message} ao gravar o contato",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AgendaContatoActivity,
                            "Contato gravado com sucesso",
                            Toast.LENGTH_LONG
                        ).show()
                        nome.setText("")
                        email.setText("")
                        telefone.setText("")
                    }
                }
            }
            cliente.newCall(request).enqueue(response)
        }

        botaoPesquisar.setOnClickListener {
            val request = Request.Builder()
                .url("$BASE_URL/contatos.json?orderBy=\"nome\"&equalTo=\"${nome.text}\"")
                .get()
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("AGENDA",
                        "Erro: ${e.message} ao acessar o Firebase")
                }
                override fun onResponse(call: Call, response: Response) {
                    val textoJson = response.body?.string()
                    Log.v("AGENDA",
                        "Resposta: ${response.body?.string()}")

                    val tipo = object :
                        TypeToken<HashMap<String?, Contato>>(){}.type
                    val jsonMap : HashMap<String?, Contato?> =
                        gson.fromJson(textoJson, tipo)

                    for(contato in jsonMap.values){
                        if(contato != null){
                            runOnUiThread{
                                nome.setText(contato.Nome)
                                telefone.setText(contato.Telefone)
                                email.setText(contato.Email)
                            }
                        }
                    }
                }
            }
            cliente.newCall(request).enqueue(response)
        }
    }
}