package ru.adepteXiao.goodsapp.models.dbModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DatabaseModelFactory(private val goodDao: GoodDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DbModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DbModel(goodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}