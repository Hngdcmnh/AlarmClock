package com.example.alarmclock.Fragment.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.ViewModel.AlarmViewModel
import com.example.alarmclock.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListAlarmFragment : Fragment() {

    lateinit var btAdd:ImageButton
    lateinit var v:View
    lateinit var alarmViewModel: AlarmViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = return inflater.inflate(R.layout.fragment_list_alarm, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        var listAlarmRecyclerView: RecyclerView = view.findViewById(R.id.rcv_listAlarm)

        listAlarmRecyclerView.layoutManager = LinearLayoutManager(this.context)
        
        alarmViewModel.readAllAlarm.observe(viewLifecycleOwner, Observer { it ->listAlarmRecyclerView.adapter = ListAlarmAdapter(it,alarmViewModel) })

        btAdd = view.findViewById(R.id.bt_addAlarm)

        btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment)
        }

    }


}


