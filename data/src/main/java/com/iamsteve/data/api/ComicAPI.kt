package com.iamsteve.data.api

import com.iamsteve.data.dto.ComicDTO
import com.iamsteve.domain.util.Consts
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicAPI {

    @GET(Consts.COMIC_METADATA_FILE_NAME)
    fun getComics(): Single<List<ComicDTO>>

    @GET("assets/comic/{fileName}")
    fun getComicPanel(@Path("fileName") fileName: String): Single<ResponseBody>
}