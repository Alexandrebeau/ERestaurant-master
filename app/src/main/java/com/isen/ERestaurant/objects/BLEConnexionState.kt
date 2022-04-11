package com.isen.ERestaurant.objects

import android.bluetooth.BluetoothProfile
import androidx.annotation.StringRes
import com.isen.ERestaurant.R

enum class BLEConnexionState (val state: Int,@StringRes val text: Int) {
    STATE_CONNECTING(BluetoothProfile.STATE_CONNECTING, R.string.connexion),
    STATE_CONNECTED(BluetoothProfile.STATE_CONNECTED, R.string.ble_device_connected),
    STATE_DISCONNECTED(BluetoothProfile.STATE_DISCONNECTED, R.string.ble_device_disconnected),
    STATE_DISCONNECTING(BluetoothProfile.STATE_DISCONNECTING, R.string.ble_device_disconnected);

    companion object {
        fun getBLEConnexionStateFromState(state: Int) = values().firstOrNull {
            it.state == state
        }
    }
}