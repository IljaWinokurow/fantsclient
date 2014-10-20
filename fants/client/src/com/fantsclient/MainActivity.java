package com.fantsclient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Button button = (Button) findViewById(R.id.gotoRegister);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this,
						RegisterActivity.class);
				MainActivity.this.startActivity(myIntent);
			}
		});

		final Button loginButton = (Button) findViewById(R.id.loginBtn);
		loginButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				EditText mEdit1 = (EditText) findViewById(R.id.username1);
				EditText mEdit2 = (EditText) findViewById(R.id.password1);

				try {

					login1(mEdit1.getText().toString(), md5(mEdit2.getText()
							.toString()));
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	};

	public void login1(String username, String password)
			throws ExecutionException, InterruptedException {

		new RestHelper().execute(username, password);

	};

	public String md5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);

		for (byte b : hash) {
			int i = (b & 0xFF);
			if (i < 0x10)
				hex.append('0');
			hex.append(Integer.toHexString(i));
		}

		return hex.toString();
	}

	private class RestHelper extends AsyncTask<String, Void, String> {

		String targetURL = "http://192.168.2.105:8080/service/auth";

		@Override
		protected String doInBackground(String... params) {
			String text = null;
			try {
				Client client = Client.create();
				// client.addHeader("Authorization", "Basic "+encoded);
				targetURL = targetURL + "?username=" + params[0] + "&password="
						+ params[1];
				WebResource service = client.resource(UriBuilder.fromUri(
						targetURL).build());
				Log.w("targetURL", "future.get==========================="
						+ targetURL);
				// getting data

				HttpGet httpget = new HttpGet(targetURL);
				httpget.setHeader("version", "1");
				httpget.setHeader("ss_id", "2");
				HttpClient httpclient = new DefaultHttpClient();
				// httpclient.getParams().setParameter(
				// "http.protocol.content-charset", "Windows-1251");

				// assemble your GET or POST
				HttpResponse response = httpclient.execute(httpget);
				// text = service.header("version", "1").header("ss_id",
				// "2").accept(MediaType.TEXT_PLAIN).get(String.class);
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
				Log.w("myApp11", "future.get===========================" + text);

			} catch (Exception e) {
				Log.w("myApp1",
						"future.get==========================="
								+ e.getMessage());
				return e.getLocalizedMessage();
			}

			return text;
		}

		@Override
		protected void onPostExecute(String results) {
			Log.w("myApp3", "future.get===========================" + results);
			if (results != null) {
				if (results.equals("none")) {
					TextView myAwesomeTextView = (TextView) findViewById(R.id.errorText);
					myAwesomeTextView.setText("Username or password is wrong.");
				} else {
					Intent myIntent = new Intent(MainActivity.this,
							FantsActivity.class);
					MainActivity.this.startActivity(myIntent);
				}
				Toast.makeText(getApplicationContext(), results,
						Toast.LENGTH_LONG).show();
				// TextView txt = (TextView) findViewById(R.id.temper);
				// txt.setText(results);
				// txt.setText(results);
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

}