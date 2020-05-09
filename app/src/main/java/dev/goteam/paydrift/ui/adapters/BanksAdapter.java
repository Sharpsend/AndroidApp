package dev.goteam.paydrift.ui.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.ItemBankBinding;
import dev.goteam.paydrift.db.entities.Bank;
import dev.goteam.paydrift.ui.listeners.OnBankItemSelectedOnAdapterListener;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BankViewHolder> {

    private int selectedIndex = -1;
    private Context context;
    private ArrayList<Bank> banks;
    private OnBankItemSelectedOnAdapterListener mOnBankItemSelectedOnAdapterListener;

    public BanksAdapter(Context context, ArrayList<Bank> banks, OnBankItemSelectedOnAdapterListener selectedOnAdapterListener) {
        this.context = context;
        this.banks = banks;
        this.mOnBankItemSelectedOnAdapterListener = selectedOnAdapterListener;
    }

    @NonNull
    @Override
    public BanksAdapter.BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_bank, parent, false
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

        public BankViewHolder(View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.bank_name);
            bankIsSelected = itemView.findViewById(R.id.bank_is_selected);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnBankItemSelectedOnAdapterListener.onBankItemSelected(getAdapterPosition());
                }
            });
        }

        public void bind(Bank bank) {
            bankName.setText(bank.getBankName());
            bankIsSelected.setVisibility(bank.isSelected() ? View.VISIBLE : View.GONE);
            itemView.setBackgroundResource(bank.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
        }

    }
}
