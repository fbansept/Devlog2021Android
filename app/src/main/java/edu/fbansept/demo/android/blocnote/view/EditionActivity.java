package edu.fbansept.demo.android.blocnote.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.controller.NoteController;
import edu.fbansept.demo.android.blocnote.model.Note;
import edu.fbansept.demo.android.blocnote.model.NoteListe;
import edu.fbansept.demo.android.blocnote.model.NoteTexte;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.ImageUpload;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.MultipartRequest;
import edu.fbansept.demo.android.blocnote.utils.requestmanager.RequestManager;
import edu.fbansept.demo.android.blocnote.view.adapter.ListeTacheEditableAdapter;

public class EditionActivity extends AppCompatActivity {

    EditText editTextTitre;
    EditText editTextContenu;
    Note note;
    BottomAppBar bottomAppBar;


    RecyclerView recyclerViewListeTache;


    FloatingActionButton fabEditionNote;

    ActivityResultLauncher<Intent> activitySelectionImage;
    ActivityResultLauncher<Intent> activityAppareilPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {


        //Si nous sommes en train d'editer la note
        if (getIntent().hasExtra("note")) {

            Serializable serializable = getIntent().getSerializableExtra("note");

            if (serializable instanceof NoteTexte) {
                NoteTexte note = (NoteTexte) serializable;
                this.note = note;
                setContentView(R.layout.activity_edition_note_texte);
                editTextContenu = findViewById(R.id.editText_contenu);
                editTextContenu.setText(note.getTexte());
            } else {
                NoteListe note = (NoteListe) serializable;
                this.note = note;
                setContentView(R.layout.activity_edition_note_liste);

                recyclerViewListeTache = findViewById(R.id.recyclerView_editionListeTache);
                recyclerViewListeTache.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewListeTache.setAdapter(
                        new ListeTacheEditableAdapter(note.getListeTache()));
            }

            editTextTitre = findViewById(R.id.editText_titre);
            editTextTitre.setText(this.note.getTitre());

            //Si c'est une nouvelle note
        } else {
            this.note = new NoteTexte();
            setContentView(R.layout.activity_edition_note_texte);
            editTextTitre = findViewById(R.id.editText_titre);
            editTextContenu = findViewById(R.id.editText_contenu);
        }

        fabEditionNote = findViewById(R.id.fab_editionNote);

        fabEditionNote.setOnClickListener(v -> {

            note.setTitre(editTextTitre.getText().toString());

            if (note instanceof NoteTexte) {
                ((NoteTexte) note).setTexte(editTextContenu.getText().toString());
            }

            NoteController.getInstance().sauvegarder(
                    this,
                    note,
                    () -> startActivity(new Intent(this, ListeNoteActivity.class)),
                    messageErreur -> Toast.makeText(this, messageErreur, Toast.LENGTH_LONG).show());
        });

        bottomAppBar = findViewById(R.id.bottomAppBar_EditionActivity);

        bottomAppBar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.menuItem_ajoutPhoto) {
                ouvertureAppareilPhoto();
            } else if (item.getItemId() == R.id.menuItem_ajoutFichier) {
                ouvertureSelecteurImage();
            } else if (item.getItemId() == R.id.menuItem_supprimerNote) {
                System.out.println("click suppression");
            }

            return true;
        });

        activitySelectionImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result != null && result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uriImageSelectionne = data.getData();

                        try {
                            ImageUpload request = new ImageUpload(
                                    this,
                                    uriImageSelectionne,
                                    this.getResources().getString(R.string.server_ip) + "test/image-upload",
                                    s-> {
                                        System.out.println(s);
                                    },
                                    e-> {
                                        e.printStackTrace();
                                    }
                            );

                            request.setRetryPolicy(new DefaultRetryPolicy(
                                    50000,
                                    0,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            RequestManager.getInstance(this).addToRequestQueue(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });



        /*
        Si il est essentiel pour votre application de prendre des photo, alors vous pouvez
        effectuer un filtre sur le play store pour que les appareil ne disposant pas de camera ne
        puisse pas voir votre application en completant le manifest :

        <manifest ... >
            <uses-feature android:name="android.hardware.camera"
                android:required="true" />
            ...
        </manifest>*/
        activityAppareilPhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result != null && result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                        stream.toByteArray();

                        MultipartRequest volleyMultipartRequest = new MultipartRequest(
                                Request.Method.POST,
                                this.getResources().getString(R.string.server_ip) + "test/upload-fichier",
                                response -> {
                                    System.out.println("ok");
                                },
                                error -> {
                                    System.out.println("nok");
                                }
                        ) {
                            @Override
                            protected Map<String, DataPart> getByteData() {
                                Map<String, DataPart> params = new HashMap<>();
                                long imagename = System.currentTimeMillis();
                                params.put("file", new MultipartRequest.DataPart(
                                        "upload.jpg", stream.toByteArray()));
                                return params;
                            }
                        };

                        //adding the request to volley
                        RequestManager.getInstance(this).addToRequestQueue(volleyMultipartRequest);

                        /*ImageUpload request = new ImageUpload(
                                this,
                                imageBitmap,
                                this.getResources().getString(R.string.server_ip) + "test/image-upload",
                                s-> {
                                    System.out.println(s);
                                },
                                e-> {
                                    e.printStackTrace();
                                }
                        );

                        request.setRetryPolicy(new DefaultRetryPolicy(
                                50000,
                                0,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestManager.getInstance(this).addToRequestQueue(request);*/
                    }
                });

    }

    private void ouvertureSelecteurImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        activitySelectionImage.launch(gallery);
    }

    private void ouvertureAppareilPhoto() {
        Intent gallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityAppareilPhoto.launch(gallery);
    }
}