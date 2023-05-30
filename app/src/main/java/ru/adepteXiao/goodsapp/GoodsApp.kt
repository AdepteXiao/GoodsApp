package ru.adepteXiao.goodsapp

import android.app.Application
import ru.adepteXiao.goodsapp.models.dbModel.GoodsRoomDatabase

/**
 * Приложение GoodsApp.
 *
 * Это подкласс класса [Application], используемый для инициализации базы данных приложения.
 * Он предоставляет доступ к экземпляру базы данных [GoodsRoomDatabase] с использованием ленивой инициализации.
 *
 * @property database Экземпляр базы данных [GoodsRoomDatabase].
 */
class GoodsApp : Application() {

    val database: GoodsRoomDatabase by lazy { GoodsRoomDatabase.getDatabase(this) }

}