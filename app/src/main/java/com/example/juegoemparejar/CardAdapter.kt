package com.example.juegoemparejar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
/**
 * Adaptador para gestionar la visualizacion de tarjetas en un RecyclerView.
 *
 * @param cards Lista de objetos Card a mostrar.
 * @param onCardClickListener Listener para gestionar los eventos de clic de las tarjetas.
 */
class CardAdapter(
    private var cards: List<Carta>,
    private val onCardClickListener: (Carta) -> Unit
) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcard, parent, false)
        return CardViewHolder(view)
    }
    /**
     * Vincula los datos de una tarjeta al ViewHolder correspondiente.
     *
     * @param position Posición de la tarjeta en el conjunto de datos.
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        // Pone el imagen corespondienta al estado
        if (card.isMatched) {
            holder.cardImageView.setImageResource(card.imageId)
        } else {
            holder.cardImageView.setImageResource(if (card.isFlipped) card.imageId else R.drawable.poker_svgrepo_com)
        }

        // Listener para cuando se hace click en una carta
        holder.itemView.setOnClickListener {
            onCardClickListener.invoke(card)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setNewData(newCards: List<Carta>) {
        cards = newCards
        notifyDataSetChanged()
    }
}