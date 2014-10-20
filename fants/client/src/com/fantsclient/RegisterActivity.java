package com.fantsclient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class RegisterActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		final Button button = (Button) findViewById(R.id.gotoRegister);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) // ���� �� ������
			{
				try {
					try {
						RefreshWish();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		final Button button1 = (Button) findViewById(R.id.loginBtn);
		button1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) // ���� �� ������
			{
				try {
					try {
						EditText mEdit1 = (EditText) findViewById(R.id.username1);
						EditText mEdit2 = (EditText) findViewById(R.id.password1);

						registerUser(mEdit1.getText().toString(), md5(mEdit1
								.getText().toString()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		try {
			try {
				RefreshWish();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	// ----------------------------------------------------------------

	// ----------------------------------------------------------------
	public void RefreshWish() throws ExecutionException, InterruptedException {

		new RestHelper().execute("");

	};

	public void registerUser(String username, String password)
			throws ExecutionException, InterruptedException {

		new RestHelperRegisterUser().execute(username, password);

	};

	private class RestHelperRegisterUser extends
			AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String text = null;
			try {
				String targetURL = "http://192.168.2.105:8080/service/users"
						+ "?username=" + params[0] + "&password=" + params[1];
				Client client = Client.create();
				WebResource service = client.resource(UriBuilder.fromUri(
						targetURL).build());
				// getting data

				HttpPost httppost = new HttpPost(targetURL);
				httppost.setHeader("version", "1");
				httppost.setHeader("ss_id", "2");

				HttpParams param = new BasicHttpParams();

				// param.setParameter("username", params[0]);
				// param.setParameter("password", params[1]);

				// ArrayList<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>();
				// nameValuePairs
				// .add(new BasicNameValuePair("username", params[0]));
				// nameValuePairs
				// .add(new BasicNameValuePair("password", params[1]));
				// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// httppost.setParams(param);
				// HttpParams httpParameters = new BasicHttpParams();
				HttpClient httpclient = new DefaultHttpClient();
				// httpclient.getParams().setParameter(
				// "http.protocol.content-charset", "Windows-1251");
				HttpResponse response = httpclient.execute(httppost);
				// text = service.header("version", "1").header("ss_id",
				// "2").accept(MediaType.TEXT_PLAIN).get(String.class);
				text = EntityUtils.toString(response.getEntity(), "UTF-8");

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
				TextView txt = (TextView) findViewById(R.id.temper);
				// txt.setText(results);
				txt.setText(results);
			}

			Intent myIntent = new Intent(RegisterActivity.this,
					MainActivity.class);
			RegisterActivity.this.startActivity(myIntent);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	private class RestHelper extends AsyncTask<String, Void, String> {

		private static final String targetURL = "http://192.168.2.105:8080/service/fants";

		@Override
		protected String doInBackground(String... params) {
			String text = null;
			try {
				Client client = Client.create();
				WebResource service = client.resource(UriBuilder.fromUri(
						targetURL).build());
				// getting data

				HttpGet httpget = new HttpGet(targetURL);
				httpget.setHeader("version", "1");
				httpget.setHeader("ss_id", "2");
				HttpClient httpclient = new DefaultHttpClient();
				// httpclient.getParams().setParameter(
				// "http.protocol.content-charset", "Windows-1251");
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
				TextView txt = (TextView) findViewById(R.id.temper);
				// txt.setText(results);
				txt.setText(results);
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