package com.example.networktest

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BluetoothActivity : AppCompatActivity() {
    private lateinit var bluetoothRceeiver: BluetoothRecevier
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        //intentFilter.addAction(BiuetoothD)
        //bluetoothRecevier = BluetoothRecevier()



    }
}

class BluetoothRecevier : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

    }

}
