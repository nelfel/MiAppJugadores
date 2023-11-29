package com.example.miappjugadores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.miappjugadores.modelo.Jugador
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etNombreJugador = findViewById<EditText>(R.id.etNombreJugador)
        val etAnioNacimiento = findViewById<EditText>(R.id.etAnioNacimiento)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etNumUrl = findViewById<EditText>(R.id.etNumUrl)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombreJugador = etNombreJugador.text.toString()
            val anioNacimiento = etAnioNacimiento.text.toString().toInt()
            val edad = etEdad.text.toString().toInt()
            val urlDescarga = etNumUrl.text.toString()

            val nuevoJugador = Jugador(
                nombreJugador,
                anioNacimiento,
                edad,
                urlDescarga
            )

            agregarJugadorFirestore(nuevoJugador)
            val intent = Intent(this, MostrarDatos::class.java)
            startActivity(intent)
        }

    }
    private fun agregarJugadorFirestore(jugador: Jugador) {
        val jugadoresCollection = firestore.collection("jugadores")

        jugadoresCollection
            .whereEqualTo("nombre", jugador.nombre)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    val equipoMap = hashMapOf(
                        "nombre" to jugador.nombre,
                        "anio_nacimiento" to jugador.anio_nacimiento,
                        "edad" to jugador.edad,
                        "url_descarga" to jugador.url_descarga
                    )

                    jugadoresCollection
                        .add(equipoMap)
                        .addOnSuccessListener { documentReference ->
                            println("Jugador agregado con ID: ${documentReference.id}")
                            limpiarFormulario()
                        }
                        .addOnFailureListener { e ->
                            println("Error al agregar el Jugador: $e")
                        }
                } else {
                    println("El Jugador ya existe en la colección")
                }
            }
            .addOnFailureListener { e ->
                println("Error al verificar la existencia del Jugador: $e")
            }
    }

    private fun limpiarFormulario() {
        findViewById<EditText>(R.id.etNombreJugador).text.clear()
        findViewById<EditText>(R.id.etAnioNacimiento).text.clear()
        findViewById<EditText>(R.id.etEdad).text.clear()
        findViewById<EditText>(R.id.etNumUrl).text.clear()
    }

}