package edu.fbansept.demo.android.blocnote.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utilisateur implements Serializable {

    private int id;

    private String pseudo;

    private String motDePasse;

    private List<Note> listeNote = new ArrayList<>();

    private List<Role> listeRole = new ArrayList<>();

    public Utilisateur(int id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public static Utilisateur fromJson(JSONObject jsonUtilisateur) throws JSONException {
        System.out.println(jsonUtilisateur);

        Utilisateur utilisateur = new Utilisateur(
                jsonUtilisateur.getInt("id"),
                jsonUtilisateur.getString("pseudo"));


        JSONArray jsonListeRole = jsonUtilisateur.getJSONArray("listeRole");
        for(int j = 0; j < jsonListeRole.length(); j++) {
            JSONObject jsonRole = jsonListeRole.getJSONObject(j);
            Role role = Role.fromJson(jsonRole);
            utilisateur.getListeRole().add(role);
        }

        JSONArray jsonListeNote = jsonUtilisateur.getJSONArray("listeNote");
        for(int j = 0; j < jsonListeNote.length(); j++) {
            JSONObject jsonNote = jsonListeNote.getJSONObject(j);
            Note note;
            if(jsonNote.has("texte")) {
                note = NoteTexte.fromJson(jsonNote);
            } else {
                note = NoteListe.fromJson(jsonNote);
            }
            utilisateur.getListeNote().add(note);
        }

        return utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Note> getListeNote() {
        return listeNote;
    }

    public void setListeNote(List<Note> listeNote) {
        this.listeNote = listeNote;
    }

    public List<Role> getListeRole() {
        return listeRole;
    }

    public void setListeRole(List<Role> listeRole) {
        this.listeRole = listeRole;
    }
}
