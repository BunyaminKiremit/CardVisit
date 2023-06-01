package com.example.cardvisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.cardvisit.configs.AppDataBase
import com.example.cardvisit.ui.home.HomeFragment
import com.google.android.material.snackbar.Snackbar

class Detail : AppCompatActivity() {

    lateinit var txtNid:TextView
    lateinit var txtGroup:EditText
    lateinit var txtName:EditText
    lateinit var txtSurname:EditText
    lateinit var txtPhone:EditText
    lateinit var txtAdress:EditText
    lateinit var btnDelete:Button
    lateinit var btnUpdate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        txtNid=findViewById(R.id.txtNid)
        txtGroup=findViewById(R.id.txtGroup)
        txtName=findViewById(R.id.txtName)
        txtSurname=findViewById(R.id.txtSurname)
        txtPhone=findViewById(R.id.txtPhone)
        txtAdress=findViewById(R.id.txtAdress)
        btnDelete=findViewById(R.id.btnDelete)
        btnUpdate=findViewById(R.id.btnUpdate)

        val selectedCardNid = intent.getStringExtra("selectedCardNid")?.toIntOrNull() ?: 0
        val selectedCardCardGroup = intent.getStringExtra("selectedCardCardGroup")
        val selectedCardName = intent.getStringExtra("selectedCardName")
        val selectedCardSurName = intent.getStringExtra("selectedCardSurName")
        val selectedCardPhone = intent.getStringExtra("selectedCardPhone")
        val selectedCardAdrees = intent.getStringExtra("selectedCardAdrees")

        txtNid.text= selectedCardNid.toString()
        txtGroup.setText(selectedCardCardGroup)
        txtName.setText(selectedCardName)
        txtSurname.setText(selectedCardSurName)
        txtPhone.setText(selectedCardPhone)
        txtAdress.setText(selectedCardAdrees)

        btnDelete.setOnClickListener {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "appDataBase"
            ).build()
            val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
            val runnable = Runnable {

                val selectedCardNid = intent.getStringExtra("selectedCardNid")
                val selectedCard = db.cardDao().findById(selectedCardNid.toString())
                db.cardDao().delete(selectedCard)
                val snackbar = Snackbar.make(
                    mainLayout,
                    "Kayıt Silindi, Ana sayfaya yönlendiriliyorsunuz...",
                    Snackbar.LENGTH_LONG
                )
                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)

                        val intent = Intent(this@Detail, MainActivity::class.java)
                        intent.putExtra("fragment", HomeFragment::class.java.name)
                        startActivity(intent)
                    }
                })
                snackbar.show()
            }
            Thread(runnable).start()
        }

        btnUpdate.setOnClickListener {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "appDataBase"
            ).build()
            val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
            val runnable = Runnable {

                val updatedCardGroup = txtGroup.text.toString()
                val updatedCardName = txtName.text.toString()
                val updatedCardSurName = txtSurname.text.toString()
                val updatedCardPhone = txtPhone.text.toString()
                val updatedCardAdress = txtAdress.text.toString()

                val selectedCard = db.cardDao().findById(selectedCardNid.toString())

                selectedCard?.let {
                    it.cardGroup = updatedCardGroup
                    it.name = updatedCardName
                    it.surname = updatedCardSurName
                    it.phone = updatedCardPhone
                    it.address = updatedCardAdress
                    db.cardDao().update(it)

                    val snackbar = Snackbar.make(
                        mainLayout,
                        "Kayıt Güncellendi, Ana sayfaya yönlendiriliyorsunuz...",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            val intent = Intent(this@Detail, MainActivity::class.java)
                            intent.putExtra("fragment", HomeFragment::class.java.name)
                            startActivity(intent)
                        }
                    })
                    snackbar.show()
                }
            }
            Thread(runnable).start()
        }
    }
}