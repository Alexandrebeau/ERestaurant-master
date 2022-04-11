package com.isen.ERestaurant.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.isen.ERestaurant.R
import com.isen.ERestaurant.activities.BLEService
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class BleServiceAdapter(bleService: List<BLEService>):ExpandableRecyclerViewAdapter<BleServiceAdapter.ServiceViewHolder,BleServiceAdapter.CharacteristicViewHolder>(bleService) {
    class ServiceViewHolder(itemView: View): GroupViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.serviceName)
        var serviceUUID: TextView = itemView.findViewById(R.id.serviceUUID)
        var serviceArrow: ImageView = itemView.findViewById(R.id.serviceArrow)
    }
    class CharacteristicViewHolder(itemView: View):ChildViewHolder(itemView){
        var characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        var characteristicUUID: TextView = itemView.findViewById(R.id.characteristicUUID)


    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ServiceViewHolder {
        TODO("Not yet implemented")
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): CharacteristicViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        if (group != null) {
            group.items
        }
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        TODO("Not yet implemented")
    }
}