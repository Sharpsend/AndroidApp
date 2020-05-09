package dev.goteam.paydrift.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

import dev.goteam.paydrift.PaydriftApp;
import dev.goteam.paydrift.R;
import dev.goteam.paydrift.db.entities.Transaction;

public class TransactionsRVAdapter extends RecyclerView.Adapter<TransactionsRVAdapter.TransactionsRVAdapterViewHolder> {
    private View.OnClickListener onClickListener;
    private List<Transaction> transactions;
    private NumberFormat numberFormat;
    private Context context;


    public TransactionsRVAdapter(Context context, View.OnClickListener onClickListener, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.onClickListener = onClickListener;
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
    }

    @NonNull
    @Override
    public TransactionsRVAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new TransactionsRVAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsRVAdapterViewHolder holder, int position) {

        if (transactions != null) {
            final Transaction current = transactions.get(position);
            holder.txTypeLabel.setText(current.getTxType());
            holder.txAmountLabel.setText("₦" + numberFormat.format(current.getAmount()));
            holder.txTimeLabel.setText(current.getTime());
            holder.txStateLabel.setText(current.getState());
            switch (current.getState()) {
                case PaydriftApp.SUCCESSFUL_TX:
                    holder.txStateLabel.setTextColor(context.getResources().getColor(R.color.tx_SuccessfulColor));
                case PaydriftApp.FAILED_TX:
                    holder.txStateLabel.setTextColor(context.getResources().getColor(R.color.tx_FailedColor));
                case PaydriftApp.PENDING_TX:
                    holder.txStateLabel.setTextColor(context.getResources().getColor(R.color.tx_PendingColor));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (transactions != null)
            return transactions.size();
        else
            return 0;
    }

    public class TransactionsRVAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txTypeLabel, txAmountLabel,
                txTimeLabel, txStateLabel, txDateLabel;

        public TransactionsRVAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txTypeLabel = itemView.findViewById(R.id.txTypeLabel);
            txAmountLabel = itemView.findViewById(R.id.txAmountLabel);
            txTimeLabel = itemView.findViewById(R.id.txTimeLabel);
            txDateLabel = itemView.findViewById(R.id.txDateLabel);
            txStateLabel = itemView.findViewById(R.id.txStateLabel);
        }
    }
}
