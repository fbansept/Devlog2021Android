package edu.fbansept.devlog2021.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.NoteController;

public class MainActivity extends AppCompatActivity {

    private Button btnAction;
    private TextView tvValeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        this.btnAction = findViewById(R.id.btnAction);
        this.tvValeur = findViewById(R.id.tvValeur);

        this.btnAction.setOnClickListener((View v) -> {
            NoteController.getInstance().changeValeur(this);
        });

    }

    public TextView getTvValeur() {
        return tvValeur;
    }

    public void setTvValeur(TextView tvValeur) {
        this.tvValeur = tvValeur;
    }
}