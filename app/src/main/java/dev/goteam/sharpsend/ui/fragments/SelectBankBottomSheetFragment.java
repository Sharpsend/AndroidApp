package dev.goteam.sharpsend.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectBankBinding;
import dev.goteam.sharpsend.db.entities.Bank;
import dev.goteam.sharpsend.ui.adapters.BanksAdapter;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelectedOnAdapterListener;
import dev.goteam.sharpsend.ui.listeners.OnBankSelectedListener;
import dev.goteam.sharpsend.utils.DataSource;

public class SelectBankBottomSheetFragment extends RoundedBottomSheetFragment implements OnBankItemSelectedOnAdapterListener {

    private FragmentSelectBankBinding binding;
    private int selectedBankIndex;
    private OnBankSelectedListener mOnBankSelectedListener;

    private BanksAdapter adapter;

    public SelectBankBottomSheetFragment(OnBankSelectedListener mOnBankSelectedListener) {
        this.mOnBankSelectedListener = mOnBankSelectedListener;
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
        Bundle arguments = getArguments();

        selectedBankIndex = arguments.getInt("selectedBankIndex", -1);

        adapter = new BanksAdapter(requireContext(), DataSource.getBanks(selectedBankIndex), this, false);
        binding.banksList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.banksList.setAdapter(adapter);

        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBankSelectedListener.onSenderBankSelected(selectedBankIndex);
                dismiss();
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBankSelectedListener.onSenderBankSelected(-2);
                dismiss();
            }
        });

    }

    @Override
    public void onBankItemSelected(int position) {
        selectedBankIndex = position;
        adapter.bankSelected(position);
    }
}
