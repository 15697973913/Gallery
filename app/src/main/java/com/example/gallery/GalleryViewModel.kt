package com.example.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive: LiveData<List<PhotoItem>>
        get() {
            return _photoListLive
        }

     fun fetchData() {
        val stringRequest = StringRequest(Request.Method.GET, getUrl(),
            Response.Listener {
                //成功后的监听
                _photoListLive.value = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                //失败后的监听
                Log.d("jiege", it.toString())
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }

    private fun getUrl(): String {
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${keyWords.random()}&per_page=100"
    }

    private var keyWords = arrayOf("cat", "dog", "car", "beauty", "computer", "flower", "animal")

}