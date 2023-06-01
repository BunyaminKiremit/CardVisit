package com.example.cardvisit.dao

import androidx.room.*
import com.example.cardvisit.models.Card


@Dao
interface CardDao {

    @Insert
    fun insert( card: Card ) : Long

    @Query("select * from card")
    fun getAll() : List<Card>

    @Query("select * from card where cardGroup=:group")
    fun getAllByGroup(group: String):List<Card>


    @Query("select * from card where name like :name ")
    fun searchName( name: String ) : List<Card>


    @Query("select * from card where nid =:nid")
    fun findById(nid: String) : Card

    @Delete
    fun delete(card: Card)

    @Update
    fun update( card: Card )


}

