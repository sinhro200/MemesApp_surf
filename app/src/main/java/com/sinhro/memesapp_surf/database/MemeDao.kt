package com.sinhro.memesapp_surf.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MemeDao {

    @Query("SELECT * from $DATABASE_TABLE_NAME")
    fun getAllMemes(): Observable<List<MemeInfo>>

    @Insert
    fun insert(meme: MemeInfo) : Completable

    @Query("DELETE FROM $DATABASE_TABLE_NAME")
    fun deleteAll() : Completable

    @Query("SELECT * from $DATABASE_TABLE_NAME WHERE id = :id")
    fun getById( id : Long) : Single<MemeInfo>

    @Delete
    fun delete(meme: MemeInfo) : Completable
}