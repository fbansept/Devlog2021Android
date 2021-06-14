package edu.fbansept.demo.android.blocnote.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.controller.UtilisateurController;
import edu.fbansept.demo.android.blocnote.model.Utilisateur;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button boutonSauvegarde =  findViewById(R.id.button_sauvegarde);

        Button boutonDeconnexion =  findViewById(R.id.button_deconnexion);

        EditText editTextPreNom = findViewById(R.id.editText_prenom);
        EditText editTextNom = findViewById(R.id.editText_nom);

        //Utilisateur utilisateurConnecte = UtilisateurController.getInstance().getUtilisateurToken(this);
        //editTextPreNom.setText(utilisateurConnecte.getPrenom());
        //editTextNom.setText(utilisateurConnecte.getNom());

        boutonSauvegarde.setOnClickListener((View v) -> {

        });

        //boutonDeconnexion.setOnClickListener((View v) -> {
        //    ConnexionController.getInstance().deconnexion(
        //            this,
        //            ()->startActivity(new Intent(this,LoginActivity.class)
        //            ));
        //});
    }
}
