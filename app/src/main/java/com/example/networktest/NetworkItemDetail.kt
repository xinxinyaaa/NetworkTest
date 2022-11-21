package com.example.networktest

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.networktest.databinding.ActivityNetworkBinding
import com.example.networktest.databinding.ActivityNetworkItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.nio.charset.Charset

class NetworkItemDetail :AppCompatActivity(){
    val TAG = "NetworkItemDetail"
    //private lateinit var binding: ActivityNetworkItemBinding
    var binding = ActivityNetworkItemBinding.inflate(layoutInflater)
    val view = binding.root
    //setContentView(view)
    val id : TextView = view.findViewById(R.id.id)
    val full_name: TextView = view.findViewById(R.id.full_name)
    val followers: TextView = view.findViewById(R.id.followers)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initData()
        sendRequestWithOkHttp()
    }

    fun initData(){
        binding = ActivityNetworkItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //binding.id.text = "id"
        //binding.cover.setImageResource()
        binding.fullName.text = "fullname"
        sendRequestWithOkHttp()
    }

    private fun sendRequestWithOkHttp(){
        val client = OkHttpClient()
        val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_$id.json").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG","未能获取数据")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body
                var bytes = body?.bytes()
                var result = bytes?.toString(Charset.defaultCharset())
                Log.d("TAG","成功获取数据"+result.toString())
                if (result != null){
                    parseJSONWithGSON(result)
                    Log.d("TAG","request")
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
        val typeOf = object : TypeToken<List<PersonItem>>(){}.type
        val contactItem = gson.fromJson<List<PersonItem>>(jsonData, typeOf)
        /*for (person in contactItem){
            Log.d(TAG,"id is ${person.id}")
            Log.d(TAG,"full_name is ${person.full_name}")
            Log.d(TAG,"followers is ${person.followers}")
        }*/

        val msg = Message.obtain()
        msg.what = 1
        msg.obj = contactItem
        listHandler.sendMessage(msg)
    }

}