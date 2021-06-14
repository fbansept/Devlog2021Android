package edu.fbansept.demo.android.blocnote.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.controller.UtilisateurController;
import edu.fbansept.demo.android.blocnote.view.adapter.ListeNoteAdapter;

public class ListeNoteActivity extends AppCompatActivity {

    private FloatingActionButton buttonAjouterNote;
    private RecyclerView recyclerViewListeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        setContentView(R.layout.activity_liste_note);

        recyclerViewListeNote = findViewById(R.id.liste_note);
        recyclerViewListeNote.setLayoutManager(new LinearLayoutManager(this));

        UtilisateurController.getInstance().getUtilisateurConnecte(
                this,
                (utilisateur -> {



                    recyclerViewListeNote.setAdapter(
                            new ListeNoteAdapter(utilisateur.getListeNote(),
                                    noteClic -> {
                                        Intent intent = new Intent(this, EditionActivity.class);
                                        intent.putExtra("note", noteClic);
                                        startActivity(intent);
                                    }));
                }));

        this.buttonAjouterNote = findViewById(R.id.button_ajouterNote);

        this.buttonAjouterNote.setOnClickListener((View v) -> {
            startActivity(new Intent(this, EditionActivity.class));
        });
    }
}