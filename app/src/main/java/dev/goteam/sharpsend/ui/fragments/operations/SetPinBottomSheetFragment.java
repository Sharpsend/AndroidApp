package dev.goteam.sharpsend.ui.fragments.operations;

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

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectBankBinding;
import dev.goteam.sharpsend.databinding.FragmentSetPinBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.ui.adapters.BanksRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnBankItemSelected;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.ui.listeners.OnPinSetListener;

public class SetPinBottomSheetFragment extends RoundedBottomSheetFragment {

    private FragmentSetPinBinding binding;
    private OnPinSetListener mOnPinSetListener;

    public SetPinBottomSheetFragment(OnPinSetListener mOnPinSetListener) {
        this.mOnPinSetListener = mOnPinSetListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSetPinBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PIN = binding.setPinTextField.getEditText().getText().toString();
                mOnPinSetListener.onPinSet(PIN);
                dismiss();
            }
        });

        binding.setPinTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String PIN = editable.toString();
                binding.proceedButton.setEnabled(PIN.length() == 4);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPinSetListener.onPinSetCanceled();
                dismiss();
            }
        });
    }
}
