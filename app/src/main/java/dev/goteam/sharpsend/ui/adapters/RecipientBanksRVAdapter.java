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
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.listeners.OnRecipientBankItemSelected;

public class RecipientBanksRVAdapter extends RecyclerView.Adapter<RecipientBanksRVAdapter.BankViewHolder>
        implements Filterable {

    private final OnRecipientBankItemSelected onRecipientBankItemSelected;
    private List<BankItem.TransferBankAction> transferBankActions;
    private List<BankItem.TransferBankAction> finalTransferBankActions;

    private Filter banksFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BankItem.TransferBankAction> filteredBanks = new ArrayList<>();
            Log.d("RecipientBanksAdapter", "CharSequence: " + charSequence + " Size: " + finalTransferBankActions.size());

            if (charSequence == null || charSequence.equals("") || charSequence.length() == 0) {
                filteredBanks.addAll(finalTransferBankActions);
            } else {
                String text = charSequence.toString().trim();
                for (BankItem.TransferBankAction transferBankAction : transferBankActions) {
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
            List<BankItem.TransferBankAction> result = (List<BankItem.TransferBankAction>) filterResults.values;
            transferBankActions = result;
            notifyDataSetChanged();
        }
    };

    public RecipientBanksRVAdapter(List<BankItem.TransferBankAction> transferBankActions,
                                   OnRecipientBankItemSelected onRecipientBankItemSelected) {

        Collections.sort(transferBankActions, new Comparator<BankItem.TransferBankAction>() {
            @Override
            public int compare(BankItem.TransferBankAction transferBankAction, BankItem.TransferBankAction t1) {
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
        BankItem.TransferBankAction bank = transferBankActions.get(position);
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
        for (BankItem.TransferBankAction bank : transferBankActions) {
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

        public void bind(BankItem.TransferBankAction bank) {
            bankName.setText(bank.getName());
            radioButton.setChecked(bank.isSelected());
        }

    }
}
