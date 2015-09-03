package com.example.testuserauth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	EditText email, password;
	Button signin,signup;
	AlertDialogManager alert=new AlertDialogManager();
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	JParser jp = new JParser();  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_login);
		email = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		signup =(Button) findViewById(R.id.signup);
		signin = (Button) findViewById(R.id.login);
		signup.setOnClickListener(this);
		signin.setOnClickListener(this);
		if(!isConnectingToInternet())
		{
			alert.showAlertDialog(this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			return;
		}
	}

	 private boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null)
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null)
	                  for (int i = 0; i < info.length; i++)
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                          return true;
	          }
	          return false;
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == signup)
		{
			Intent i = new Intent(this, SignUpActivity.class);
			startActivity(i);
		}
		if(v == signin)
		{
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login_id",email.getText().toString()));
			params.add(new BasicNameValuePair("login_pass",password.getText().toString()));
			String result=jp.openConnection("login", "POST", params);
			try{
				JSONObject json = new JSONObject(result); 
				if(json.getString("message").equalsIgnoreCase("user login success")){
					alert.showAlertDialog(this,
						"Login",
						"User Login Successfully", true);
					//Log.d("Data", json.getString("login_id").toString());
					 Intent i = new Intent(this, Welcome.class);
					 i.putExtra("name", json.getString("login_id").toString());
					 startActivity(i);
				}
				else {
					alert.showAlertDialog(this,
							"Login",
							"User Not Exist", true);
					return ;
				}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	 
	

}
