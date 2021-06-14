package edu.fbansept.demo.android.blocnote.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import java.io.FileOutputStream;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.model.Utilisateur;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.ImageRequest;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.JsonObjectRequestWithToken;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.RequestManager;

public class UtilisateurController {

    Utilisateur utilisateurConnecte = null;

    private static UtilisateurController instance = null;

    private UtilisateurController() {
    }

    public static UtilisateurController getInstance() {

        if(instance == null) {
            instance = new UtilisateurController();
        }

        return instance;
    }



    public interface TelechargementUtilisateurListener {
        void onUtilisateurEstTelecharge(Utilisateur utilisateur);
    }

    public void getUtilisateurConnecte(Context context, TelechargementUtilisateurListener evenement) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequestWithToken(
                context,
                Request.Method.GET,
                context.getResources().getString(R.string.server_ip) + "user/utilisateur-connecte",
                null,
                jsonUtilisateur -> {

                    try {
                        Utilisateur utilisateur = Utilisateur.fromJson(jsonUtilisateur);
                        evenement.onUtilisateurEstTelecharge(utilisateur);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Erreur", error.toString())){
        };

        RequestManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public interface TelechargementImageProfilUtilisateurListener {
        void onImageProfilUtilisateurEstTelecharge(Bitmap bitmap);
    }

    public void getImageProfilUtilisateurConnecte(Context context, TelechargementImageProfilUtilisateurListener evenement) {

        ImageRequest request = new ImageRequest(Request.Method.GET,
                context.getResources().getString(R.string.server_ip) + "test/image-resource",
                response -> {

                    try {
                        if (response!=null) {

                            FileOutputStream outputStream;
                            //String name=<FILE_NAME_WITH_EXTENSION e.g reference.txt>;
                            //outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
                            //outputStream.write(response);
                            //outputStream.close();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);

                            evenement.onImageProfilUtilisateurEstTelecharge(bitmap);
                        }
                    } catch (Exception e) {
                        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                        e.printStackTrace();
                    }
                },

                error -> {
                    error.printStackTrace();
                });

        RequestManager.getInstance(context).addToRequestQueue(request);
    }
}
