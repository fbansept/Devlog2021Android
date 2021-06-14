package edu.fbansept.demo.android.blocnote.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.model.Note;
import edu.fbansept.demo.android.blocnote.model.NoteListe;
import edu.fbansept.demo.android.blocnote.model.NoteTexte;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.RequestManager;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.StringRequestWithToken;

public final class NoteController {

    private static NoteController instance = null;

    private NoteController() {
    }

    public static NoteController getInstance() {

        if(instance == null) {
            instance = new NoteController();
        }

        return instance;
    }


    public interface SuccesEcouteur {
        void onSuccesSauvegarde();
    }

    public interface ErreurEcouteur {
        void onErreurSauvegarde(String messageErreur);
    }

    public void sauvegarder(
            Context context,
            Note note,
            SuccesEcouteur ecouteurSucces,
            ErreurEcouteur ecouteurErreur) {

        String fin_url;

        if(note instanceof NoteTexte) {
            fin_url = "user/noteTexte";
        } else {
            fin_url = "user/noteListe";
        }

        StringRequestWithToken stringRequest = new StringRequestWithToken (
                context,
                Request.Method.POST,  context.getResources().getString(R.string.server_ip) + fin_url,
                token -> {
                    ecouteurSucces.onSuccesSauvegarde();
                },
                error -> {
                    ecouteurErreur.onErreurSauvegarde("Impossible de sauvegarder");
                }
        ){

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {

                    JSONObject jsonBody;

                    if(note instanceof NoteTexte) {
                        jsonBody = NoteTexte.toJson((NoteTexte) note);
                    } else {
                        jsonBody = NoteListe.toJson((NoteListe) note);
                    }

                    return jsonBody.toString().getBytes(StandardCharsets.UTF_8);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(stringRequest);

    }
}
