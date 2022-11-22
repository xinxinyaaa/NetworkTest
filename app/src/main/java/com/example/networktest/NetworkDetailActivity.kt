package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.networktest.databinding.ActivityNetworkDetailBinding
import com.example.networktest.databinding.ActivityNetworkItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.nio.charset.Charset

class NetworkDetailActivity : AppCompatActivity(){
    val TAG = "NetworkDetailActivity"
    private lateinit var binding: ActivityNetworkDetailBinding
    private lateinit var cover: ImageView
    private lateinit var id: TextView
    private lateinit var full_name: TextView
    private lateinit var followers: TextView
    private lateinit var email: TextView
    private lateinit var description: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkDetailBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(view)
        cover = view.findViewById(R.id.cover)
        id = view.findViewById(R.id.id)
        full_name = view.findViewById(R.id.full_name)
        followers = view.findViewById(R.id.followers)
        email = view.findViewById(R.id.email)
        description = view.findViewById(R.id.description)
        sendRequestWithOkHttp()
    }

    /*fun initData(){
        binding = ActivityNetworkDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //binding.id.text = "id"
        //binding.cover.setImageResource()
        binding.fullName.text = "fullname"
        sendRequestWithOkHttp()
    }*/

    private fun sendRequestWithOkHttp(){
        val client = OkHttpClient()
        val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_$id.json").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG,"未能获取数据")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body
                var bytes = body?.bytes()
                var result = bytes?.toString(Charset.defaultCharset())
                Log.d(TAG,"成功获取数据"+result.toString())
                if (result != null){
                    parseJSONWithGSON(result)
                    Log.d(TAG,"request")
                }
            }
        })

    }

    private val listHandler by lazy { ListHandler() }
    class ListHandler(): Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val contactItem = msg.obj
            Log.d("TAG","NetworkActivity: " + contactItem)
            //val adapter = NetworkAdapter(context,contactItem)
            //binding.recyclerView.adapter = adapter
        }
    }


    private fun parseJSONWithGSON(jsonData: String){
        val gson = Gson()
        val typeOf = object : TypeToken<List<PersonDetail>>(){}.type
        val contactItem = gson.fromJson<List<PersonDetail>>(jsonData, typeOf)

        val msg = Message.obtain()
        msg.what = 1
        msg.obj = contactItem
        listHandler.sendMessage(msg)
    }

}