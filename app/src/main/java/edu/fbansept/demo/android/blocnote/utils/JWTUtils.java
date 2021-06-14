package edu.fbansept.demo.android.blocnote.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import edu.fbansept.demo.android.blocnote.R;

public class JWTUtils {

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static JSONObject getBody(String JWTEncoded) throws UnsupportedEncodingException, JSONException {
        String[] split = JWTEncoded.split("\\.");
        return  new JSONObject(getJson(split[1]));
    }

    public static boolean isTokenValide(Context context){
        SharedPreferences preference = context.getSharedPreferences(
                context.getResources().getString(R.string.fichier_preference), 0);

        String token = preference.getString("token",null);
        if(token != null) {
            try {

                //La date étant stockée en seconde, on la multiplie par 1000;
                Date expiration = new Date(JWTUtils.getBody(token).getLong("exp") * 1000);

                if(expiration.after(new Date())){
                    return true;
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                return false;
            }
        }
        return false;
    }
}