package dev.goteam.sharpsend.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelected;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BanksRVAdapter extends RecyclerView.Adapter<BanksRVAdapter.BankRVViewHolder> {

    private Context context;
    private ArrayList<BankItem.Bank> banks;
    private OnBankItemSelected mOnBankItemSelected;

    public BanksRVAdapter(Context context,
                          ArrayList<BankItem.Bank> banks,
                          OnBankItemSelected selectedOnAdapterListener) {
        this.context = context;
        this.banks = banks;
        this.mOnBankItemSelected = selectedOnAdapterListener;
    }

    @NonNull
    @Override
    public BanksRVAdapter.BankRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_bank, parent, false );

        return new BankRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksRVAdapter.BankRVViewHolder holder, int position) {
        BankItem.Bank bank = banks.get(position);
        holder.bind(bank);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankSelected(position);
                Log.i(TAG, "onClick: " + position);
                mOnBankItemSelected.onBankItemSelected(banks.get(position));
            }
        });
    }

    public void bankSelected(int position) {
        for (BankItem.Bank bank: banks) {
            bank.setSelected(false);
        }
        banks.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    class BankRVViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;
        ImageView selectorImage;

        public BankRVViewHolder(View itemView) {
            super(itemView);

                bankName = itemView.findViewById(R.id.bank_name);
                selectorImage = itemView.findViewById(R.id.selectorImage);
        }

        public void bind(BankItem.Bank bank) {
            bankName.setText(bank.getName());
            selectorImage.setVisibility(bank.isSelected() ? View.VISIBLE : View.GONE);

            itemView.setBackgroundResource(bank.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
        }
    }
}
