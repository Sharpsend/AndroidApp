package dev.goteam.paydrift.ui.fragments;

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

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentHomeBinding;
import dev.goteam.paydrift.db.entities.HomeItem;
import dev.goteam.paydrift.ui.activities.OperationsActivity;
import dev.goteam.paydrift.ui.adapters.HomeRVAdapter;
import dev.goteam.paydrift.utils.Constants;
import dev.goteam.paydrift.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment implements HomeRVAdapter.OnHomeItemSelectedListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.salutationLabel.setText(String.format("Hi, %s", user.getUserName()));
            }
        });

        List<HomeItem> homeItems = new ArrayList<>();
        homeItems.add(new HomeItem("Transfer Funds", "Send money to any bank account in Nigeria", R.drawable.ic_transfer, "stegege"));
        homeItems.add(new HomeItem("Buy Airtime", "Get instant call credit on any phone with zero stress", R.drawable.ic_buy_airtime, "stegege"));
        homeItems.add(new HomeItem("Check Airtime", "Easily check your airtime account balance with one-click ", R.drawable.ic_check_airtime, "stegege"));

        HomeRVAdapter homeRVAdapter = new HomeRVAdapter(this, homeItems);
        binding.homeRV.setAdapter(homeRVAdapter);
        return binding.getRoot();
    }

    @Override
    public void onItemSelected(int position) {
        Intent operationsIntent = new Intent(requireActivity(), OperationsActivity.class);
        operationsIntent.putExtra("operation_id", position);
        startActivity(operationsIntent);
    }
}
