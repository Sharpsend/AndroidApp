package dev.goteam.sharpsend.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.DialogTransactionSuccessfulBinding;

public class TransactionSuccessDialog extends DialogFragment {

    String message;
    private DialogTransactionSuccessfulBinding binding;

    public TransactionSuccessDialog(String message) {
        this.message = message;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogTransactionSuccessfulBinding.inflate(inflater, container, false);
        binding.transactionResponseText.setText(message);
        binding.closeBtn.setOnClickListener(view -> {
            requireActivity().finish();
        });
        return binding.getRoot();
    }

    public void onStart() {
        super.onStart();

        if(getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }

}
