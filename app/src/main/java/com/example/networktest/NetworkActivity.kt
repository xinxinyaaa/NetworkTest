package com.example.networktest

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networktest.databinding.ActivityNetworkBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.nio.charset.Charset

class NetworkActivity : AppCompatActivity() {
    val TAG = "NetworkActivity"
    private lateinit var binding: ActivityNetworkBinding
    lateinit var viewModel: NetworkViewModel

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        context = this
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)
        initData()
    }

    fun initData(){
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NetworkAdapter(this,viewModel.contactList)
        binding.recyclerView.adapter = adapter
        setContentView(binding.root)
        sendRequestWithOkHttp()
    }

    private fun sendRequestWithOkHttp(){
        Log.d("TAG","Send")
        val client = OkHttpClient()
        //val request = Request.Builder().url("https://www.jianshu.com/p/289dfb1a839a").build()
        //val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/users.json").build()
        val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_1.json").build()
        Log.d("TAG","request")
        client.newCall(request).enqueue(object :Callback{

            override fun onFailure(call: Call, e: IOException) {
                //println("未能获取数据："+ Thread.currentThread().name)
                Log.d("TAG","未能获取数据")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body
                var bytes = body?.bytes()
                var result = bytes?.toString(Charset.defaultCharset())
                Log.d("TAG","成功获取数据"+result.toString())
                if (result != null){
                    //showResponse(responseData)
                    //parseJSONWithJSONObject(responseData)
                    parseJSONWithGSON(result)
                    Log.d("TAG","request")
                }
            }
        })
    }


    private val listHandler by lazy { ListHandler(this,binding) }
    class ListHandler(val context: Context,val binding: ActivityNetworkBinding):Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val contactList = msg.obj as List<Person>
            Log.d("TAG","NetworkActivity:"+contactList)
            val adapter = NetworkAdapter(context,contactList)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun parseJSONWithGSON(jsonData: String){
        val gson = Gson()
        val typeOf = object : TypeToken<List<Person>>(){}.type
        val contactList = gson.fromJson<List<Person>>(jsonData, typeOf)
        for (person in contactList){
            Log.d(TAG,"id is ${person.id}")
            Log.d(TAG,"full_name is ${person.full_name}")
            Log.d(TAG,"followers is ${person.followers}")
        }

        val msg = Message.obtain()
        msg.what = 1
        msg.obj = contactList
        listHandler.sendMessage(msg)
    }
}