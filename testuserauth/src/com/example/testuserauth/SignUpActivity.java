package com.example.testuserauth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity implements OnClickListener{

	EditText username, pass, cpass, email, phone;
	Button register;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	JParser jp =new JParser();
	
	AlertDialogManager alert=new AlertDialogManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		StrictMode.setThreadPolicy(policy);
		username = (EditText) findViewById(R.id.user1);
		pass = (EditText) findViewById(R.id.pass1);
		register = (Button) findViewById(R.id.sign_up);
		register.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == register)
		{
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login_id",username.getText().toString()));
			params.add(new BasicNameValuePair("login_pass",pass.getText().toString()));
			String result=jp.openConnection("register", "POST", params);
		//	Log.d("Result", result); 
			try{
				JSONObject json= new JSONObject(result);
				if(!json.getString("login_id").isEmpty())
				{
					alert.showAlertDialog(this,
							"Login",
							"User register Successfully", true);
					Intent i =new Intent(this, Welcome.class);
					i.putExtra("name", json.getString("login_id").toString());
					startActivity(i);
				}
				else
				{
					alert.showAlertDialog(this,
							"Register",
							"User already exist ", false);
					return;
				}
				
			}catch(Exception e)
			{ 
				e.printStackTrace();
			}
		}
	}

}
