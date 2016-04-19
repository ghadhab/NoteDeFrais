package com.compta.firstak.notedefrais.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.compta.firstak.notedefrais.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class AppController extends Application {
	private ImageLoader mImageLoader;
	public static final String TAG = AppController.class.getSimpleName();
String reqGetIp;
	String ip;
	private RequestQueue mRequestQueue;
private String IP="http://firstak.com/comptabilite/get_adresse.php";
	private static AppController mInstance;


	private static String urlRacine="";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		getIPJsonObject();
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}


	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}



	public String  getIPJsonObject() {

		if (isNetworkAvailable()) {
			reqGetIp = "json_obj_req_get_Client";
			Log.i("UrlGetIP", IP);
			JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
					IP, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject ClientJsonFromJson) {
							Log.d("RespWSGetClient", ClientJsonFromJson.toString());

							try {
								ip= ClientJsonFromJson.getString("ip");
								Log.i("ip",ip);
								urlRacine="http://"+ip+":5689/Comptabilitee/rest/";
								Log.i("urlRacineFromAppContr",urlRacine);
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.i("ErrorMessageVolley", "Error: " + error.getMessage());
					VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
					Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
							R.anim.abc_slide_in_bottom);
				}
			});

			jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
					30000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetIp);
		} else {
			Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
					R.anim.abc_slide_in_bottom);

		}

		return this.urlRacine;
	}

	public String getUrlRacine()
	{
		return this.urlRacine;
	}





	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}



}