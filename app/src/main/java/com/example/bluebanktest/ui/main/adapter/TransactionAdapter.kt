package com.example.bluebanktest.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluebanktest.R
import com.example.bluebanktest.databinding.ItemTransactionBinding
import com.example.bluebanktest.domain.entity.Transaction

class TransactionAdapter :
    PagingDataAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionComparator) {


    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionViewHolder(
        item = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction,
            parent, false
        )
    )

    class TransactionViewHolder(private val item: ItemTransactionBinding) : RecyclerView.ViewHolder(item.root) {

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bindData(transaction: Transaction) {
            when(transaction.type){
                1-> {
                    item.textView2.text="انتقال به باکس"
                    item.imageView8.setImageResource(R.drawable.remittance_transfer)
                }
                2-> {
                    item.textView2.text="واریز به کارت"
                    item.imageView8.setImageResource(R.drawable.deposit_gift)
                }
                3-> {
                    item.textView2.text="خرید اینترنتی"
                    item.imageView8.setImageResource(R.drawable.atm)
                }
                4-> {
                    item.textView2.text="خرید از فروشگاه"
                    item.imageView8.setImageResource(R.drawable.fee)
                }
            }

            if(transaction.type==2){
                item.textView8.setBackgroundColor(R.color.highlights_tint)
            }else{
                item.textView8.background=null
            }

            item.textView7.text = transaction.date
            item.textView8.text = transaction.amount.toString()+" ریال"
        }

    }

    object TransactionComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }


    companion object Builder {
        fun build(
            recyclerView: RecyclerView,
        ) = TransactionAdapter().apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(recyclerView.context)
            }
            recyclerView.adapter = this

        }

    }

}