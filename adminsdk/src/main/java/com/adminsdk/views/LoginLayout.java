package com.adminsdk.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adminsdk.BuildConfig;
import com.adminsdk.R;
import com.adminsdk.listeners.OnRequestListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginLayout extends LinearLayout {

    private View parentView;
    private TypedArray typedArray;
    private OnRequestListener onRequestListener;

    private EditText etEmail,etNumber,etPassword;
    private Button btnSubmit;
    private boolean hasEmail,hasNumber,hasPassword;
    private Context context;

    public LoginLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_login, this);
    }

    public LoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public LoginLayout(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context,attrs);
    }

    public LoginLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context,attrs);
    }

    private void initViews(Context context,AttributeSet attributeSet){
        this.context = context;
        parentView = inflate(context, R.layout.layout_login, this);
        typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LoginLayout, 0, 0);

        etEmail = (EditText)parentView.findViewById(R.id.etEmail);
        etNumber = (EditText)parentView.findViewById(R.id.etNumber);
        etPassword = (EditText)parentView.findViewById(R.id.etPassword);
        btnSubmit = (Button) parentView.findViewById(R.id.btnSubmit);

        try {
            hasEmail = typedArray.getBoolean(R.styleable.LoginLayout_hasEmail,true);
            hasNumber = typedArray.getBoolean(R.styleable.LoginLayout_hasNumber,true);
            hasPassword = typedArray.getBoolean(R.styleable.LoginLayout_hasPassword,true);
        } finally {
            typedArray.recycle();
        }


        customiseLayout();
    }

    private View.OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            doSave();
        }
    };

    private void doSave(){
        String email = etEmail.getText().toString();
        String phone = etNumber.getText().toString();
        String password = etPassword.getText().toString();

        if(hasEmail){
            if(email.isEmpty() || email==""){
                return;
            }
        }

        if(hasNumber){
            if(phone.isEmpty() || phone==""){
                return;
            }
        }

        if(hasPassword){
            if(password.isEmpty() || password==""){
                return;
            }
        }

        makeRequest(email,phone,password);
    }

    private void setListener(){
        btnSubmit.setOnClickListener(clickListener);
    }

    public void setOnRequestListener(OnRequestListener onRequestListener){
        this.onRequestListener = onRequestListener;
    }

    private void customiseLayout(){
        enableDisableInputs();
        setListener();
    }


    private void enableDisableInputs(){
        etEmail.setVisibility(hasEmail ? VISIBLE : GONE);
        etNumber.setVisibility(hasNumber ? VISIBLE : GONE);
        etPassword.setVisibility(hasPassword ? VISIBLE : GONE);
    }

    private void makeRequest(final String email, final String number, final String password){
        onRequestListener.onInit();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.BASE_URL+"/saveUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onRequestListener.onSuccess(200,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error!=null && error.networkResponse!=null){
                            onRequestListener.onError(error.networkResponse.statusCode,error.getMessage());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("package", context.getPackageName());
                params.put("email", email);
                params.put("mobile", number);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
