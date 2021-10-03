package com.example.alarmclock.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.ViewModel.AlarmViewModel
import com.example.alarmclock.R
import java.util.*


class AddAlarmFragment : Fragment() {

    lateinit var alarmViewModel: AlarmViewModel

    lateinit var v :View
    lateinit var timePicker:TimePicker
    lateinit var btAdd:Button
    lateinit var edtTitle:EditText
    lateinit var checkRepeat: CheckBox
    lateinit var checkMon :CheckBox
    lateinit var checkTue :CheckBox
    lateinit var checkWed :CheckBox
    lateinit var checkThu :CheckBox
    lateinit var checkFri :CheckBox
    lateinit var checkSat :CheckBox
    lateinit var checkSun :CheckBox
    var check = false
    lateinit var repeatOptions:LinearLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_add_alarm, container, false)
        return v
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
        btAdd = view.findViewById<Button>(R.id.bt_add)
        timePicker = view.findViewById<TimePicker>(R.id.timePicker)
        edtTitle = view.findViewById(R.id.edt_title)
        checkRepeat = view.findViewById(R.id.cb_repeat)
        checkMon = view.findViewById<CheckBox>(R.id.cb_Mon)
        checkTue = view.findViewById<CheckBox>(R.id.cb_Tue)
        checkWed = view.findViewById<CheckBox>(R.id.cb_Wed)
        checkThu = view.findViewById<CheckBox>(R.id.cb_Thu)
        checkFri = view.findViewById<CheckBox>(R.id.cb_Fri)
        checkSat = view.findViewById<CheckBox>(R.id.cb_Sat)
        checkSun = view.findViewById<CheckBox>(R.id.cb_Sun)
        repeatOptions = view.findViewById<LinearLayout>(R.id.repeatOptions)

        var bundle = arguments

        var nowAlarm = bundle?.getSerializable("now alarm")
        if (nowAlarm != null) {
            setNowAlarm(nowAlarm as Alarm)
        }

        checkRepeat.setOnClickListener {
            if(checkRepeat.isChecked == true)
            {
                repeatOptions.visibility= View.VISIBLE
                var calendar = Calendar.getInstance()
                var today = calendar.get(Calendar.DAY_OF_WEEK)
                when(today)
                {
                    Calendar.MONDAY -> { checkMon.isChecked = true}
                    Calendar.TUESDAY-> { checkTue.isChecked = true}
                    Calendar.WEDNESDAY -> { checkWed.isChecked = true}
                    Calendar.THURSDAY -> { checkThu.isChecked = true}
                    Calendar.FRIDAY -> { checkFri.isChecked = true}
                    Calendar.SATURDAY -> { checkSat.isChecked = true}
                    Calendar.SUNDAY -> { checkSun.isChecked = true}
                }
            }
            else if(checkRepeat.isChecked == false)
            {
                repeatOptions.visibility= View.GONE
            }
        }

        btAdd.setOnClickListener {
            addNewAlarm()
//            Log.e("Key",checkMon.isChecked.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setNowAlarm(nowAlarm :Alarm) {
        if (nowAlarm != null) {
            timePicker.hour = nowAlarm.hour
        }
        if (nowAlarm != null) {
            timePicker.minute = nowAlarm.minute
        }
        if (nowAlarm != null) {
            if(nowAlarm.repeat)
            {
                checkRepeat.isChecked=true
                repeatOptions.visibility = View.VISIBLE
                checkMon.isChecked = nowAlarm.Mon
                checkTue.isChecked = nowAlarm.Tue
                checkWed.isChecked = nowAlarm.Wed
                checkThu.isChecked = nowAlarm.Thu
                checkFri.isChecked = nowAlarm.Fri
                checkSat.isChecked = nowAlarm.Sat
                checkSun.isChecked = nowAlarm.Sun
            }
            else checkRepeat.isChecked = false
        }

        if (nowAlarm != null) {
            nowAlarm.cancelAlarm(v.context)
        }

        if (nowAlarm != null) {
            Toast.makeText(this.requireContext(),"Remove",Toast.LENGTH_LONG)
            alarmViewModel.deleteAlarm(nowAlarm)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addNewAlarm() {
//        Log.e("Key",timePicker.hour.toString())
        var newAlarm = Alarm(timePicker.hour,timePicker.minute,"",edtTitle.text.toString(),checkRepeat.isChecked,checkMon.isChecked,checkTue.isChecked,checkWed.isChecked,checkThu.isChecked,checkFri.isChecked,checkSat.isChecked,checkSun.isChecked)

        alarmViewModel.addAlarm(newAlarm)
//        Log.e("NewAlarm",newAlarm.id.toString())

//        Log.e(this.javaClass.simpleName,this.context.toString())
        this.context?.let { newAlarm.scheduleAlarm(it) }
        findNavController().navigate(R.id.action_addAlarmFragment_to_listAlarmFragment)
    }

}