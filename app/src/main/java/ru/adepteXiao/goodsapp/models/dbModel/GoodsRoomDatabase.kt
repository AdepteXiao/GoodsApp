package ru.adepteXiao.goodsapp.models.dbModel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Абстрактный класс базы данных для товаров.
 */
@Database(entities = [GoodEntity::class], version = 1, exportSchema = false)
abstract class GoodsRoomDatabase : RoomDatabase() {
    /**
     * Возвращает DAO (Data Access Object) для работы с товарами.
     */
    abstract fun goodDao(): GoodDao

    companion object {
        @Volatile
        private var INSTANCE: GoodsRoomDatabase? = null

        /**
         * Получает экземпляр базы данных.
         *
         * @param context Контекст приложения.
         * @return Экземпляр базы данных.
         */
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