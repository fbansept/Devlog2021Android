package edu.fbansept.demo.android.blocnote.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteTexte extends Note{

    private String texte;
    private String url;

    public NoteTexte() {
    }

    public NoteTexte(int id, String titre, String texte, String url) {
        super(id, titre);
        this.texte = texte;
        this.url = url;
    }

    public static NoteTexte fromJson(JSONObject jsonNote) throws JSONException {
        return new NoteTexte(
                jsonNote.getInt("id"),
                jsonNote.getString("titre"),
                jsonNote.getString("texte"),
                jsonNote.getString("url")
        );
    }

    public static JSONObject toJson(NoteTexte note) throws JSONException {
        JSONObject jsonNote = new JSONObject();
        jsonNote.put("id", note.getId());
        jsonNote.put("titre", note.getTitre());
        jsonNote.put("texte", note.getTexte());
        jsonNote.put("url", note.getUrl());

        return jsonNote;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
