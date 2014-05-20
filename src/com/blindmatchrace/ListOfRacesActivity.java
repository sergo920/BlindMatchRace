package com.blindmatchrace;




import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.blindmatchrace.classes.C;
import com.blindmatchrace.modules.JsonReader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Menu activity. Shows the menu screen.
 *
 */
public class ListOfRacesActivity extends Activity {

	private String user = "", pass = "";
	private Button bRace1, bRace2, bRace3, bRace4, bRace5;


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

		bRace1 = (Button) findViewById(R.id.bRace1);
		bRace2 = (Button) findViewById(R.id.bRace2);
		bRace3 = (Button) findViewById(R.id.bRace3);
		bRace4 = (Button) findViewById(R.id.bRace4);
		bRace5 = (Button) findViewById(R.id.bRace5);

		bRace1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new GetEventsTask().execute(1);

				
			}
		});
		bRace2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new GetEventsTask().execute(2);

				
			}
		});
		bRace3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new GetEventsTask().execute(3);

				
			}
		});
		bRace4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new GetEventsTask().execute(4);

				
			}
		});
		bRace5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new GetEventsTask().execute(0);

				
			}
		});
		
				
	}


	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	
	private class GetEventsTask extends AsyncTask<Integer, String, String> {
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
       protected String doInBackground(Integer... rn) {
   		String[] temp=new String[5];
   		int count=0;
   		try {
   			JSONObject json = JsonReader.readJsonFromUrl(C.URL_CLIENTS_TABLE);
   			JSONArray jsonArray = json.getJSONArray("positions");
   			for (int i = 0; i < jsonArray.length()&&count<5; i++) {
   				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
   				if (jsonObj.getString("info").startsWith("BuoyNum2")) {
   					
   						String event = jsonObj.getString("event");
   						
   						
   						temp[count]=event;
   						count++;
   					
   				}
   			}
   			Rnum=rn[0];
   			return temp[Rnum];
   		}
   		catch (JSONException e) {
   			Log.i(name, "JSONException");
   			return null;
   		}
   		catch (IOException e) {
   			Log.i(name, "IOException");
   			return null;
   		}
   	}
   	 protected void onPostExecute(String temp) {
   		 pDialog.dismiss();
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
	
	
