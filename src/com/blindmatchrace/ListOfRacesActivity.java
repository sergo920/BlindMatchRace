package com.blindmatchrace;





import java.io.IOException;







import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blindmatchrace.classes.C;
import com.blindmatchrace.classes.SendDataHThread;
import com.blindmatchrace.modules.JsonReader;







import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Menu activity. Shows the menu screen.
 *
 */
public class ListOfRacesActivity extends Activity implements OnClickListener {

	
	private String user = "", pass = "";
	private Button bRace1, bRace5;
    private EditText etEvent;

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
		user = getIntent().getStringExtra(C.USER_NAME);
		pass = getIntent().getStringExtra(C.USER_PASS);
				
		// Initializing Buttons.
       
		etEvent = (EditText) findViewById(R.id.etEvent);
		bRace1 = (Button) findViewById(R.id.bRace1);
		bRace5 = (Button) findViewById(R.id.bRace5);
		

		bRace1.setOnClickListener(this); 
		bRace5.setOnClickListener(this);
	}
	
	       @Override
	       public void onClick(View v) {
            switch (v.getId()) {
				case R.id.bRace1:
					new GetEventsTask().execute("random");
					break;
					
                case R.id.bRace5:
                	new GetEventsTask().execute(etEvent.getText().toString());
					break;
		   		}
         }
		
		
				
	

	

	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	private class GetEventsTask extends AsyncTask<String, String, String> {
   	 private ProgressDialog pDialog;
       private String name;
       private int Rnum;
   	@Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(ListOfRacesActivity.this);
           pDialog.setMessage("Getting Data ...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
          
   	}
   	
   	@Override
       protected String doInBackground(String... rn) {
   		String[] temp=new String[5];
   		int count=0;
   		try {
   			JSONObject json = JsonReader.readJsonFromUrl(C.URL_CLIENTS_TABLE);
   			JSONArray jsonArray = json.getJSONArray("positions");
   			if(rn[0].equals("random")){
   			for (int i = 0; i < jsonArray.length()&&count<5; i++) {
   				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
   				if (jsonObj.getString("info").startsWith("BuoyNum2")) {
   					String event = jsonObj.getString("event");
   					temp[count]=event;
   					count++;
   				
   				}
   			}
   			
   			Rnum=0+(int)(Math.random()*(4-0)+1);
   			return temp[Rnum];
   			}
   			else{
   				for (int i = 0; i < jsonArray.length(); i++) {
   	   				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
   	   				if (jsonObj.getString("event").startsWith(rn[0]))
   	   					return jsonObj.getString("event");
   				}
   			}
   		}
   		catch (JSONException e) {
   			Log.i(name, "JSONException");
   			return null;
   		}
   		catch (IOException e) {
   			Log.i(name, "IOException");
   			return null;
   		}
   		return null;
   	}
   	 protected void onPostExecute(String temp) {
   		 pDialog.dismiss();
   	// HandlerThread for creating a new user in the DB through thread. 		 
   		 if (!user.equals("Sailoradmin") && !user.equals("SailorAdmin")) {
			// Updates the SharedPreferences.
			SharedPreferences.Editor spEdit = LoginActivity.sp.edit();
			String fullUserName = user + "_" + pass + "_" + temp;
			spEdit.putString(C.PREFS_FULL_USER_NAME, fullUserName);
			spEdit.commit();
		}
   		 
   		if(LoginActivity.registerRequest){
   		LoginActivity.registerRequest = false;
   		SendDataHThread thread = new SendDataHThread("CreateNewUser");
	    thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        thread.setFullUserName(user + "_" + pass + "_" + temp);
	    thread.setEvent(temp);
		thread.setLat("0");
		thread.setLng("0");
		thread.setSpeed("0");
		thread.setBearing("0");

		thread.start();
   		}
   		 
   		 
   		 Intent intent =new Intent(ListOfRacesActivity.this,MenuActivity.class);
   		intent.putExtra(C.USER_NAME, user);
		intent.putExtra(C.USER_PASS, pass);
		intent.putExtra(C.EVENT_NUM, temp);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();

   		    	 }
   }

}
	
	
