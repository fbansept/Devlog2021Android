package edu.fbansept.demo.android.blocnote.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Note implements Serializable {

    private Integer id;
    private String titre;

    public Note() {
    }

    public Note(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public static Note fromJson(JSONObject jsonNote) throws JSONException {
        return new Note(
                jsonNote.getInt("id"),
                jsonNote.getString("titre")
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}