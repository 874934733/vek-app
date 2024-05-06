package com.yingyangfly.baselib.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [VideoBean::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getVideoDao(): VideoDao

    companion object {
        @Volatile
        private var sInstance: AppDataBase? = null
        private const val DATA_BASE_NAME = "extract_audio_video.db"

        @JvmStatic
        fun getInstance(context: Context): AppDataBase? {
            if (sInstance == null) {
                synchronized(AppDataBase::class.java) {
                    if (sInstance == null) {
                        sInstance = createInstance(context)
                    }
                }
            }
            return sInstance
        }

        private fun createInstance(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context, AppDataBase::class.java, DATA_BASE_NAME
            ).allowMainThreadQueries().build()
        }
    }

}