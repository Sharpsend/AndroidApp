package dev.goteam.sharpsend.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.goteam.sharpsend.databinding.FragmentTransactionsBinding;
import dev.goteam.sharpsend.ui.adapters.TransactionsRVAdapter;
import dev.goteam.sharpsend.viewmodels.TransactionsViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransactionsFragment extends Fragment implements View.OnClickListener {

    private TransactionsViewModel transactionsViewModel;
    private FragmentTransactionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater);
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        transactionsViewModel.getTransactions().observe(getViewLifecycleOwner(), transactionList -> {
            if (transactionList != null) {

                binding.emptyTransactionLayout.setVisibility(View.GONE);
                binding.transactionsRV.setVisibility(View.VISIBLE);

                // Load Transactions
                Log.i(TAG, "onCreateView: " + transactionList.size());

                TransactionsRVAdapter transactionsRVAdapter = new TransactionsRVAdapter(requireContext(),
                        this, transactionList);
                binding.transactionsRV.setAdapter(transactionsRVAdapter);

            } else {
                binding.emptyTransactionLayout.setVisibility(View.VISIBLE);
                binding.transactionsRV.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {


    }
}
