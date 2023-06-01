package com.example.cardvisit.ui.home


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.cardvisit.configs.AppDataBase
import com.example.cardvisit.dao.CardDao
import com.example.cardvisit.models.Card


class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val db: AppDataBase = Room.databaseBuilder(
        application.applicationContext,
        AppDataBase::class.java,
        "appDataBase"
    ).fallbackToDestructiveMigration().build()

    fun getAllCards(callback: (List<Card>) -> Unit) {
        Thread {
            val cards = db.cardDao().getAll()
            callback(cards)

        }.start()
    }
    fun searchCardsByName(name: String, callback: (List<Card>) -> Unit) {
        Thread {
            val cards = db.cardDao().searchName(name)
            callback(cards)
        }.start()
    }
}

