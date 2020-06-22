package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentHomeBinding;
import dev.goteam.sharpsend.db.entities.HomeItem;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.ui.adapters.HomeRVAdapter;
import dev.goteam.sharpsend.ui.fragments.operations.SelectMobileNetworkBottomSheetFragment;
import dev.goteam.sharpsend.ui.listeners.OnNetworkSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.HomeViewModel;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;

public class HomeFragment extends Fragment implements HomeRVAdapter.OnHomeItemSelectedListener, OnNetworkSelection {

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

       /* if (position != 2) {
            Intent operationsIntent = new Intent(requireActivity(), OperationsActivity.class);
            operationsIntent.putExtra("operation_id", position);
            startActivity(operationsIntent);
        } else {

            ArrayList<NetworkItem.Network> networks = new ArrayList<>();
            networks.add(new NetworkItem.Network("MTN", "mtn", R.drawable.mtn));
            networks.add(new NetworkItem.Network("GLO", "glo", R.drawable.glo));
            networks.add(new NetworkItem.Network("AIRTEL", "airtel", R.drawable.airtel));
            networks.add(new NetworkItem.Network("9MOBILE", "9mobile", R.drawable.mobile_9));

            SelectMobileNetworkBottomSheetFragment selectMobileNetworkBottomSheetFragment
                    = new SelectMobileNetworkBottomSheetFragment(
                    this, networks
            );
            selectMobileNetworkBottomSheetFragment.show(getParentFragmentManager(), "networkSelection");
        }
        switch (pos)*/
    }

    @Override
    public void onNetworkSelected(NetworkItem.NetworkImpl network) {
        Toast.makeText(requireContext(), "Network selected: " + network.getDisplayname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkSelectionCanceled() {
        // add stub code...
    }
}
