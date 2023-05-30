package ru.adepteXiao.goodsapp.models.dbModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.adepteXiao.goodsapp.models.Converters
import ru.adepteXiao.goodsapp.models.Good

/**
 * Модель базы данных для работы с товарами.
 *
 * @property goodDao DAO (Data Access Object) для работы с товарами в базе данных.
 */
class DbModel(private val goodDao: GoodDao) : ViewModel() {

    private val converters = Converters()

    /**
     * Вставить товар в базу данных.
     *
     * @param good Товар для вставки.
     */
    fun insertGood(good: Good) {
        viewModelScope.launch {
            goodDao.insert(converters.goodToGoodEntity(good))
        }
    }

    /**
     * Удалить товар из базы данных.
     *
     * @param good Товар для удаления.
     */
    fun deleteGood(good: Good) {
        viewModelScope.launch {
            goodDao.delete(converters.goodToGoodEntity(good))
        }
    }

    /**
     * Обновить информацию о товаре в базе данных.
     *
     * @param good Товар для обновления.
     */
    fun updateGood(good: Good) {
        viewModelScope.launch {
            goodDao.update(converters.goodToGoodEntity(good))
        }
    }

    /**
     * Получить все товары из базы данных.
     *
     * @param listener Слушатель для обработки полученных товаров.
     */
    fun getAllGoods(listener: AllGoodsGotListener) {
        viewModelScope.launch {
            val goods = mutableListOf<Good>()
            goodDao.getAll().take(1).collect { goodEntities ->
                goodEntities.mapTo(goods) { converters.goodEntityToGood(it) }
            }
            listener.onGoodsGot(goods)
        }
    }

    /**
     * Проверить наличие товара с указанным штрих-кодом в базе данных.
     *
     * @param listener Слушатель для обработки результата проверки.
     * @param barcode Штрих-код для проверки.
     */
    fun checkInDb(listener: ItemCheckListener, barcode: String) {
        viewModelScope.launch {
            goodDao.hasItemWithBarcode(barcode).collect { items ->
                if (items.isNotEmpty()) {
                    listener.onItemChecked(converters.goodEntityToGood(items.first()))
                } else {
                    listener.onItemChecked(null)
                }
            }
        }
    }
}