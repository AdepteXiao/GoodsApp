package ru.adepteXiao.goodsapp

import android.app.Application
import ru.adepteXiao.goodsapp.models.dbModel.GoodsRoomDatabase

class GoodsApp : Application() {

    val database: GoodsRoomDatabase by lazy { GoodsRoomDatabase.getDatabase(this) }

}