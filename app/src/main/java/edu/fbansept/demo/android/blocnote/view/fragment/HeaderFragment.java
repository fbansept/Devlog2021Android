package edu.fbansept.demo.android.blocnote.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.controller.UtilisateurController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_header, container, false);


        //TextView pseudo =  view.findViewById(R.id.textView_pseudoProfil);

        UtilisateurController.getInstance().getImageProfilUtilisateurConnecte(view.getContext(),
                bitmap -> {
                    ImageView image = view.findViewById(R.id.imageView_photoProfil);
                    image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, image.getWidth(), image.getHeight(), false));
                });

        //nom.setText(utilisateurConnecte.getNom());
        //prenom.setText(utilisateurConnecte.getPrenom());

        //boutonProfil.setOnClickListener((View v) -> {
        //    startActivity(new Intent(getActivity(), ProfilActivity.class));
        //});

        // Inflate the layout for this fragment
        return view;
    }
}
