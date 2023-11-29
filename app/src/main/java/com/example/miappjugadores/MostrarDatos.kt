package com.example.miappjugadores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miappjugadores.adaptador.AdapterJugadores
import com.example.miappjugadores.modelo.Jugador
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MostrarDatos : AppCompatActivity() {
    private lateinit var mFirebase: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AdapterJugadores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_datos)

        recyclerView = findViewById(R.id.recyclerViewJugadores)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        mFirebase = FirebaseFirestore.getInstance()

        val query: Query = mFirebase.collection("jugadores")

        val options: FirestoreRecyclerOptions<Jugador> = FirestoreRecyclerOptions.Builder<Jugador>()
            .setQuery(query, Jugador::class.java)
            .build()

        mAdapter = AdapterJugadores(mFirebase,options)
        recyclerView.adapter = mAdapter
        val btnGuardar = findViewById<Button>(R.id.btnNuevoRegistro)

        btnGuardar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Importante para comenzar y detener el escuchador de FirestoreRecyclerAdapter
    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }
}