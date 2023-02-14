package com.iamsteve.data.data_source

import com.iamsteve.data.dto.ComicDto
import com.iamsteve.domain.util.Consts
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDataSource {
    @GET(Consts.COMIC_METADATA_FILE_NAME)
    fun getComics(): Single<List<ComicDto>>

    @GET("assets/comic/{fileName}")
    fun getComicPanel(@Path("fileName") fileName: String): Single<ResponseBody>
}