package ru.adepteXiao.goodsapp.models.dbModel

import ru.adepteXiao.goodsapp.models.Good

/**
 * Интерфейс слушателя для проверки наличия товара.
 */
interface ItemCheckListener {
    /**
     * Вызывается при проверке наличия товара.
     *
     * @param response Результат проверки наличия товара. Может быть либо экземпляром класса [Good], если товар найден,
     * либо null, если товар не найден.
     */
    fun onItemChecked(response: Good?)
}