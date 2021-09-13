package com.example.alarmclock.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.DeleteMode
import com.example.alarmclock.Data.AlarmViewModel
import com.example.alarmclock.R

class ListAlarmFragment : Fragment(),DeleteMode {

    lateinit var btAdd:Button
    lateinit var v:View
    lateinit var delLinearLayout: LinearLayout
    lateinit var alarmViewModel:AlarmViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = return inflater.inflate(R.layout.fragment_list_alarm, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        var listAlarmRecyclerView: RecyclerView = view.findViewById(R.id.rcv_listAlarm)

        listAlarmRecyclerView.layoutManager = LinearLayoutManager(this.context)
        
//        AlarmViewModel.listAlarmLiveData.observe(viewLifecycleOwner, Observer { listAlarm: ArrayList<Alarm> -> listAlarmRecyclerView.adapter = ListAlarmAdapter(AlarmViewModel.listAlarm) })
        alarmViewModel.readAllAlarm.observe(viewLifecycleOwner, Observer { it ->listAlarmRecyclerView.adapter = ListAlarmAdapter(it,alarmViewModel) })


        btAdd = view.findViewById(R.id.bt_addAlarm)

        btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment)
        }

        delLinearLayout = view.findViewById(R.id.ll_deleteMode)

    }

    override fun setDeleteMode() {
        delLinearLayout.visibility = View.VISIBLE
    }


}


