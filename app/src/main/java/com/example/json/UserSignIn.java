package com.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;

public class UserSignIn {

	public String setUserName, setUserPassword;

	InputStream is = null;
	String json = "";
	JSONObject jObj = null;
	StringBuilder sb;
	JSONStringer item = null;

	String status;

	public String userLogin(String name, String password, Context con) {

		try {

			// Insert below your http url + method name..

			HttpPost request = new HttpPost("Your HTTP Url + method name");
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type",
					"application/x-www-form-urlencoded");

			try {
				item = new JSONStringer().object().key("UserName").value(name)
						.key("Password").endObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			StringEntity entity = null;

			try {
				entity = new StringEntity(item.toString());
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = null;

			try {
				response = httpClient.execute(request);

				if (response.getStatusLine().getStatusCode() == 200) {

					HttpEntity responseEntity = response.getEntity();

					try {
						is = responseEntity.getContent();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						BufferedReader reader2 = new BufferedReader(
								new InputStreamReader(is, "iso-8859-1"), 8);
						sb = new StringBuilder();
						String line = null;
						while ((line = reader2.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						json = sb.toString();
					} catch (Exception e) {
						Log.e("Buffer Error",
								"Error converting result " + e.toString());
					}
					try {
						jObj = new JSONObject(json);

						// Json response is here errormessage and successfull..

						String errorMessage = jObj.optString("ErrorMessage");
						String authentication = jObj
								.optString("Authentication");
						String token = jObj.optString("Token");

						if (errorMessage
								.equalsIgnoreCase("Invalid UserName or Password")) {
							status = "false";
						} else if (authentication.equalsIgnoreCase("true")) {
							status = "true";
						}

					} catch (JSONException e) {
						Log.e("JSON Parser",
								"Error parsing data " + e.toString());
					}

				} else {
					status = null;
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
