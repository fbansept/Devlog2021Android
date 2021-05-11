package edu.fbansept.devlog2021.model;

public class Note {
    private String titre;
    private String texte;

    public Note() {
    }

    public Note(String titre, String texte) {
        this.titre = titre;
        this.texte = texte;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}