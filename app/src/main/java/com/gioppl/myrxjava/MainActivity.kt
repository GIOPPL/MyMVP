package com.gioppl.myrxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv_main)
    var tv_main:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        tv_main= findViewById(R.id.tv_main) as TextView?
        getMovies()
    }

    private fun getMovies() {
        var moviesUrl="https://api.douban.com/v2/movie/"
        var retrofit=Retrofit
                .Builder().baseUrl(moviesUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        var myNet=retrofit.create(MyNet::class.java)
        myNet.getTopMovie(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<MovieEntity>() {
                    override fun onError(e: Throwable?) {
                        tv_main!!.setText(e!!.message)
                    }

                    override fun onCompleted() {
                        Toast.makeText(this@MainActivity, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    override fun onNext(t: MovieEntity?) {
                        tv_main!!.setText(t!!.subjects!!.get(0).title)
                    }
                })



//        var call=myNet.getTopMovie(0,10)
//        call.enqueue(object : Callback<MovieEntity>{
//            override fun onFailure(call: Call<MovieEntity>?, t: Throwable?) {
//                tv_main!!.setText("错误"+t!!.message)
//            }
//
//            override fun onResponse(call: Call<MovieEntity>?, response: Response<MovieEntity>?) {
//                tv_main!!.setText("正确"+ response!!.body().title!!)
//            }
//
//        })
    }
}
