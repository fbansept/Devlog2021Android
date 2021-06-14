package edu.fbansept.demo.android.blocnote.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private Integer id;
    private String denomination;

    public Role(Integer id, String nom) {
        this.id = id;
        this.denomination = nom;
    }

    public static Role fromJson(JSONObject jsonRole) throws JSONException {
        return new Role(
                jsonRole.getInt("id"),
                jsonRole.getString("denomination")
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }
}
