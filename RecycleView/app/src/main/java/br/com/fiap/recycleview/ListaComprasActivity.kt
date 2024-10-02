package br.com.fiap.recycleview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.recycleview.model.Item

class ListaComprasActivity : AppCompatActivity() {

    val lista = ArrayList<Item>()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        lista.add(Item(1, "Detergente", 2.50, 4U))
        lista.add(Item(2, "Coca-cola", 8.50, 1U))
        lista.add(Item(3, "Amaciate", 17.50, 2U))
        lista.add(Item(4, "Maçã", 5.0, 10U))
        lista.add(Item(5, "Papel Higiênico", 32.50, 24U))
        lista.add(Item(6, "Miojo", 2.50, 5U))
        lista.add(Item(7, "Sucrilhos", 9.50, 1U))

        setContentView(R.layout.item_activity_layout)
        val listaCompras =
            findViewById<RecyclerView>(R.id.rcv_lista_compras)

        listaCompras.adapter = ItemAdapter(this, lista)
        listaCompras.layoutManager = LinearLayoutManager(this)
    }

}