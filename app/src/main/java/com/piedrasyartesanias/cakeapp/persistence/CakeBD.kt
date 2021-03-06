package com.piedrasyartesanias.cakeapp.persistence

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piedrasyartesanias.cakeapp.persistence.dao.ProductDao
import com.piedrasyartesanias.cakeapp.persistence.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CakeBD : RoomDatabase() {

    abstract fun productDao():ProductDao

    companion object {
        private var instance:CakeBD? = null

        fun getInstance(context: Context): CakeBD? {
            if (instance == null) {
                synchronized(CakeBD::class) {
                    instance = Room.databaseBuilder(context.applicationContext, CakeBD::class.java, "cake_data_base")
                        .fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}