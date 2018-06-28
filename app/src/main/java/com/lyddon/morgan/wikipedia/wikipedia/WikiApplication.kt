package com.lyddon.morgan.wikipedia.wikipedia

import android.app.Application
import com.lyddon.morgan.wikipedia.wikipedia.managers.WikiManager
import com.lyddon.morgan.wikipedia.wikipedia.providers.ArticleDataProvider
import com.lyddon.morgan.wikipedia.wikipedia.repositories.ArticleDatabaseOpenHelper
import com.lyddon.morgan.wikipedia.wikipedia.repositories.FavoritesRepository
import com.lyddon.morgan.wikipedia.wikipedia.repositories.HistoryRepository

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }
}