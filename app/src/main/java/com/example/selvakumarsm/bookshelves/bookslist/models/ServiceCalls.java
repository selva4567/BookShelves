package com.example.selvakumarsm.bookshelves.bookslist.models;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.selvakumarsm.bookshelves.bookslist.BookShelvesApplication;
import com.example.selvakumarsm.bookshelves.bookslist.Constants;

import org.json.JSONObject;


/**
 * Created by selvakumar.sm on 1/17/2018.
 */
//Class to handle the network calls
public class ServiceCalls {

    private final String TAG = ServiceCalls.class.getSimpleName();
    private RequestQueue requestQueue;
    private ServiceCallsCallback callback;

    public ServiceCalls(ServiceCallsCallback callback){
        this.callback = callback;
        requestQueue = BookShelvesApplication.getVolleyRequestQueue();
    }

    public void makeRequest(String requestTag, final int responseCode){
        Log.d(TAG, "Sending request....");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.BOOKSlIST_END_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: trigger image request using setimageurl
                Log.d(TAG, response.toString());
                callback.onSuccess(response, responseCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
                String errorMessage = error.getMessage();
                if(error instanceof TimeoutError){
                    errorMessage = Constants.TIMEOUTERROR;
                }else if(error instanceof NoConnectionError){
                    errorMessage = Constants.NOCONNECTIONERROR;
                }else {
                    errorMessage = Constants.OTHERERRORS;
                }
                callback.onError(errorMessage, responseCode);
            }
        });
        jsonObjectRequest.setTag(requestTag);
        requestQueue.add(jsonObjectRequest);
    }

    public void cancelPendingRequests(String requestTag){
        if(requestQueue != null)
            requestQueue.cancelAll(requestTag);
    }

    public interface ServiceCallsCallback {
        void onSuccess(JSONObject responseObject, int responseCode);
        void onError(String error, int responseCode);
    }
}
