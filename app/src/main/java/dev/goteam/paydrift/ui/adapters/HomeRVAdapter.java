package dev.goteam.paydrift.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.db.entities.HomeItem;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.HomeRVAdapterViewHolder> {
    private View.OnClickListener onClickListener;
    private List<HomeItem> homeItems;

    public HomeRVAdapter(View.OnClickListener onClickListener, List<HomeItem> homeItems) {
        this.homeItems = homeItems;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HomeRVAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rv, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new HomeRVAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRVAdapterViewHolder holder, int position) {

        if (homeItems != null) {
            final HomeItem current = homeItems.get(position);
            holder.title.setText(current.getTitle());
            holder.description.setText(current.getDescription());
            holder.icon.setImageResource(current.getIconResID());
        }
    }

    @Override
    public int getItemCount() {
        if (homeItems != null)
            return homeItems.size();
        else
            return 0;
    }

    public class HomeRVAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView icon;

        public HomeRVAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
