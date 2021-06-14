package edu.fbansept.demo.android.blocnote.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Tache extends BaseObservable implements Serializable  {

    private Integer id;


    private boolean termine = false;

    private String texte = "";

    public Tache(int id, boolean termine, String texte) {
        this.id = id;
        this.termine = termine;
        this.texte = texte;
    }

    public Tache() {
    }

    public static Tache fromJson(JSONObject jsonRole) throws JSONException {
        return new Tache(
                jsonRole.getInt("id"),
                jsonRole.getBoolean("termine"),
                jsonRole.getString("texte")
        );
    }

    public static JSONObject toJson(Tache tache) throws JSONException {
        JSONObject jsonTache = new JSONObject();
        jsonTache.put("id", tache.id);
        jsonTache.put("texte", tache.texte);
        jsonTache.put("termine", tache.termine);
        return jsonTache;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public boolean isTermine() {
        return termine;
    }

    public void setTermine(boolean termine) {
        this.termine = termine;
    }
    @Bindable
    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }


}
