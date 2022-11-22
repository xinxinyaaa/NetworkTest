package com.example.networktest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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

    private lateinit var contact:Person



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkDetailBinding.inflate(layoutInflater)
        binding.id.text = "1"
        val view = binding.root
        setContentView(view)
        cover = view.findViewById(R.id.cover)
        id = view.findViewById(R.id.id)
        full_name = view.findViewById(R.id.full_name)
        followers = view.findViewById(R.id.followers)
        email = view.findViewById(R.id.email)
        description = view.findViewById(R.id.description)
        val bundle = intent.getBundleExtra("bundle")
        contact = bundle?.getParcelable<Person>("contact")!!
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
        val url = "https://raw.staticdn.net/android10/Sample-Data/master/Android-CleanArchitecture/user_${contact.id}.json"
        Log.d(TAG,"url is " + url)
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG,"未能获取数据")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body
                var bytes = body?.bytes()
                var result = bytes?.toString(Charset.defaultCharset())

                Log.d(TAG,"onResponse " + result)


                if (result != null){
                    parseJSONWithGSON(result)
                    Log.d(TAG,"request")
                }
            }
        })

    }

    private val listHandler by lazy { ListHandler(this,binding) }
    class ListHandler(val context: Context, val binding: ActivityNetworkDetailBinding): Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val contactDetail = msg.obj as PersonDetail
            when(msg.what){
                1 -> Log.d("TAG","NetworkActivity: " + contactDetail)
            }
            val cover = contactDetail.cover_url
            val cover_url = cover?.replace("raw.githubusercontent.com","raw.staticdn.net")
            Glide.with(context).load(cover_url).into(binding.cover)
            binding.id.text = contactDetail.id.toString()
            binding.email.text = contactDetail.email.toString()
            binding.description.text = contactDetail.description.toString()
            binding.followers.text = contactDetail.followers.toString()
            binding.fullName.text = contactDetail.full_name.toString()

            //val adapter = NetworkAdapter(context,contactDetail)
            //binding.recyclerView.adapter = adapter
        }
    }


    private fun parseJSONWithGSON(jsonData: String){
        val gson = Gson()
        val typeOf = object : TypeToken<PersonDetail>(){}.type
        val contactDetail = gson.fromJson<PersonDetail>(jsonData, typeOf)
        Log.d(TAG,"contactDetail $contactDetail")
        

        val msg = Message.obtain()
        msg.what = 1
        msg.obj = contactDetail
        listHandler.sendMessage(msg)
    }

}