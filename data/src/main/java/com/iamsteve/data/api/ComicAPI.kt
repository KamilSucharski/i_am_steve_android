package com.iamsteve.data.api

import com.iamsteve.data.dto.ComicDTO
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicAPI {

    @GET("comics.json")
    fun getComics(): Observable<List<ComicDTO>>

    @GET("assets/comic/{fileName}")
    fun getComicPanel(@Path("fileName") fileName: String): Observable<ResponseBody>
}