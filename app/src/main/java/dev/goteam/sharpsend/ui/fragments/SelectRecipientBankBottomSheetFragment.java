package dev.goteam.sharpsend.ui.fragments;

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

import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectRecipientBankBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.adapters.RecipientBanksRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnRecipientBankItemSelected;
import dev.goteam.sharpsend.ui.listeners.OnRecipientBankSelection;

public class SelectRecipientBankBottomSheetFragment extends RoundedBottomSheetFragment implements OnRecipientBankItemSelected {

    private final List<BankItem.TransferBank> transferBanks;
    private FragmentSelectRecipientBankBinding binding;
    private OnRecipientBankSelection onRecipientBankSelectionListener;
    private RecipientBanksRVAdapter adapter;

    public SelectRecipientBankBottomSheetFragment(OnRecipientBankSelection onRecipientBankSelectionListener, List<BankItem.TransferBank> transferBanks) {
        this.onRecipientBankSelectionListener = onRecipientBankSelectionListener;
        this.transferBanks = transferBanks;
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

        adapter = new RecipientBanksRVAdapter(transferBanks, this);
        binding.recipientBankList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recipientBankList.setAdapter(adapter);

        binding.cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecipientBankSelectionListener.onSelectionCanceled();
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
    public void onBankItemSelected(BankItem.TransferBank transferBank) {
        onRecipientBankSelectionListener.onRecipientBankSelected(transferBank);
        dismiss();
    }
}
