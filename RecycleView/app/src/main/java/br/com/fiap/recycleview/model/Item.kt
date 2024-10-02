package br.com.fiap.recycleview.model

data class Item(
    var id: Long = 0,
    var nome: String = "",
    var precoMedio : Double = 0.0,
    var quantidade : UInt = 0U
)