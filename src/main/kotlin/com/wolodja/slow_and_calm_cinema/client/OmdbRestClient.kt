package com.wolodja.slow_and_calm_cinema.client

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal

@Component
class OmdbRestClient {

    private val omdbUrl: String = "http://www.omdbapi.com"

    private var apiKey: String? = System.getProperty("omdb.api-key")


    private val log = LoggerFactory.getLogger(this.javaClass)

    private val webClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .baseUrl(omdbUrl!!)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs {
                    it.defaultCodecs().maxInMemorySize(5 * 1024 * 1024) //max buffer for rest response: 5 MB
                }
                .build())
        .build()

    fun getMovie(imdbId: String): OmdbMovieResponse? {
        return try {
            webClient
                .get()
                .uri { it.queryParam("apikey", apiKey).queryParam("i", imdbId).build() }
                .retrieve()
                .bodyToMono(OmdbMovieResponse::class.java).block()!!
        } catch (e: Exception) {
            log.error(e.message, e)
            null
        }
    }
}

data class OmdbMovieResponse(
    val imdbID: String,
    val Title: String,
    val Released: String,
    val Runtime: String,
    val imdbRating: BigDecimal,
    val Plot: String,
    val Director: String,
)