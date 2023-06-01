package com.example.cardvisit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nid")
    val nid: Int?,
    var cardGroup:String?,
    var name:String?,
    var surname:String?,
    var phone:String?,
    var address:String?

)


