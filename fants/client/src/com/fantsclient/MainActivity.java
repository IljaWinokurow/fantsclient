package com.fantsclient;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Button button = (Button) findViewById(R.id.refrbutton);
		Log.w("myApp", "future.get===========================");
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

	// ----------------------------------------------------------------

	// ----------------------------------------------------------------
	public void RefreshWish() throws ExecutionException, InterruptedException {

		new RestHelper().execute("");

	};

	private class RestHelper extends AsyncTask<String, Void, String> {

		private static final String targetURL = "http://192.168.2.103/service/customer/1";

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