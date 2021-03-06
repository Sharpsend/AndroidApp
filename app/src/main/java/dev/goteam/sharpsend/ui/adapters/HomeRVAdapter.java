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
import dev.goteam.sharpsend.db.entities.HomeItem;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.HomeRVAdapterViewHolder> {
    private HomeRVAdapter.OnHomeItemSelectedListener onHomeItemSelectedListener;
    private List<HomeItem> homeItems;

    public HomeRVAdapter(HomeRVAdapter.OnHomeItemSelectedListener onHomeItemSelectedListener, List<HomeItem> homeItems) {
        this.homeItems = homeItems;
        this.onHomeItemSelectedListener = onHomeItemSelectedListener;
    }

    @NonNull
    @Override
    public HomeRVAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rv, parent, false);
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

    public class HomeRVAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        ImageView icon;

        public HomeRVAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onHomeItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }

    public interface OnHomeItemSelectedListener {
        void onItemSelected(int position);
    }
}
