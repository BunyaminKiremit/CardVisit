package com.example.cardvisit.ui.friend

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.cardvisit.configs.AppDataBase
import com.example.cardvisit.models.Card

class FriendViewModel(application: Application) : AndroidViewModel(application) {
    private val db: AppDataBase = Room.databaseBuilder(
        application.applicationContext,
        AppDataBase::class.java,
        "appDataBase"
    ).fallbackToDestructiveMigration().build()

    fun getAllCardsByGroup(group: String, callback: (List<Card>) -> Unit) {
        Thread {
            val cards = db.cardDao().getAllByGroup(group)
            callback(cards)
        }.start()
    }
}