package dev.goteam.paydrift.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentTransferFundsBinding;
import dev.goteam.paydrift.db.entities.Bank;
import dev.goteam.paydrift.ui.listeners.OnBankSelectedListener;
import dev.goteam.paydrift.utils.Constants;
import dev.goteam.paydrift.utils.DataSource;

public class TransferFundsFragment extends Fragment implements OnBankSelectedListener, TextWatcher {

    private FragmentTransferFundsBinding binding;
    private int selectedSenderBank = Constants.NO_BANK_SELECTED;
    private int selectedRecipientBank = Constants.NO_BANK_SELECTED;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_funds, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.senderBankField.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchBankSelection(false);
            }
        });

        binding.selectRecipientBankField.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchBankSelection(true);
            }
        });

        binding.accountNumberField.getEditText().addTextChangedListener(this);
        binding.amountField.getEditText().addTextChangedListener(this);

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "send money from bank: " + DataSource.getBanks(selectedSenderBank).get(selectedSenderBank).getBankName() + " to: "
                         + DataSource.getBanks(selectedRecipientBank).get(selectedRecipientBank).getBankName() + " with an account number of"
                         + binding.accountNumberField.getEditText().getText().toString() + " an amount of: " +
                        binding.amountField.getEditText().getText().toString();

                Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void launchBankSelection(boolean isRecipient) {
        SelectBankBottomSheetFragment mSelectBankBottomSheetFragment = new SelectBankBottomSheetFragment(this);

        Bundle arguments = new Bundle();
        arguments.putInt("selectedBankIndex", isRecipient ? selectedRecipientBank : selectedSenderBank);
        arguments.putBoolean("isRecipient", isRecipient);

        mSelectBankBottomSheetFragment.setArguments(arguments);
        mSelectBankBottomSheetFragment.show(getParentFragmentManager(), "selectBankModal");
    }

    @Override
    public void onSenderBankSelected(int newBankIndex) {
        if (newBankIndex != -2 && newBankIndex != -1) {
            Bank bank = DataSource.getBanks(newBankIndex).get(newBankIndex);
            binding.senderBankField.getEditText().setText(bank.getBankName());
            selectedSenderBank = newBankIndex;
        }
    }

    @Override
    public void onRecipientBankSelected(int newBankIndex) {
        if (newBankIndex != -2 && newBankIndex != -1) {
            Bank bank = DataSource.getBanks(newBankIndex).get(newBankIndex);
            binding.selectRecipientBankField.getEditText().setText(bank.getBankName());
            selectedRecipientBank = newBankIndex;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (
                !binding.amountField.getEditText().getText().toString().isEmpty() &&
                        !binding.accountNumberField.getEditText().getText().toString().isEmpty() &&
                        binding.accountNumberField.getEditText().getText().toString().length() == 10 &&
                        selectedRecipientBank != -1 && selectedSenderBank != -1
        ) {
            binding.sendButton.setEnabled(true);
        } else {
            binding.sendButton.setEnabled(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
