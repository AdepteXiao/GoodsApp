package ru.adepteXiao.goodsapp.models.dbModel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GoodEntity::class], version = 1, exportSchema = false)
abstract class GoodsRoomDatabase : RoomDatabase() {
    abstract fun goodDao(): GoodDao

    companion object {
        @Volatile
        private var INSTANCE: GoodsRoomDatabase? = null

        fun getDatabase(context: Context): GoodsRoomDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    GoodsRoomDatabase::class.java,
                    "goods_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}