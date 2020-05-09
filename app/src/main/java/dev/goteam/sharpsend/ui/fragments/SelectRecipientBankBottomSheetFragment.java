package dev.goteam.paydrift.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentSelectBankBinding;
import dev.goteam.paydrift.databinding.FragmentSelectRecipientBankBinding;
import dev.goteam.paydrift.ui.adapters.BanksAdapter;
import dev.goteam.paydrift.ui.listeners.OnBankItemSelectedOnAdapterListener;
import dev.goteam.paydrift.ui.listeners.OnBankSelectedListener;
import dev.goteam.paydrift.utils.DataSource;

public class SelectRecipientBankBottomSheetFragment extends RoundedBottomSheetFragment implements OnBankItemSelectedOnAdapterListener {

    private FragmentSelectRecipientBankBinding binding;
    private int selectedBankIndex;
    private OnBankSelectedListener mOnBankSelectedListener;

    private BanksAdapter adapter;

    public SelectRecipientBankBottomSheetFragment(OnBankSelectedListener mOnBankSelectedListener) {
        this.mOnBankSelectedListener = mOnBankSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_recipient_bank, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        selectedBankIndex = arguments.getInt("selectedBankIndex", -1);

        adapter = new BanksAdapter(requireContext(), DataSource.getRecipientBanks(selectedBankIndex), this, true);
        binding.recipientBankList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recipientBankList.setAdapter(adapter);

        binding.cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBankSelectedListener.onRecipientBankSelected(-2);
                dismiss();
            }
        });

        binding.bankNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                adapter.getFilter().filter(text);
            }
        });

    }

    @Override
    public void onBankItemSelected(int position) {
        selectedBankIndex = position;
        adapter.bankSelected(position);

        mOnBankSelectedListener.onRecipientBankSelected(selectedBankIndex);
        dismiss();
    }
}
