package com.blindmatchrace;

/***
 *    Application Name : BlindMatchRace 
 *    Author : Sergey Molchanov
 *    Android SDK : 2.2
 */

import java.util.Calendar;

import com.blindmatchrace.classes.C;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class SetTimeDateActivity extends Activity implements OnClickListener {

	// Widget GUI
	private Button bCalendar, bTimePicker,bSet;
	private EditText txtDate, txtTime;

	private String user,event;
	// Variable for storing current date and time

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settimedate);

		user = getIntent().getStringExtra(C.USER_NAME);
		event = getIntent().getStringExtra(C.EVENT_NUM);
		
		bSet = (Button) findViewById(R.id.bSet);
		bCalendar = (Button) findViewById(R.id.bCalendar);
		bTimePicker = (Button) findViewById(R.id.bTimePicker);

		txtDate = (EditText) findViewById(R.id.txtDate);
		txtTime = (EditText) findViewById(R.id.txtTime);
        
		bSet.setOnClickListener(this);
		bCalendar.setOnClickListener(this);
		bTimePicker.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v == bCalendar) {

			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			C.mYear = c.get(Calendar.YEAR);
			C.mMonth = c.get(Calendar.MONTH);
			C.mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							txtDate.setText(dayOfMonth + "-"
									+ (monthOfYear + 1) + "-" + year);

						}
					}, C.mYear, C.mMonth, C.mDay);
			dpd.show();
		}
		if (v == bTimePicker) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			C.mHour = c.get(Calendar.HOUR_OF_DAY);
			C.mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							txtTime.setText(hourOfDay + ":" + minute);
						}
					}, C.mHour, C.mMinute, false);
			tpd.show();
		}
		if (v==bSet){
			Intent intent = new Intent(this, AdminActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, event);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
}