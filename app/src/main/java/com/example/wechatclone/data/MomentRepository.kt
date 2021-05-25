package com.example.wechatclone.data

import android.util.Log
import com.example.wechatclone.network.Endpoints
import com.example.wechatclone.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

object MomentRepository {
    private const val TAG = "MomentRepository"
    private val request = ServiceBuilder.buildService(Endpoints::class.java)

    fun searchTweets(callback: (tweets: List<Tweet>) -> Unit) {
        thread {
            request.getTweets().enqueue(object : Callback<List<Tweet>> {
                override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { responseResults ->
                            callback(responseResults.filter {
                                with(it) { sender != null && (content != null || images != null) }
                            })
                        }
                    }
                }

                override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                    Log.e(TAG, "onFailure: request for tweets failed!", t)
                }
            })
        }
    }

    fun searchUserProfile(callback: (userProfile: UserProfile) -> Unit) {
        thread {
            request.getProfile().enqueue(object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback(it) }
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Log.e(TAG, "onFailure: request for UserProfile failed!", t)
                }
            })
        }
    }
}
