package com.example.testuserauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		TextView welcome = (TextView) findViewById(R.id.welcome);
		Intent i = getIntent();
		welcome.setText("Welcome "+i.getExtras().getString("name").toString());
	}
}
