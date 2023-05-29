package ru.adepteXiao.goodsapp.models.dbModel

import ru.adepteXiao.goodsapp.models.Good

interface AllGoodsGotListener {
    fun onGoodsGot(goods: MutableList<Good>)
}