package br.com.fiap.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.recycleview.model.Item

class ItemAdapter(var context : Context,
                  var lista : ArrayList<Item>) : RecyclerView.Adapter<ItemViewHolder>()  {

    var inflater = LayoutInflater.from(context)

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = lista.get(position)
        holder.txtNome.text = item.nome
        holder.txtQuantidade.text = item.quantidade.toString()
        holder.txtPreco.text = "R$7.2".format(item.precoMedio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = inflater.inflate(
            R.layout.item_activity_layout, parent, false)
        return ItemViewHolder(view)
    }
}