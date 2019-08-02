package com.adminsdk.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.adminsdk.BuildConfig;
import com.adminsdk.R;
import com.adminsdk.adapter.AppsAdapter;
import com.adminsdk.listeners.AppClickListener;
import com.adminsdk.listeners.OnRequestListener;
import com.adminsdk.model.AppsModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppsLayout extends LinearLayout {

    private View parentView;
    private TypedArray typedArray;
    private OnRequestListener onRequestListener;

    private RecyclerView rvOurApps;
    private AppsAdapter appsAdapter;
    private Context context;
    private List<AppsModel> appsModelList = new ArrayList<>();

    public AppsLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_apps, this);
    }

    public AppsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public AppsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context,attrs);
    }

    public AppsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context,attrs);
    }

    private void initViews(Context context,AttributeSet attributeSet){
        this.context = context;
        parentView = inflate(context, R.layout.layout_apps, this);
        typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LoginLayout, 0, 0);

        rvOurApps = (RecyclerView) parentView.findViewById(R.id.rvOurApps);
        rvOurApps.setLayoutManager(new GridLayoutManager(context,3));

    }

    public void initiate(AppClickListener appClickListener,OnRequestListener onRequestListener){
        appsAdapter = new AppsAdapter(appsModelList,appClickListener);
        this.onRequestListener  = onRequestListener;
        rvOurApps.setAdapter(appsAdapter);
    }

    public void loadApps(){
        makeRequest();
    }

    private void makeRequest(){
        onRequestListener.onInit();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BuildConfig.BASE_URL+"/getOtherApps.php?"+context.getPackageName(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response!=null){
                            parseApps(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error!=null && error.networkResponse!=null){
                            onRequestListener.onError(error.networkResponse.statusCode,error.getMessage());
                        }
                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    private void parseApps(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                appsModelList.add(getModel(
                        jsonObject.getString("app_package"),
                        jsonObject.getString("app_name"),
                        jsonObject.getString("app_icon")
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        appsAdapter.notifyDataSetChanged();
        onRequestListener.onSuccess(200,data);
    }

    private AppsModel getModel(String packageName,String name,String icon){
        return new AppsModel(packageName,name,icon);
    }
}
