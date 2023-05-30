package ru.adepteXiao.goodsapp.activities

import android.view.View

/**
 * Интерфейс слушателя кликов на элементы списка товаров.
 */
interface CardClickListener {

    /**
     * Вызывается при клике на элемент списка.
     *
     * @param view Кликнутый вид элемента списка.
     * @param position Позиция элемента в списке.
     */
    fun onCardClick(view: View, position: Int)

    /**
     * Вызывается при клике на кнопку удаления элемента списка.
     *
     * @param view Кликнутая кнопка удаления.
     * @param position Позиция элемента в списке.
     */
    fun onDelButtonClick(view: View, position: Int)
}