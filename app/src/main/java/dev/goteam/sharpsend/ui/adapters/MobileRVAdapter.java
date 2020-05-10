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
import dev.goteam.sharpsend.db.entities.MobileItem;
import dev.goteam.sharpsend.ui.listeners.OnMobileItemSelection;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MobileRVAdapter extends RecyclerView.Adapter<MobileRVAdapter.MobileViewHolder> {

    private Context context;
    private ArrayList<MobileItem.Mobile> mobiles;
    private OnMobileItemSelection onMobileItemSelection;

    public MobileRVAdapter(Context context,
                           ArrayList<MobileItem.Mobile> mobiles,
                           OnMobileItemSelection selectedOnAdapterListener) {
        this.context = context;
        this.mobiles = mobiles;
        this.onMobileItemSelection = selectedOnAdapterListener;
    }

    @NonNull
    @Override
    public MobileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_bank, parent, false );

        return new MobileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MobileViewHolder holder, int position) {
        MobileItem.Mobile mobile = mobiles.get(position);
        holder.bind(mobile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankSelected(position);
                Log.i(TAG, "onClick: " + position);
                onMobileItemSelection.onMobileSelected(mobiles.get(position));
            }
        });
    }

    public void bankSelected(int position) {
        for (MobileItem.Mobile mobile: mobiles) {
            mobile.setSelected(false);
        }
        mobiles.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mobiles.size();
    }

    class MobileViewHolder extends RecyclerView.ViewHolder {
        TextView mobileName;
        ImageView selectorImage;

        public MobileViewHolder(View itemView) {
            super(itemView);

                mobileName = itemView.findViewById(R.id.bank_name);
                selectorImage = itemView.findViewById(R.id.selectorImage);
        }

        public void bind(MobileItem.Mobile mobile) {
            mobileName.setText(mobile.getName());
            selectorImage.setVisibility(mobile.isSelected() ? View.VISIBLE : View.GONE);

            itemView.setBackgroundResource(mobile.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
        }
    }
}
