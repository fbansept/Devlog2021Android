package edu.fbansept.demo.android.blocnote.utils.requestmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.fbansept.demo.android.blocnote.R;

public class StringRequestWithToken extends StringRequest {

    Context context;

    public StringRequestWithToken(Context context, int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.context = context;
    }


    @Override
    public Map<String, String> getHeaders() {
        SharedPreferences preference = context.getSharedPreferences(
                context.getResources().getString(R.string.fichier_preference), 0); // 0 - for private mode
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json; charset=UTF-8");
        params.put("Authorization", "Bearer " + preference.getString("token",""));
        return params;
    }
}
