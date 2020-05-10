package dev.goteam.sharpsend.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectBankBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.adapters.BanksAdapter;
import dev.goteam.sharpsend.ui.adapters.BanksRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelected;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelectedOnAdapterListener;
import dev.goteam.sharpsend.ui.listeners.OnBankSelectedListener;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.utils.DataSource;

public class SelectBankBottomSheetFragment extends RoundedBottomSheetFragment implements OnBankItemSelected {

    private FragmentSelectBankBinding binding;
    private BankItem.Bank bankSelection;
    private OnBankSelection onBankSelectionListener;

    private BanksRVAdapter adapter;

    public SelectBankBottomSheetFragment(OnBankSelection onBankSelectionListener) {
        this.onBankSelectionListener = onBankSelectionListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_bank, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BanksRVAdapter(requireContext(), new BankItem().getSupportedBanks(), this);
        binding.banksList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.banksList.setAdapter(adapter);

        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBankSelectionListener.onBankSelected(bankSelection);
                dismiss();
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBankSelectionListener.onSelectionCanceled();
                dismiss();
            }
        });
    }

    @Override
    public void onBankItemSelected(BankItem.Bank bank) {
        bankSelection = bank;
    }
}
