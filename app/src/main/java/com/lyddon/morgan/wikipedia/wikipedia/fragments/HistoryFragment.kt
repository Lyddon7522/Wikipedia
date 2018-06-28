package com.lyddon.morgan.wikipedia.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.lyddon.morgan.wikipedia.R
import com.lyddon.morgan.wikipedia.wikipedia.WikiApplication
import com.lyddon.morgan.wikipedia.wikipedia.adapters.ArticleListItemRecyclerAdapter
import com.lyddon.morgan.wikipedia.wikipedia.managers.WikiManager
import com.lyddon.morgan.wikipedia.wikipedia.models.WikiPage
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null
    private val adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)

        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)
        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val history = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(history as ArrayList<WikiPage>)
            activity.runOnUiThread{adapter.notifyDataSetChanged()}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       if (item!!.itemId == R.id.action_clear_history){
           // Show confirmation Alert
           activity.alert("Are you sure you want to clear history", "Confirm" ){
               yesButton {
                   adapter.currentResults.clear()
                   doAsync {
                       wikiManager!!.clearHistory()
                   }
                   activity.runOnUiThread { adapter.notifyDataSetChanged() }
               }
               noButton {

               }
           }.show()
       }

        return true
    }
}
