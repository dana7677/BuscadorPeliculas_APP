package com.example.buscadorpeliculas_app.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsService {

    //http://www.omdbapi.com/?apikey=[yourkey]&
    // https://www.omdbapi.com/?i=tt3796198&apikey=59c1a3cf
    //'https://dummyjson.com/recipes/search?q=Margherita'
    @GET("?apikey=59c1a3cf")
    suspend fun getFilmList(
        @Query("i") query:String ="empty"
    ):FilmsResponse

    @GET("?apikey=59c1a3cf")
    suspend fun searchFilm(
        @Query("s") query:String="empty"
    ):SearchFilms
}