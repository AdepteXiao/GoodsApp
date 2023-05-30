package ru.adepteXiao.goodsapp.models.dbModel

import ru.adepteXiao.goodsapp.models.Good

/**
 * Интерфейс слушателя для получения всех товаров.
 */
interface AllGoodsGotListener {

    /**
     * Вызывается при успешном получении всех товаров.
     *
     * @param goods Список товаров, полученных из базы данных.
     */
    fun onGoodsGot(goods: MutableList<Good>)
}