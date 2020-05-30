package dev.goteam.sharpsend.ui.fragments.operations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectMobileNetworkBinding;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.ui.adapters.NetworkRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnNetworkItemSelection;
import dev.goteam.sharpsend.ui.listeners.OnNetworkSelection;

public class SelectMobileNetworkBottomSheetFragment extends RoundedBottomSheetFragment
    implements OnNetworkItemSelection {

    private OnNetworkSelection onNetworkSelection;
    private ArrayList<NetworkItem.Network> networks;

    private FragmentSelectMobileNetworkBinding binding;
    private NetworkRVAdapter networkRVAdapter;
    private NetworkItem.Network selectedNetwork;

    public SelectMobileNetworkBottomSheetFragment(
            OnNetworkSelection networkSelection,
            ArrayList<NetworkItem.Network> networks ) {
        this.onNetworkSelection = networkSelection;
        this.networks = networks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_mobile_network, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.networkList.setHasFixedSize(true);
        binding.networkList.setLayoutManager(new LinearLayoutManager(requireContext()));

        networkRVAdapter = new NetworkRVAdapter(requireContext(), networks, this);
        binding.networkList.setAdapter(networkRVAdapter);

        binding.proceedButton.setOnClickListener(view1 -> {
            onNetworkSelection.onNetworkSelected(selectedNetwork);
            dismiss();
        });

        binding.cancelButton.setOnClickListener(view1 -> {
            onNetworkSelection.onNetworkSelectionCanceled();
            dismiss();
        });
    }

    @Override
    public void onNetworkSelected(NetworkItem.Network network) {
        this.selectedNetwork = network;
    }
}
