package com.example.networktest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.networktest.databinding.ActivityNetworkItemBinding

class NetworkItemActivity (): AppCompatActivity(){
    val TAG = "NetworkItemActivity"
    private lateinit var binding: ActivityNetworkItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_network_item)
        binding = ActivityNetworkItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val bundle = intent.extras
        val bundle = intent.getBundleExtra("bundle")
        //val shu = bundle?.getInt("Int")
        val contact = bundle?.getParcelable<Person>("contact")
        //getParcelableExtra<Person>("contact")
        Log.d(TAG,"onCreate" + contact)
        //binding.text.text = contact.toString()
        if (contact != null) {
            binding.text.text = "id = " + contact.id.toString() + "\nname = " + contact.full_name + "\nfollower = " + contact.followers.toString()
        }

    }
}