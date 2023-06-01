package com.example.cardvisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.cardvisit.configs.AppDataBase
import com.example.cardvisit.databinding.ActivityInsertBinding
import com.example.cardvisit.models.Card
import com.example.cardvisit.ui.home.HomeFragment
import com.google.android.material.snackbar.Snackbar

class Insert : AppCompatActivity() {

    private lateinit var btnGrup: Button
    private lateinit var btnKaydet: Button
    private lateinit var editTxtAd: EditText
    private lateinit var editTxtSoyad: EditText
    private lateinit var editTxtTelefon: EditText
    private lateinit var editTxtAdres: EditText
    private lateinit var spinner: Spinner
    private lateinit var insertBinding: ActivityInsertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertBinding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(insertBinding.root)



        btnGrup = insertBinding.btnGrup
        btnKaydet = insertBinding.btnKaydet
        editTxtAd = insertBinding.editTxtAd
        editTxtSoyad = insertBinding.editTxtSoyad
        editTxtTelefon = insertBinding.editTxtTelefon
        editTxtAdres = insertBinding.editTxtAdres
        spinner = insertBinding.spinner

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.grup_sec,
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter

        btnKaydet.setOnClickListener {
            val selectedValue = spinner.selectedItem.toString()
            val name = editTxtAd.text.toString()
            val sName = editTxtSoyad.text.toString()
            val phone = editTxtTelefon.text.toString()
            val address = editTxtAdres.text.toString()

            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "appDataBase"
            ).build()

            val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
            val runnable = Runnable {
                val card = Card(null, selectedValue, name, sName, phone, address)
                db.cardDao().insert(card)


                val snackbar = Snackbar.make(mainLayout, "Kayıt başarılı, Ana sayfaya yönlendiriliyorsunuz...", Snackbar.LENGTH_LONG)
                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        // Snackbar kapatıldığında yapılacak işlemler
                        val intent = Intent(this@Insert, MainActivity::class.java)
                        intent.putExtra("fragment", HomeFragment::class.java.name)
                        startActivity(intent)
                    }
                })
                snackbar.show()
            }
            Thread(runnable).start()
        }

        btnGrup.setOnClickListener {
            spinner.performClick()
        }
    }
}