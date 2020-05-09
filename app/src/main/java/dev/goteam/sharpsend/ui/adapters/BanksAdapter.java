package dev.goteam.sharpsend.ui.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.ItemBankBinding;
import dev.goteam.sharpsend.db.entities.Bank;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelectedOnAdapterListener;
import dev.goteam.sharpsend.utils.DataSource;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BankViewHolder>
    implements Filterable {

    private final boolean isRecipient;
    private int selectedIndex = -1;
    private Context context;
    private ArrayList<Bank> banks;
    private OnBankItemSelectedOnAdapterListener mOnBankItemSelectedOnAdapterListener;

    private Filter banksFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Bank> filteredBanks = new ArrayList<>();

            if (charSequence == null || charSequence == "") {
                filteredBanks.addAll(DataSource.getRecipientBanks(0));
            } else {
                String text = charSequence.toString().trim();
                for (Bank bank: banks) {
                    if (bank.getBankName().trim().contains(text)) {
                        filteredBanks.add(bank);
                    }
                }
            }

            FilterResults res = new FilterResults();
            res.values = filteredBanks;
            return res;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Bank> result = (ArrayList<Bank>) filterResults.values;
            banks = result;
            notifyDataSetChanged();
        }
    };

    public BanksAdapter(Context context,
                        ArrayList<Bank> banks,
                        OnBankItemSelectedOnAdapterListener selectedOnAdapterListener,
                        boolean isRecipient
    ) {
        this.context = context;
        this.banks = banks;
        this.mOnBankItemSelectedOnAdapterListener = selectedOnAdapterListener;
        this.isRecipient = isRecipient;
    }

    @Override
    public Filter getFilter() {
        return banksFilter;
    }

    @NonNull
    @Override
    public BanksAdapter.BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                isRecipient ? R.layout.item_recipient_bank : R.layout.item_bank, parent, false
        );

        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksAdapter.BankViewHolder holder, int position) {
        Bank bank = banks.get(position);
        if (selectedIndex == -1) {
            selectedIndex = bank.isSelected() ? position : -1;
        }
        holder.bind(bank);
    }



    public void bankSelected(int position) {
        for (Bank bank: banks) {
            bank.setSelected(false);
        }
        banks.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    class BankViewHolder extends RecyclerView.ViewHolder {

        TextView bankName;
        ImageView bankIsSelected;
        RadioButton recipientBankIsSelected;

        public BankViewHolder(View itemView) {
            super(itemView);

            if (isRecipient) {
                bankName = itemView.findViewById(R.id.recipient_bank_name);
                recipientBankIsSelected = itemView.findViewById(R.id.recipient_radio_button);
            } else {
                bankName = itemView.findViewById(R.id.bank_name);
                bankIsSelected = itemView.findViewById(R.id.bank_is_selected);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bank bank = banks.get(getAdapterPosition());

                    if (isRecipient) {
                        int realPosition = 0;
                        ArrayList<Bank> source = DataSource.getRecipientBanks(0);
                        for (int i = 0; i < source.size(); i++) {
                            Bank b = source.get(i);
                            if (b.getBankName() == bank.getBankName()) {
                                realPosition = i;
                            }
                        }
                        mOnBankItemSelectedOnAdapterListener.onBankItemSelected(realPosition);
                        return;
                    }

                    mOnBankItemSelectedOnAdapterListener.onBankItemSelected(getAdapterPosition());
                }
            });
        }

        public void bind(Bank bank) {
            bankName.setText(bank.getBankName());

            if (!isRecipient) {
                bankIsSelected.setVisibility(bank.isSelected() ? View.VISIBLE : View.GONE);
                itemView.setBackgroundResource(bank.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
            } else {
                recipientBankIsSelected.setChecked(bank.isSelected());
            }

        }

    }
}
