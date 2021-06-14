package edu.fbansept.demo.android.blocnote.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoteListe extends Note{

    private boolean trierParEtat;

    private List<Tache> listeTache = new ArrayList<>();

    public NoteListe(int id, String titre, boolean trierParEtat, List<Tache> listeTache) {
        super(id, titre);
        this.trierParEtat = trierParEtat;
        this.listeTache = listeTache;
    }

    public static NoteListe fromJson(JSONObject jsonNote) throws JSONException {

        List<Tache> listeTache = new ArrayList<>();

        JSONArray jsonListeTache = jsonNote.getJSONArray("listeTache");

        for(int i = 0; i < jsonListeTache.length(); i++) {
            JSONObject jsonTache = jsonListeTache.getJSONObject(i);
            listeTache.add(Tache.fromJson(jsonTache));
        }

        return new NoteListe(
                jsonNote.getInt("id"),
                jsonNote.getString("titre"),
                jsonNote.getBoolean("trierParEtat"),
                listeTache
        );
    }

    public static JSONObject toJson(NoteListe note) throws JSONException {
        JSONObject jsonNote = new JSONObject();
        jsonNote.put("id", note.getId());
        jsonNote.put("titre", note.getTitre());
        jsonNote.put("trierParEtat", note.isTrierParEtat());

        JSONArray jsonListeTache = new JSONArray();
        for(Tache tache : note.getListeTache()) {
            jsonListeTache.put(Tache.toJson(tache));
        }
        jsonNote.put("listeTache", jsonListeTache);

        return jsonNote;
    }

    public boolean isTrierParEtat() {
        return trierParEtat;
    }

    public void setTrierParEtat(boolean trierParEtat) {
        this.trierParEtat = trierParEtat;
    }

    public List<Tache> getListeTache() {
        return listeTache;
    }

    public void setListeTache(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }
}
