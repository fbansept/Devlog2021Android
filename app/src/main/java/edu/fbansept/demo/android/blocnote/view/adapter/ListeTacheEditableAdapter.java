package edu.fbansept.demo.android.blocnote.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.demo.android.blocnote.R;
import edu.fbansept.demo.android.blocnote.databinding.ItemTacheEditableBinding;
import edu.fbansept.demo.android.blocnote.model.Tache;

public class ListeTacheEditableAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private final List<Tache> listeTache;

    private final int VIEW_TYPE_TACHE = 0;
    private final int VIEW_TYPE_BOUTON = 1;

    public ListeTacheEditableAdapter(List<Tache> listeTache) {
        this.listeTache = listeTache;
    }

    static class TacheViewHolder extends RecyclerView.ViewHolder {

        /*LinearLayout layoutItem;
        CheckBox checkboxTermine;
        EditText editTextContenuTache;
        ImageButton buttonSupprimerTache;

        TacheViewHolder(View itemView){
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            checkboxTermine = itemView.findViewById(R.id.checkbox_termine);
            editTextContenuTache = itemView.findViewById(R.id.editText_contenuTache);
            buttonSupprimerTache = itemView.findViewById(R.id.button_supprimerTache);
        }*/

        ItemTacheEditableBinding binding;
        ImageButton buttonSupprimerTache;

        TacheViewHolder(ItemTacheEditableBinding itemView){
            super(itemView.getRoot());
            buttonSupprimerTache = itemView.getRoot().findViewById(R.id.button_supprimerTache);
            this.binding = itemView;
        }

        public void bind(Tache tache) {
            binding.setTache(tache);
            binding.executePendingBindings();
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

        if (viewType == VIEW_TYPE_TACHE) {

            LayoutInflater  layoutInflater = LayoutInflater
                    .from(parent.getContext());

            ItemTacheEditableBinding itemTacheEditableBinding =
                    ItemTacheEditableBinding.inflate(layoutInflater,parent,false);

            return new TacheViewHolder(itemTacheEditableBinding);
        }
        else {

            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_bouton_ajouter_tache,parent,false);

            return new BoutonAjouterTacheViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_TACHE) {

            ((TacheViewHolder)holder).bind(listeTache.get(position));

            /*((TacheViewHolder)holder).checkboxTermine.setChecked(tache.isTermine());
            ((TacheViewHolder)holder).editTextContenuTache.setText(tache.getTexte());*/
            ((TacheViewHolder)holder).buttonSupprimerTache.setOnClickListener(
                    v -> {
                        listeTache.remove(position);
                        notifyDataSetChanged();
                    });
        } else {
            ((BoutonAjouterTacheViewHolder)holder).buttonAjouterTache.setOnClickListener(
                    v -> {
                        listeTache.add(new Tache());
                        notifyItemInserted(listeTache.size() - 1);
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return listeTache.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listeTache.size()) ? VIEW_TYPE_BOUTON : VIEW_TYPE_TACHE;
    }
}
