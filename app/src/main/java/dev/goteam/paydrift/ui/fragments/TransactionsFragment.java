package dev.goteam.paydrift.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.goteam.paydrift.databinding.FragmentTransactionsBinding;
import dev.goteam.paydrift.viewmodels.TransactionsViewModel;

public class TransactionsFragment extends Fragment {

    private TransactionsViewModel transactionsViewModel;
    private FragmentTransactionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater);
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        transactionsViewModel.getTransactions().observe(getViewLifecycleOwner(), transactionList -> {
            if (transactionList != null) {

                binding.emptyTransactionLayout.setVisibility(View.VISIBLE);
                binding.transactionsRV.setVisibility(View.GONE);

                // Load

            } else {
                binding.emptyTransactionLayout.setVisibility(View.VISIBLE);
                binding.transactionsRV.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}
