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
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.ui.listeners.OnNetworkItemSelection;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NetworkRVAdapter extends RecyclerView.Adapter<NetworkRVAdapter.NetworkViewHolder> {

    private Context context;
    private ArrayList<NetworkItem.NetworkImpl> networks;
    private OnNetworkItemSelection onNetworkItemSelection;

    public NetworkRVAdapter(Context context,
                            ArrayList<NetworkItem.NetworkImpl> networks,
                            OnNetworkItemSelection selectedOnAdapterListener) {
        this.context = context;
        this.networks = networks;
        this.onNetworkItemSelection = selectedOnAdapterListener;
    }

    @NonNull
    @Override
    public NetworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_network, parent, false );

        return new NetworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkViewHolder holder, int position) {
        NetworkItem.NetworkImpl network = networks.get(position);
        holder.bind(network);

        holder.itemView.setOnClickListener(view -> {
            networkSelected(position);
            Log.i(TAG, "onClick: " + position);
            onNetworkItemSelection.onNetworkSelected(networks.get(position));
        });
    }

    public void networkSelected(int position) {
        for (NetworkItem.NetworkImpl network: networks) {
            network.setSelected(false);
        }
        networks.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return networks == null ? 0 : networks.size();
    }

    class NetworkViewHolder extends RecyclerView.ViewHolder {
        TextView mobileName;
        ImageView selectorImage, image;

        public NetworkViewHolder(View itemView) {
            super(itemView);

                mobileName = itemView.findViewById(R.id.bank_name);
                selectorImage = itemView.findViewById(R.id.selectorImage);
                image = itemView.findViewById(R.id.image);
        }

        public void bind(NetworkItem.NetworkImpl network) {
            if (network.getNetworkOperatorCode() != null) {
                // Normal display
                mobileName.setText(
                        String.format("%s (SIM %d)",
                                network.getDisplayName(),
                                network.getSlotIdx() + 1));
            } else {
                mobileName.setText(network.getDisplayName());
            }

            selectorImage.setVisibility(network.isSelected() ? View.VISIBLE : View.GONE);
            image.setImageResource(network.getImage());

            itemView.setBackgroundResource(network.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
        }
    }
}
