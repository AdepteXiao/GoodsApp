package ru.adepteXiao.goodsapp.models.dbModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.adepteXiao.goodsapp.models.Converters
import ru.adepteXiao.goodsapp.models.Good

class DbModel(private val goodDao: GoodDao) : ViewModel() {

    private val converters = Converters()

    fun insertGood(good: Good) {
        viewModelScope.launch {
            goodDao.insert(converters.goodToGoodEntity(good))
        }
    }

    fun deleteGood(good: Good) {
        viewModelScope.launch {
            goodDao.delete(converters.goodToGoodEntity(good))
        }
    }

    fun updateGood(good: Good) {
        viewModelScope.launch {
            goodDao.update(converters.goodToGoodEntity(good))
        }
    }

    fun getAllGoods(listener: AllGoodsGotListener) {
        viewModelScope.launch {
            val goods = mutableListOf<Good>()
            goodDao.getAll().take(1).collect { goodEntities ->
                goodEntities.mapTo(goods) { converters.goodEntityToGood(it) }
            }
            listener.onGoodsGot(goods)
        }
    }

    fun isInDb(listener: ItemCheckListener, good: Good) {
        viewModelScope.launch {
            val count = goodDao.hasItemWithBarcode(good.barcode)
            listener.onItemChecked(count != 0)
        }
    }
}