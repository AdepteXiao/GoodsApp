package ru.adepteXiao.goodsapp.models.dbModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Фабрика для создания экземпляров моделей базы данных.
 *
 * @property goodDao DAO (Data Access Object) для работы с товарами в базе данных.
 */
class DatabaseModelFactory(private val goodDao: GoodDao) : ViewModelProvider.Factory {
    /**
     * Создает экземпляр модели базы данных указанного класса.
     *
     * @param modelClass Класс модели базы данных.
     * @return Экземпляр модели базы данных.
     * @throws IllegalArgumentException Если класс модели базы данных неизвестен.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DbModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DbModel(goodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}