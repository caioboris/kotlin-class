package br.com.fiap.agendacontatos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NotasActivity : Activity() {

    override fun onCreate(bundle: Bundle?){
        super.onCreate(bundle)
        setContentView(R.layout.NotasLayout)

        val notaProva1 = findViewById<EditText>(R.id.nota1)
        val notaProva2 = findViewById<EditText>(R.id.nota2)
        val notaProva3 = findViewById<EditText>(R.id.nota3)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener{
            val intent = Intent(this, MediaActivity :: class.java)
        }
    }

    fun obterMedia(nota1 : Double, nota2 : Double, nota3: Double) : Double{
        return (nota1 + nota2 + nota3) / 3
    }

}