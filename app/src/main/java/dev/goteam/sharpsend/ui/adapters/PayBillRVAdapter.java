package dev.goteam.sharpsend.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.db.entities.PayBillItem;

public class PayBillRVAdapter extends RecyclerView.Adapter<PayBillRVAdapter.PayBillRVAdapterViewHolder> {
    private PayBillRVAdapter.OnPayBillItemSelectedListener onPayBillItemSelectedListener;
    private List<PayBillItem> payBillItems;

    public PayBillRVAdapter(PayBillRVAdapter.OnPayBillItemSelectedListener onPayBillItemSelectedListener, List<PayBillItem> payBillItems) {
        this.payBillItems = payBillItems;
        this.onPayBillItemSelectedListener = onPayBillItemSelectedListener;
    }

    @NonNull
    @Override
    public PayBillRVAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_bill, parent, false);
        return new PayBillRVAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PayBillRVAdapterViewHolder holder, int position) {

        if (payBillItems != null) {
            final PayBillItem current = payBillItems.get(position);
            holder.title.setText(current.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (payBillItems != null)
            return payBillItems.size();
        else
            return 0;
    }

    public class PayBillRVAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        public PayBillRVAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPayBillItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }

    public interface OnPayBillItemSelectedListener {
        void onItemSelected(int position);
    }
}
