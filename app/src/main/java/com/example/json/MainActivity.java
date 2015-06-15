package com.example.json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	String _name, _password;
	private UserSignIn uSi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final EditText name = (EditText) findViewById(R.id.name);
		final EditText password = (EditText) findViewById(R.id.password);

		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				_name = name.getText().toString();
				_password = password.getText().toString();

				new CallToService().execute();

			}
		});
	}

	public class CallToService extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		protected String status;

		@Override
		protected void onPreExecute() {
			
			// Create a progress dialog

			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Loading...");
			progressDialog.setMessage("please wait...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(Void... params) {

			try {
				uSi = new UserSignIn();
				status = uSi.userLogin(_name, _password, getBaseContext());
				Log.d("status", status + " ");
			} catch (Exception e) {
				Log.d("SignInActivity Error", e.toString());
			}
			return status;
		}

		@Override
		protected void onPostExecute(final String success) {
			progressDialog.dismiss();

			if (success.equals("success")) {

				// login success

			} else {

				// invalid username and password

			}
		}

	}

}
