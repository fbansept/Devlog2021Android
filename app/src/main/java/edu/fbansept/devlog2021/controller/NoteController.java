package edu.fbansept.devlog2021.controller;

import android.util.Log;

import edu.fbansept.devlog2021.view.MainActivity;

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

    public void hello() {
        Log.d("Message","Hello world !");
    }

    public void changeValeur(MainActivity activity) {
        activity.getTvValeur().setText("nouvelle valeur");
    }
}
