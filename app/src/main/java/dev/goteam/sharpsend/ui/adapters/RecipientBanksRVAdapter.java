package dev.goteam.sharpsend.ui.adapters;

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
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelectedOnAdapterListener;

public class RecipientBanksRVAdapter extends RecyclerView.Adapter<RecipientBanksRVAdapter.BankViewHolder>
        implements Filterable {

    private List<BankItem.TransferBank> transferBanks;
    private List<BankItem.TransferBank> finalTransferBanks;
    private OnBankItemSelectedOnAdapterListener mOnBankItemSelectedOnAdapterListener;

    private Filter banksFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BankItem.TransferBank> filteredBanks = new ArrayList<>();

            if (charSequence == null || charSequence == "") {
                filteredBanks.addAll(finalTransferBanks);
            } else {
                String text = charSequence.toString().trim();
                for (BankItem.TransferBank transferBank : transferBanks) {
                    if (transferBank.getName().trim().contains(text)) {
                        filteredBanks.add(transferBank);
                    }
                }
            }

            FilterResults res = new FilterResults();
            res.values = filteredBanks;
            return res;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<BankItem.TransferBank> result = (List<BankItem.TransferBank>) filterResults.values;
            transferBanks = result;
            notifyDataSetChanged();
        }
    };

    public RecipientBanksRVAdapter(List<BankItem.TransferBank> transferBanks,
                                   OnBankItemSelectedOnAdapterListener selectedOnAdapterListener) {
        this.transferBanks = transferBanks;
        this.finalTransferBanks = transferBanks;
        this.mOnBankItemSelectedOnAdapterListener = selectedOnAdapterListener;
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
        BankItem.TransferBank bank = transferBanks.get(position);
        holder.bind(bank);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BankItem.TransferBank bank = transferBanks.get(position);
                bankSelected(position);

                mOnBankItemSelectedOnAdapterListener.onBankItemSelected(position);
                return;
            }
        });
    }

    public void bankSelected(int position) {
        for (BankItem.TransferBank bank : transferBanks) {
            bank.setSelected(false);
        }
        transferBanks.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transferBanks.size();
    }

    class BankViewHolder extends RecyclerView.ViewHolder {

        TextView bankName;
        RadioButton radioButton;

        public BankViewHolder(View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.recipient_bank_name);
            radioButton = itemView.findViewById(R.id.recipient_radio_button);
        }

        public void bind(BankItem.TransferBank bank) {
            bankName.setText(bank.getName());
            radioButton.setChecked(bank.isSelected());
        }

    }
}
