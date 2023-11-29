package com.example.miappjugadores.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.miappjugadores.R
import com.example.miappjugadores.modelo.Jugador
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class AdapterJugadores(
    private val db: FirebaseFirestore,
    options: FirestoreRecyclerOptions<Jugador>
) : FirestoreRecyclerAdapter<Jugador, AdapterJugadores.JugadoresViewHolder>(options) {

    class JugadoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewJugador: ImageView = itemView.findViewById(R.id.ivJugador)
        val textViewNombreJugador: TextView = itemView.findViewById(R.id.tvNombreJugador)
        val textViewAnioNacimiento: TextView = itemView.findViewById(R.id.tvAnioNacimiento)
        val textViewEdad: TextView = itemView.findViewById(R.id.tvEdad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
        return JugadoresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int, model: Jugador) {
        // Cargar la imagen del equipo utilizando Glide
        Glide.with(holder.itemView)
            .load(model.url_descarga)
            .into(holder.imageViewJugador)

        holder.textViewNombreJugador.text = model.nombre
        holder.textViewAnioNacimiento.text = model.anio_nacimiento.toString()
        holder.textViewEdad.text = model.edad.toString()

        // Establecer el listener de clic en el elemento de la vista
        holder.itemView.setOnClickListener {
        }
    }
}