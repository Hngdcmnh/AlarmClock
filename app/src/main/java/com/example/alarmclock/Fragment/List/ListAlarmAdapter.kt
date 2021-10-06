package com.example.alarmclock.Fragment.List

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.MyApp
import com.example.alarmclock.ViewModel.AlarmViewModel
import com.example.alarmclock.R
import kotlin.coroutines.coroutineContext

class ListAlarmAdapter(var listAlarm: List<Alarm>, var alarmViewModel: AlarmViewModel):RecyclerView.Adapter<ListAlarmAdapter.ViewHolder>(){
    lateinit var v: View
    var listSelected: ArrayList<Alarm> = ArrayList()
    var enable: Boolean = false
    lateinit var mActionModeCallback: ActionMode.Callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listAlarm[position])
        var swOn = v.findViewById<SwitchCompat>(R.id.sw_on)

        //create delete mode
        mActionModeCallback = object : ActionMode.Callback2() {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.menuInflater.inflate(R.menu.delete, menu)
                mode.title = "Choose delete alarm"
                return true
            }

            override fun onGetContentRect(mode: ActionMode?, view: View?, outRect: Rect) {
                super.onGetContentRect(mode, view, outRect)
                outRect.set(10, 20, 20, 10)
            }

            override fun onPrepareActionMode(p0: ActionMode, p1: Menu?): Boolean {
                enable = true
                return false
            }

            override fun onActionItemClicked(p0: ActionMode, p1: MenuItem): Boolean {
                when(p1.itemId)
                {
                    R.id.del_menu ->{
                        for(alarm in listSelected)
                        {
                            alarmViewModel.deleteAlarm(alarm)
                            alarm.cancelAlarm(v.context)
                        }
                        listSelected.clear()
                        p0.finish()
                        return true
                    }
                }
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
                notifyDataSetChanged()
                listSelected.clear()
                enable = false
            }
        }



        // handle switch
        swOn.setOnClickListener {
            if(swOn.isChecked)
            {
                Log.e(this.javaClass.simpleName,"true")
                listAlarm[position].scheduleAlarm(v.context)
                Log.e(this.javaClass.simpleName,listAlarm[position].toString())
                listAlarm[position].on = true
                alarmViewModel.updateAlarm(listAlarm[position])
            }
            else
            {
                Log.e(this.javaClass.simpleName,"false")
                Log.e(this.javaClass.simpleName,listAlarm[position].toString())
                listAlarm[position].cancelAlarm(v.context)
                listAlarm[position].on = false
                alarmViewModel.updateAlarm(listAlarm[position])
            }
        }


        v.setOnLongClickListener {
            if(!enable) {
                (v.context as AppCompatActivity).startActionMode(mActionModeCallback,ActionMode.TYPE_PRIMARY)
                var alarm = listAlarm[holder.adapterPosition]
                holder.itemView.setBackgroundColor(Color.CYAN)
                holder.checkBox.visibility =View.VISIBLE
                holder.checkBox.isChecked=true
                listSelected.add(alarm)
            }
            Log.e(this.javaClass.simpleName,"longclick")
            return@setOnLongClickListener true
        }

        v.setOnClickListener {
            if(!enable) // update alarm
            {
                var bundle = Bundle()
                bundle.putSerializable("now alarm",listAlarm[position])
                v.findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment,bundle)
            }
            else // select delete alarm
            {
                var alarm = listAlarm[holder.adapterPosition]
                if(holder.checkBox.visibility == View.GONE)
                {
                    holder.itemView.setBackgroundColor(Color.CYAN)
                    holder.checkBox.visibility =View.VISIBLE
                    holder.checkBox.isChecked=true
                    listSelected.add(alarm)
                }
                else
                {
                    listSelected.remove(alarm)
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                    holder.checkBox.visibility =View.GONE
                }
            }

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
        var checkBox = view.findViewById<CheckBox>(R.id.cb_del)
        var swRepeat = view.findViewById<SwitchCompat>(R.id.sw_on)
        var repeatStatus = view.findViewById<TextView>(R.id.tv_repeatStatus)
        fun bindView(alarm: Alarm)
        {
            hour.text = String.format("%02d",alarm.hour)
            minute.text = String.format("%02d",alarm.minute)
            title.text = alarm.title
            itemView.setBackgroundColor(Color.TRANSPARENT)
            checkBox.visibility=View.GONE
            swRepeat.isChecked = alarm.on
            if(alarm.repeat==false)
            {
                repeatStatus.text = "Một lần"
            }
            else
            {
                var repeatStat=""
                if(alarm.Mon) repeatStat+="T2."
                if(alarm.Tue) repeatStat+="T3."
                if(alarm.Wed) repeatStat+="T4."
                if(alarm.Thu) repeatStat+="T5."
                if(alarm.Fri) repeatStat+="T6."
                if(alarm.Sat) repeatStat+="T7."
                if(alarm.Sun) repeatStat+="CN."
                repeatStatus.text = repeatStat
            }
        }
    }


}

