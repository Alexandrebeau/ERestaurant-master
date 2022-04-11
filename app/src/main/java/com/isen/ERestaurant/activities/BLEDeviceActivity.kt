package com.isen.ERestaurant.activities
import android.annotation.SuppressLint
import android.bluetooth.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.isen.ERestaurant.R
import com.isen.ERestaurant.adapter.BleServiceAdapter
import com.isen.ERestaurant.databinding.ActivityBledeviceBinding


@SuppressLint("MissingPermission")
class BleDeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBledeviceBinding
    private var bluetoothGatt : BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBledeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice?>(BLEScanActivity.ITEM_KEY)
        binding.deviceName.text = device?.name ?: "Nom inconnu"
        binding.deviceStatue.text = getString(R.string.ble_device_connected)

        connectToDevice(device)
    }


    private fun connectToDevice(device: BluetoothDevice?) {
        bluetoothGatt = device?.connectGatt(this, true, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                onConnectionStateChange(gatt, newState)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                val bleService= gatt?.services?.map { BLEService(it.uuid.toString(), it.characteristics) } ?: arrayListOf()
                val adapter= BleServiceAdapter(bleService)
                runOnUiThread {
                    binding.serviceList.layoutManager = LinearLayoutManager(this@BleDeviceActivity)
                    binding.serviceList.adapter = adapter
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }
        })
        bluetoothGatt?.connect()
    }

    private fun onConnectionStateChange (gatt: BluetoothGatt?, newState: Int){
        val state = if(newState == BluetoothProfile.STATE_CONNECTED) {
            getString(R.string.ble_device_connected)
        }else{
            getString(R.string.ble_device_disconnected)
        }
        runOnUiThread {
            binding.deviceStatue.text = state
        }
    }

    override fun onStop() {
        super.onStop()
        closeBluetoothGatt()
    }

    private fun closeBluetoothGatt() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}