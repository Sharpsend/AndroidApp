package dev.goteam.sharpsend.ui.fragments.operations;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.goteam.sharpsend.databinding.FragmentChangeUsernameBinding;
import dev.goteam.sharpsend.ui.listeners.OnUsernameChangedListener;

public class ChangeUsernameBottomSheetFragment extends RoundedBottomSheetFragment {

    private FragmentChangeUsernameBinding binding;
    private OnUsernameChangedListener onUsernameChangedListener;

    public ChangeUsernameBottomSheetFragment(OnUsernameChangedListener onUsernameChangedListener) {
        this.onUsernameChangedListener = onUsernameChangedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeUsernameBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = binding.changeUsernameTextField.getEditText().getText().toString();
                onUsernameChangedListener.onUsernameChanged(newUsername);
                dismiss();
            }
        });

        binding.changeUsernameTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = editable.toString();
                binding.changeButton.setEnabled(username.length() > 0);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUsernameChangedListener.onProcessCanceled();
                dismiss();
            }
        });
    }
}
