package dev.goteam.paydrift.ui.fragments;

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

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentSelectBankBinding;
import dev.goteam.paydrift.db.entities.Bank;
import dev.goteam.paydrift.ui.adapters.BanksAdapter;
import dev.goteam.paydrift.ui.listeners.OnBankItemSelectedOnAdapterListener;
import dev.goteam.paydrift.ui.listeners.OnBankSelectedListener;
import dev.goteam.paydrift.utils.DataSource;

public class SelectBankBottomSheetFragment extends RoundedBottomSheetFragment implements OnBankItemSelectedOnAdapterListener {

    private FragmentSelectBankBinding binding;
    private int selectedBankIndex;
    private boolean isRecipient;
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
        isRecipient = arguments.getBoolean("isRecipient");

        adapter = new BanksAdapter(requireContext(), DataSource.getBanks(selectedBankIndex), this);
        binding.banksList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.banksList.setAdapter(adapter);

        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecipient) {
                    mOnBankSelectedListener.onRecipientBankSelected(selectedBankIndex);
                } else {
                    mOnBankSelectedListener.onSenderBankSelected(selectedBankIndex);
                }
                dismiss();
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecipient) {
                    mOnBankSelectedListener.onRecipientBankSelected(-2);
                } else {
                    mOnBankSelectedListener.onSenderBankSelected(-2);
                }
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
