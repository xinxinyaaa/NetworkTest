package com.example.networktest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.networktest.databinding.ActivityNetworkItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.nio.charset.Charset

/*
class NetworkItemActivity (): AppCompatActivity(){
    val TAG = "NetworkItemActivity"
    private lateinit var binding: ActivityNetworkItemBinding

    val bundle = intent.getBundleExtra("bundle")
    val contact = bundle?.getParcelable<Person>("contact")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_network_item)
        binding = ActivityNetworkItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sendRequestWithOkHttp()

        //val bundle = intent.extras

        if (contact != null) {
            binding.text.text = "id = " + contact.id.toString() + "\nname = " + contact.full_name + "\nfollower = " + contact.followers.toString()
        }

    }
    private fun sendRequestWithOkHttp(){
        val client = OkHttpClient()
        val request = Request.Builder().url("https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_${contact?.id}.json").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG,"未能获取数据")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body
                var bytes = body?.bytes()
                var result = bytes?.toString(Charset.defaultCharset())
                Log.d(TAG,"成功获取数据"+result.toString())
                val bundle = intent.getBundleExtra("bundle")
                //val shu = bundle?.getInt("Int")
                val contact = bundle?.getParcelable<Person>("contact")
                val url = "https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_${contact?.id}.json"
                Log.d(TAG,"url is " + url)
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
*/
