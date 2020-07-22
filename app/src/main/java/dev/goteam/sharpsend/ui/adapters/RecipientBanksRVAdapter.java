package dev.goteam.sharpsend.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.db.entities.Action;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.listeners.OnRecipientBankItemSelected;

public class RecipientBanksRVAdapter extends RecyclerView.Adapter<RecipientBanksRVAdapter.BankViewHolder>
        implements Filterable {

    private final OnRecipientBankItemSelected onRecipientBankItemSelected;
    private List<Action> transferBankActions;
    private List<Action> finalTransferBankActions;

    private Filter banksFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Action> filteredBanks = new ArrayList<>();
            Log.d("RecipientBanksAdapter", "CharSequence: " + charSequence + " Size: " + finalTransferBankActions.size());

            if (charSequence == null || charSequence.equals("") || charSequence.length() == 0) {
                filteredBanks.addAll(finalTransferBankActions);
            } else {
                String text = charSequence.toString().trim();
                for (Action transferBankAction : transferBankActions) {
                    if (transferBankAction.getName().trim().contains(text)) {
                        filteredBanks.add(transferBankAction);
                    }
                }
            }

            FilterResults res = new FilterResults();
            res.values = filteredBanks;
            return res;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<Action> result = (List<Action>) filterResults.values;
            transferBankActions = result;
            notifyDataSetChanged();
        }
    };

    public RecipientBanksRVAdapter(List<Action> transferBankActions,
                                   OnRecipientBankItemSelected onRecipientBankItemSelected) {

        Collections.sort(transferBankActions, new Comparator<Action>() {
            @Override
            public int compare(Action transferBankAction, Action t1) {
                return transferBankAction.getName().compareTo(t1.getName());
            }
        });

        this.transferBankActions = transferBankActions;
        this.finalTransferBankActions = transferBankActions;
        this.onRecipientBankItemSelected = onRecipientBankItemSelected;
    }

    @Override
    public Filter getFilter() {
        return banksFilter;
    }

    @NonNull
    @Override
    public RecipientBanksRVAdapter.BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipient_bank, parent, false);

        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipientBanksRVAdapter.BankViewHolder holder, int position) {
        Action bank = transferBankActions.get(position);
        holder.bind(bank);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankSelected(position);
                onRecipientBankItemSelected.onBankItemSelected(transferBankActions.get(position));
                return;
            }
        });
    }

    public void bankSelected(int position) {
        for (Action bank : transferBankActions) {
            bank.setSelected(false);
        }
        transferBankActions.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transferBankActions.size();
    }

    class BankViewHolder extends RecyclerView.ViewHolder {

        TextView bankName;
        RadioButton radioButton;

        public BankViewHolder(View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.recipient_bank_name);
            radioButton = itemView.findViewById(R.id.recipient_radio_button);
        }

        public void bind(Action bank) {
            bankName.setText(bank.getName());
            radioButton.setChecked(bank.isSelected());
        }

    }
}
