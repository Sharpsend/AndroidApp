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
    private ArrayList<NetworkItem.Network> networks;
    private OnNetworkItemSelection onNetworkItemSelection;

    public NetworkRVAdapter(Context context,
                            ArrayList<NetworkItem.Network> networks,
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
        NetworkItem.Network network = networks.get(position);
        holder.bind(network);

        holder.itemView.setOnClickListener(view -> {
            networkSelected(position);
            Log.i(TAG, "onClick: " + position);
            onNetworkItemSelection.onNetworkSelected(networks.get(position));
        });
    }

    public void networkSelected(int position) {
        for (NetworkItem.Network network: networks) {
            network.setSelected(false);
        }
        networks.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return networks.size();
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

        public void bind(NetworkItem.Network network) {
            mobileName.setText(network.getName());
            selectorImage.setVisibility(network.isSelected() ? View.VISIBLE : View.GONE);
            image.setImageResource(network.getImage());

            itemView.setBackgroundResource(network.isSelected() ? R.drawable.bank_selected_bg : R.drawable.bank_unselected_bg);
        }
    }
}
