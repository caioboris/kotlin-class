package br.com.fiap.agendacontatosjc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.fiap.agendacontatosjc.ui.theme.AgendaContatosJCTheme
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Formulario(this)
        }
    }
}

@Composable
fun Formulario(contexto : ComponentActivity){
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var URLBASE = "https://mobile-cboris-default-rtdb.firebaseio.com"

    var gson = Gson()
    var httpClient = OkHttpClient()

    Column(Modifier.fillMaxSize()) {
        Text("Agenda de Contato", fontSize = 32.sp)
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it } ,
            label = {
                Text("Nome Completo")
            })
        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it } ,
            label = {
                Text("Telefone")
            })
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Email")
            })
        Button(onClick =
        {
            val contato = Contato(nome, email, telefone)
            val contatoJson = gson.toJson(contato)

            val request = Request.Builder()
                .url("$URLBASE/contatos.json")
                .post(contatoJson.toRequestBody(
                    "application/json".toMediaType()
                ))
                .build()
            val response = object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    contexto.runOnUiThread{
                        Toast.makeText(contexto,"Contato gravado com sucesso", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    contexto.runOnUiThread{
                        Toast.makeText(contexto,"Erro ao gravar contato", Toast.LENGTH_LONG).show()
                    }
                }
            }
            httpClient.newCall(request).enqueue(response)
        }) {
            Text("Salvar")
        }
    }
}