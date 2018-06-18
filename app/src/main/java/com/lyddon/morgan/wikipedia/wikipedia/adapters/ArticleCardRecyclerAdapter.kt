package com.lyddon.morgan.wikipedia.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lyddon.morgan.wikipedia.R
import com.lyddon.morgan.wikipedia.wikipedia.holders.CardHolder

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardHolder>() {

    override fun getItemCount(): Int {
        return 15 //temporary
    }

    override fun onBindViewHolder(holder: CardHolder?, position: Int) {
        //this is where the view gets updated
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }
}