package ru.adepteXiao.goodsapp.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.adepteXiao.goodsapp.databinding.GoodCardBinding
import ru.adepteXiao.goodsapp.models.Good
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale

/**
 *   Aдаптер для RecyclerView, который используется для отображения списка товаров.
 */
class GoodsRecycleListAdapter(private val clicksListener: CardClickListener) : RecyclerView.Adapter<GoodsRecycleListAdapter.GoodsViewHolder>() {
    class GoodsViewHolder(val binding: GoodCardBinding,
                         listener: CardClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onCardClick(it, adapterPosition)
            }
            binding.delButton.setOnClickListener {
                listener.onDelButtonClick(it, adapterPosition)
            }
        }
    }

    var goodsList = mutableListOf<Good>()

    /**
     *   Создает и инициализирует объект GoodsViewHolder для элемента списка.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GoodCardBinding.inflate(inflater, parent, false)
        return GoodsViewHolder(binding, clicksListener)
    }

    /**
     *   Метод, возвращающий количество элементов в списке товаров.
     */
    override fun getItemCount(): Int = goodsList.size


    /**
     *   Метод, связывающий данные товара с элементом списка.
     */
    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val good = goodsList[position]
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale("ru", "RU"))
        with(holder.binding) {
            titleTextView.text = good.name
            dateFrom.text = dateFormat.format(good.fromDate.time)
            dateTo.text = dateFormat.format(good.toDate.time)

            val toLocalDate = good.toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

            val daysToExpire = ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate)
            dayToExpire.text = if (daysToExpire >= 0) {
                daysToExpire.toString()
            } else {
                "Срок годности истек!"
            }
        }
    }

    /**
     *   Метод, очищающий список товаров и обновляющй адаптер.
     */
    fun clearGoods() {
        goodsList.clear()
        notifyDataSetChanged()
    }

    /**
     *   Метод, добавления товара в список и обновления адаптер.
     */
    fun addGood(good: Good){
        goodsList.add(good)
        notifyItemInserted(itemCount - 1)
    }

}