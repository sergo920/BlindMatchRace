package com.blindmatchrace;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blindmatchrace.classes.C;
import com.blindmatchrace.classes.GetBuoysTask;
import com.blindmatchrace.classes.SaveKmlTask;
import com.blindmatchrace.modules.JsonReader;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * Menu activity. Shows the menu screen.
 *
 */
public class ListOfRacesActivity extends Activity implements OnClickListener,LocationListener {

	// Application variables.
	
	private String Lati,Long,name="Stam";
	private boolean disableLocation = false;
	private String[] events;
	private String user = "", pass = "", event = "", fullUserName = "";

	// Views.
	private Button bRace1, bRace2, bRace3, bRace4, bRace5;
	private Marker currentPosition;
	private GoogleMap googleMap;
	private LocationManager locationManager;
	private Circle[] buoyRadiuses=new Circle[C.MAX_BUOYS];;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choiserace);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Disables lock-screen and keeps screen on.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initialize();
	}

	/**
	 * Initialize components.
	 */
	private void initialize() {
		// The user name, password and event number connected to the application.
		events=new String[5];
		user = getIntent().getStringExtra(C.USER_NAME);
		pass = getIntent().getStringExtra(C.USER_PASS);
		fullUserName = user + "_" + pass;
        
		// Initialize location ability.
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 3000, C.MIN_DISTANCE, this);
		
		//Lati = new DecimalFormat("##.######").format(currentPosition.getPosition().latitude);
		//Long = new DecimalFormat("##.######").format(currentPosition.getPosition().longitude);
		// Initializing Buttons.
		bRace1 = (Button) findViewById(R.id.bRace1);
		bRace2 = (Button) findViewById(R.id.bRace2);
		bRace3 = (Button) findViewById(R.id.bRace3);
		bRace4 = (Button) findViewById(R.id.bRace4);
		bRace5 = (Button) findViewById(R.id.bRace5);

		bRace1.setOnClickListener(this);
		bRace2.setOnClickListener(this);
		bRace3.setOnClickListener(this);
		bRace4.setOnClickListener(this);
		bRace5.setOnClickListener(this);
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.bRace1:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, events[4]);
			intent.putExtra(C.USER_PASS, pass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.bRace2:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, events[0]);
			intent.putExtra(C.USER_PASS, pass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.bRace3:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, events[1]);
			intent.putExtra(C.USER_PASS, pass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.bRace4:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, events[2]);
			intent.putExtra(C.USER_PASS, pass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.bRace5:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra(C.USER_NAME, user);
			intent.putExtra(C.EVENT_NUM, events[3]);
			intent.putExtra(C.USER_PASS, pass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Lati=new DecimalFormat("##.######").format(location.getLatitude());
		Long = new DecimalFormat("##.######").format(location.getLongitude());
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
