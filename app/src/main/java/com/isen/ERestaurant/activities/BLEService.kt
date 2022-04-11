package com.isen.ERestaurant.activities

import android.bluetooth.BluetoothGattCharacteristic
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class BLEService(val name: String,val characteristics: MutableList<BluetoothGattCharacteristic>):
ExpandableGroup<BluetoothGattCharacteristic>(name,characteristics)
{

}