package com.yingyangfly.baselib.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VideoDao : BaseDao<VideoBean> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: VideoBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(list: MutableList<VideoBean>)

    @Query("select * from Video where type = :type")
    fun getAllVideoBean(type: String): MutableList<VideoBean>

    @Query("select * from Video where id = :id")
    fun getVideoById(id: Int): VideoBean

    @Query("delete from Video where id = :id")
    fun deleteById(id: Int)

    @Query("delete from Video")
    fun deleteAll()

    @Update
    override fun update(element: VideoBean)


}