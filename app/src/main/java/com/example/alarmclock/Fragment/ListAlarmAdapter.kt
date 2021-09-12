package com.example.alarmclock.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.Data.AlarmViewModel
import com.example.alarmclock.R

class ListAlarmAdapter(var listAlarm: ArrayList<Alarm>):RecyclerView.Adapter<ListAlarmAdapter.ViewHolder>() {
    lateinit var v:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listAlarm[position])
        var btDdel = v.findViewById<Button>(R.id.bt_delete)
//        var swRepeat = v.findViewById<Switch>(R.id.sw_repeat)

        btDdel.setOnClickListener {
            listAlarm[position].cancelAlarm(v.context)
            listAlarm.remove(listAlarm[position])
//            AlarmViewModel.listAlarmLiveData.value = listAlarm
        }

        v.setOnLongClickListener{
            v.findViewById<CheckBox>(R.id.cb_del).visibility = View.VISIBLE
            return@setOnLongClickListener true
        }
        v.setOnClickListener {
            var bundle:Bundle = Bundle()
            bundle.putInt("position",position)
            v.findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment,bundle)
        }



    }

    override fun getItemCount(): Int {
        return listAlarm.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var hour = view.findViewById<TextView>(R.id.tv_hourAlarm)
        var minute = view.findViewById<TextView>(R.id.tv_minuteAlarm)
        var title = view.findViewById<TextView>(R.id.tv_title)
//        var swRepeat = view.findViewById<Switch>(R.id.sw_repeat)
        fun bindView(alarm: Alarm)
        {
            hour.text = alarm.hour.toString()
            minute.text = alarm.minute.toString()
            title.text = alarm.title
//            if(alarm.repeat) swRepeat.textOn
//            else swRepeat.textOff
        }
    }
}