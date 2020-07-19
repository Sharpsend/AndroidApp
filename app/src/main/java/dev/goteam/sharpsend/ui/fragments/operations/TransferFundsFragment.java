package dev.goteam.sharpsend.ui.fragments.operations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hover.sdk.api.HoverParameters;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentTransferFundsBinding;
import dev.goteam.sharpsend.db.entities.Action;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.models.StartActivityModel;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.ui.listeners.OnRecipientBankSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class TransferFundsFragment extends Fragment implements OnBankSelection, OnRecipientBankSelection, TextWatcher {

    private BankItem.Bank senderBank;
    private Action recipientBank;
    private FragmentTransferFundsBinding binding;
    private final String TAG = getClass().getSimpleName();
    private OperationsViewModel operationsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_funds, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        operationsViewModel = new ViewModelProvider(getActivity()).get(OperationsViewModel.class);

        binding.senderBankField.getEditText().setOnClickListener(view1 -> launchBankSelection());

        binding.selectRecipientBankField.getEditText().setOnClickListener(view1 -> launchRecipientBankSelection());

        OperationsActivity.title.setText("Transfer Funds");
        binding.accountNumberField.getEditText().addTextChangedListener(this);
        binding.amountField.getEditText().addTextChangedListener(this);

        binding.sendButton.setOnClickListener(view1 -> {

            try {
                // TODO Validate inputs
                Intent i = new HoverParameters.Builder(requireActivity())
                        .request(recipientBank.getActionID()) // Add your action ID here
                        .extra("Amount", binding.amountField.getEditText().getText().toString())
                        .extra("Nuban", binding.accountNumberField.getEditText().getText().toString())
                        .finalMsgDisplayTime(0)
                        .setSim(operationsViewModel.getNetworkFromSlot(OperationsActivity.user.getSlotIdx()).getNetworkOperatorCode())
                        .buildIntent();
                ((OperationsActivity) requireActivity()).getStartActivityModel()
                        .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onClick: ERROR");
                /*if (e instanceof HoverConfigException) {
                    Toast.makeText(requireActivity(), "Internet ", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResultFrag: " + requestCode + "." + resultCode + "." + data);

    }

    private void launchBankSelection() {

        RoundedBottomSheetFragment mSelectBankBottomSheetFragment;
        mSelectBankBottomSheetFragment = new SelectBankBottomSheetFragment(this);
        mSelectBankBottomSheetFragment.show(getParentFragmentManager(), "selectBankModal");
    }

    private void launchRecipientBankSelection() {

        if (senderBank != null) {
            RoundedBottomSheetFragment mSelectBankBottomSheetFragment;
            mSelectBankBottomSheetFragment = new SelectRecipientBankBottomSheetFragment(this, senderBank.getTransferBankActionList());
            mSelectBankBottomSheetFragment.show(getParentFragmentManager(), "selectRecipientBankModal");
        } else {
            Toast.makeText(requireActivity(), "Input your bank account first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBankSelected(BankItem.Bank bank) {
        senderBank = bank;
        binding.senderBankField.getEditText().setText(bank.getName());
    }

    @Override
    public void onRecipientBankSelected(Action transferBankAction) {
        this.recipientBank = transferBankAction;
        binding.selectRecipientBankField.getEditText().setText(transferBankAction.getName());
    }

    @Override
    public void onSelectionCanceled() {
        if (senderBank != null) {
            recipientBank = null;
            binding.selectRecipientBankField.getEditText().setText(null);
        } else {
            binding.senderBankField.getEditText().setText(null);
        }
    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if (
                !binding.amountField.getEditText().getText().toString().isEmpty() &&
                        !binding.accountNumberField.getEditText().getText().toString().isEmpty() &&
                        binding.accountNumberField.getEditText().getText().toString().length() == 10 &&
                        !binding.senderBankField.getEditText().getText().toString().isEmpty() &&
                        !binding.selectRecipientBankField.getEditText().getText().toString().isEmpty()
        ) {
            binding.sendButton.setEnabled(true);
        } else {
            binding.sendButton.setEnabled(false);
        }
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
}
