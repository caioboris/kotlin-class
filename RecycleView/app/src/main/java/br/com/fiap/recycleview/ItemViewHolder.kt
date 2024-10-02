package br.com.fiap.recycleview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    var txtNome : TextView = view.findViewById(R.id.txt_nome_produto)
    var txtQuantidade : TextView = view.findViewById(R.id.txt_quantidade)
    var txtPreco : TextView = view.findViewById(R.id.txt_preco)
}