package com.lyddon.morgan.wikipedia.wikipedia.managers

import com.lyddon.morgan.wikipedia.wikipedia.models.WikiPage
import com.lyddon.morgan.wikipedia.wikipedia.models.WikiResult
import com.lyddon.morgan.wikipedia.wikipedia.providers.ArticleDataProvider
import com.lyddon.morgan.wikipedia.wikipedia.repositories.FavoritesRepository
import com.lyddon.morgan.wikipedia.wikipedia.repositories.HistoryRepository
import java.lang.reflect.InvocationHandler

class WikiManager(private val provider: ArticleDataProvider, private val favoritesRepository: FavoritesRepository, private val historyRepository: HistoryRepository) {
    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>?{
        if (historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage>?{
        if (favoritesCache == null)
            favoritesCache = favoritesRepository.getAllFavorites()
        return favoritesCache
    }

    fun addFavorite(page: WikiPage){
        favoritesCache?.add(page)
        favoritesRepository.addFavorite(page)
    }

    fun removeFavorite(pageId: Int){
        favoritesRepository.removeFavoriteById(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int): Boolean{
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage){
        historyCache?.add(page)
        historyRepository.addPage(page)
    }

    fun clearHistory(){
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory?.forEach{historyRepository?.removePageById(it.pageid!!)}
    }
}