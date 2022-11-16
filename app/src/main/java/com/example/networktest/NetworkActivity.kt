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
    //public final val TAG: String = "NetworkActivity"
    private lateinit var binding: ActivityNetworkBinding
    lateinit var viewModel: NetworkViewModel
    
    private lateinit var context: Context
    //private lateinit var personList: ArrayList<PersonResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        context = this
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)
        initData()
        /*binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NetworkAdapter(this,viewModel.contactList)
        binding.recyclerView.adapter = adapter
        setContentView(binding.root)
        sendRequestWithOkHttp()*/
        /*binding.sendRequestBtn.setOnClickListener {
            //sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }*/
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
    
    


    /*private fun sendRequestWithHttpURLConnection(){
        //开启线程发起网络请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                val input = connection.inputStream
                //对获取到的输入流进行读取
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                connection?.disconnect()
            }
        }
    }*/
    private fun sendRequestWithOkHttp(){
        Log.d("TAG","Send")
        val client = OkHttpClient()
        //val request = Request.Builder().url("https://www.jianshu.com/p/289dfb1a839a").build()
        val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/users.json").build()
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


        /*thread {
            try {
                val client = OkHttpClient()
                //val request = Request.Builder().url("https://www.baidu.com").build()
                val request = Request.Builder().url("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/users.json").build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null){
                    //showResponse(responseData)
                    //parseJSONWithJSONObject(responseData)
                    parseJSONWithGSON(responseData)
                    Log.d("TAG","request")
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }*/
    }

    /*private fun showResponse(response:String){
        runOnUiThread {
            //进行UI操作，显示结果在界面上
            binding.responseText.text = response

        }

    }*/
    /*private fun parseJSONWithJSONObject(jsonData: String){
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val full_name = jsonObject.getString("full_name")
                val followers = jsonObject.getString("followers")
                Log.d("MainActivity","id is $id")
                Log.d("MainActivity","full_name is $full_name")
                Log.d("MainActivity","id is $followers")

            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/
    private val listHandler by lazy { ListHandler(this,binding) }
    class ListHandler(val context: Context,val binding: ActivityNetworkBinding):Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            //when(msg)
            val contactList = msg.obj as List<ListResponse>
            Log.d("TAG","NetworkActivity:"+contactList)
            val adapter = NetworkAdapter(context,contactList)
            binding.recyclerView.adapter = adapter

        }

    }

    private fun parseJSONWithGSON(jsonData: String){
        val gson = Gson()
        val typeOf = object : TypeToken<List<ListResponse>>(){}.type
        val contactList = gson.fromJson<List<ListResponse>>(jsonData, typeOf)
        //personList = Gson().fromJson(jsonData,typeOf)
        for (person in contactList){
            Log.d(TAG,"id is ${person.id}")
            Log.d(TAG,"full_name is ${person.name}")
            Log.d(TAG,"followers is ${person.followers}")
        }

        val msg = Message.obtain()
        msg.what = 1
        msg.obj = contactList
        listHandler.sendMessage(msg)

        //listHandler.sendEmptyMessage(1)
    }
}
/*

Handler() {
    send()
    handleMessage()
}

Handler handler = new Handler() {
    @override
    HandlerMessage() {

    }
}

secondThread()
{
    main() {
        handler.send(new Message(1))
    }
}*/
