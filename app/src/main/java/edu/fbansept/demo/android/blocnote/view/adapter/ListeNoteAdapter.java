package edu.fbansept.demo.android.blocnote.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.model.Note;
import edu.fbansept.demo.android.blocnote.model.NoteListe;
import edu.fbansept.demo.android.blocnote.model.NoteTexte;

public class ListeNoteAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_NOTE_TEXTE = 1;
    private final static int VIEW_TYPE_NOTE_LISTE = 2;
    private final EcouteurClicNote ecouteur;

    public interface EcouteurClicNote {
        void onClicNote(Note item);
    }

    private List<Note> listeNote;

    public ListeNoteAdapter(List<Note> listeNote, EcouteurClicNote ecouteur) {
        this.ecouteur = ecouteur;
        this.listeNote = listeNote;
    }

    static abstract class NoteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView textViewTitre;
        CardView cardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_note);
            textViewTitre = itemView.findViewById(R.id.textView_titre);
            cardView = itemView.findViewById(R.id.cardView_note);
        }
    }

    static class NoteTexteViewHolder extends NoteViewHolder {

        TextView textViewContenu;

        NoteTexteViewHolder(View itemView){
            super(itemView);
            textViewContenu = itemView.findViewById(R.id.textView_contenu);
        }
    }

    static class NoteListeViewHolder extends NoteViewHolder {

        RecyclerView recyclerViewListeTache;

        NoteListeViewHolder(View itemView){
            super(itemView);
            recyclerViewListeTache = itemView.findViewById(R.id.recyclerView_listeTache);
            recyclerViewListeTache.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == VIEW_TYPE_NOTE_TEXTE) {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_note_texte,parent,false);
            return new NoteTexteViewHolder(view);

        } else {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_note_liste,parent,false);

            return new NoteListeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Note note = listeNote.get(position);
        NoteViewHolder noteViewHolder = (NoteViewHolder)holder;

        if(holder.getItemViewType() == VIEW_TYPE_NOTE_TEXTE) {
            NoteTexte noteTexte = (NoteTexte)note;
            NoteTexteViewHolder noteTexteViewHolder = (NoteTexteViewHolder) holder;
            noteTexteViewHolder.textViewContenu.setText(noteTexte.getTexte());
        } else {
            NoteListe noteListe = (NoteListe)note;
            NoteListeViewHolder noteListeViewHolder = (NoteListeViewHolder) noteViewHolder;

            noteListeViewHolder.recyclerViewListeTache.setAdapter(
                    new ListeTacheAdapter(noteListe.getListeTache()));
        }

        noteViewHolder.textViewTitre.setText(note.getTitre());

        noteViewHolder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicNote(note)
        );

        ViewGroup.MarginLayoutParams  layoutParams;
        if(position == 0 ){
            layoutParams = (ViewGroup.MarginLayoutParams)noteViewHolder.layoutItem.getLayoutParams();
            layoutParams.setMargins(0,128,0,0);
            noteViewHolder.layoutItem.setLayoutParams(layoutParams);
        } else if (position == listeNote.size() - 1) {
            layoutParams = (ViewGroup.MarginLayoutParams)noteViewHolder.layoutItem.getLayoutParams();
            layoutParams.setMargins(0,0,0,96);
            noteViewHolder.layoutItem.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listeNote.get(position) instanceof NoteTexte
            ? VIEW_TYPE_NOTE_TEXTE
            : VIEW_TYPE_NOTE_LISTE;
    }

    @Override
    public int getItemCount() {
        return listeNote.size();
    }
}
