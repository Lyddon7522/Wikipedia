package com.lyddon.morgan.wikipedia.wikipedia.providers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.lyddon.morgan.wikipedia.wikipedia.models.Urls
import com.lyddon.morgan.wikipedia.wikipedia.models.WikiPage
import com.lyddon.morgan.wikipedia.wikipedia.models.WikiResult
import java.io.Reader

class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf(pair = "User-Agent" to "Lyddon Wikipedia")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?){
        Urls.getSearchUrl(term, skip, take).httpGet().responseObject(WikipediaDataDeserializer()){_, response, result ->
            if (response.statusCode != 200){
                throw Exception("Unable to get articles")
            }
            val(data, _) = result
            responseHandler.invoke(data as WikiResult)
        }
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?){
        Urls.getRandom(take).httpGet().responseObject(WikipediaDataDeserializer()){_, response, result ->
            if (response.statusCode != 200){
                throw Exception("Unable to get articles")
            }
            val(data, _) = result
            responseHandler.invoke(data as WikiResult)

        }
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult>{
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }
}