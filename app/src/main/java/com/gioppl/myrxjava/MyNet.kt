package com.gioppl.myrxjava

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by GIOPPL on 2017/8/14.
 */
interface MyNet {
    @GET("top250")
    fun getTopMovie(@Query("start") start: Int, @Query("count") count: Int): Observable<MovieEntity>
}