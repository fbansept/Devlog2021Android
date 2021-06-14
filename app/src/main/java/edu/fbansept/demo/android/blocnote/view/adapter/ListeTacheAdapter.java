package edu.fbansept.demo.android.blocnote.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.model.Tache;

public class ListeTacheAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private List<Tache> listeTache;

    public ListeTacheAdapter(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }

    static class TacheViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        CheckBox checkboxTermine;

        TacheViewHolder(View itemView){
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_note);
            checkboxTermine = itemView.findViewById(R.id.checkbox_termine);
        }
    }

    static class BoutonAjouterTacheViewHolder extends RecyclerView.ViewHolder {

        Button buttonAjouterTache;

        BoutonAjouterTacheViewHolder(View itemView){
            super(itemView);
            buttonAjouterTache = itemView.findViewById(R.id.button_ajouterTache);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_tache,parent,false);

        return new TacheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CheckBox checkbox = ((TacheViewHolder)holder).checkboxTermine;
        Tache tache = listeTache.get(position);
        checkbox.setText(tache.getTexte());
        checkbox.setChecked(tache.isTermine());
        checkbox.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return listeTache.size();
    }
}
