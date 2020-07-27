package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentHomeBinding;
import dev.goteam.sharpsend.db.entities.HomeItem;
import dev.goteam.sharpsend.ui.adapters.HomeRVAdapter;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.HomeViewModel;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;

public class HomeFragment extends Fragment implements HomeRVAdapter.OnHomeItemSelectedListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private List<HomeItem> homeItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.salutationLabel.setText(String.format("Hi, %s", user.getUserName()));
            }
        });

        homeItems = new ArrayList<>();
        homeItems.add(new HomeItem("Transfer Funds", "Send money to any bank account in Nigeria", R.drawable.ic_transfer, Constants.TRANSFER_FUNDS));
        homeItems.add(new HomeItem("Buy Airtime", "Get instant call credit on any phone with zero stress", R.drawable.ic_buy_airtime, Constants.BUY_AIRTIME));
        homeItems.add(new HomeItem("Check Airtime", "Easily check your airtime account balance with one-click ", R.drawable.ic_check_airtime, Constants.CHECK_AIRTIME));
        homeItems.add(new HomeItem("Buy Data", "Get data on your mobile phone with zero stress", R.drawable.ic_buy_airtime, Constants.BUY_DATA));

        HomeRVAdapter homeRVAdapter = new HomeRVAdapter(this, homeItems);
        binding.homeRV.setAdapter(homeRVAdapter);
        return binding.getRoot();
    }

    @Override
    public void onItemSelected(int position) {
        HomeItem homeItem = homeItems.get(position);

        Intent operationsIntent = new Intent(requireActivity(), OperationsActivity.class);
        operationsIntent.putExtra("operation_id", homeItem.getType());
        startActivity(operationsIntent);
    }
}
