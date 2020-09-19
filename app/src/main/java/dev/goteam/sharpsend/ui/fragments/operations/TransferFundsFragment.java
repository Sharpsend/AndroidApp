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

import dev.goteam.sharpsend.BuildConfig;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentTransferFundsBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.db.entities.Selectable;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.ui.listeners.OnMobileSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.utils.Utils;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

import static dev.goteam.sharpsend.ui.activities.OperationsActivity.user;

public class TransferFundsFragment extends Fragment implements OnBankSelection, OnMobileSelection, TextWatcher {

    private BankItem.Bank senderBank;
    private Selectable.Item recipientBank;
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
        binding.senderBankField.setEndIconOnClickListener(view1 -> launchBankSelection());

        binding.selectRecipientBankField.getEditText().setOnClickListener(view1 -> launchRecipientBankSelection());
        binding.selectRecipientBankField.setEndIconOnClickListener(view1 -> launchRecipientBankSelection());

        OperationsActivity.title.setText("Transfer Funds");
        binding.accountNumberField.getEditText().addTextChangedListener(this);
        binding.amountField.getEditText().addTextChangedListener(this);

        binding.sendButton.setOnClickListener(view1 -> {
            Utils.closeKeyboard(requireActivity().getCurrentFocus(), requireContext());
            transferFunds();
            /*try {
                // TODO Validate inputs
                Intent i = new HoverParameters.Builder(requireActivity())
                        .request(recipientBank.getActionID()) // Add your action ID here
                        .extra("Amount", binding.amountField.getEditText().getText().toString())
                        .extra("Nuban", binding.accountNumberField.getEditText().getText().toString())
                        .finalMsgDisplayTime(0)
                        .setSim(operationsViewModel.getNetworkFromSlot(user.getSlotIdx()).getNetworkOperatorCode())
                        .buildIntent();
                ((OperationsActivity) requireActivity()).getStartActivityModel()
                        .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onClick: ERROR");
                *//*if (e instanceof HoverConfigException) {
                    Toast.makeText(requireActivity(), "Internet ", Toast.LENGTH_SHORT).show();
                }*//*
            }*/
        });
    }

    public void transferFunds() {
        int slotIdx = user.getSlotIdx();
        try {
            Intent intent;
            if (recipientBank != null && recipientBank.getId().equals(Constants.TRANSFER_SAME_BANK)) {
                 intent = operationsViewModel.getCallIntent(
                        senderBank.getTransferSameBankCode(
                                binding.amountField.getEditText().getText().toString(),
                                binding.accountNumberField.getEditText().getText().toString()
                        ),
                        slotIdx
                );
            } else {
                intent = operationsViewModel.getCallIntent(
                        senderBank.getTransferBankCode(
                                binding.amountField.getEditText().getText().toString(),
                                binding.accountNumberField.getEditText().getText().toString()
                        ),
                        slotIdx
                );
            }

            if (intent != null) {
                startActivity(intent);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Toast.makeText(getActivity(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } else {
                Toast.makeText(getActivity(), "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
            }
        }
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
        if (senderBank != null && senderBank.getTransferItems() != null) {
        SelectAirtimeRecipientBottomSheetFragment selectAirtimeRecipientBottomSheetFragment =
                new SelectAirtimeRecipientBottomSheetFragment(this, senderBank.getTransferItems(), "Recipient Bank");
        selectAirtimeRecipientBottomSheetFragment.show(getParentFragmentManager(), "buyAirtimeFragment");
        } else {
            Toast.makeText(requireActivity(), "Select your bank first", Toast.LENGTH_SHORT).show();
        }
        /*if (senderBank != null) {
            RoundedBottomSheetFragment mSelectBankBottomSheetFragment;
            mSelectBankBottomSheetFragment = new SelectRecipientBankBottomSheetFragment(this, senderBank.getTransferBankActionList());
            mSelectBankBottomSheetFragment.show(getParentFragmentManager(), "selectRecipientBankModal");
        } else {
            Toast.makeText(requireActivity(), "Input your bank account first", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onBankSelected(BankItem.Bank bank) {
        senderBank = bank;
        setUpBank();
    }

    private void setUpBank() {
        if (senderBank != null && senderBank.getTransferItems() != null) {

            // If Sender bank support same bank transfer
            binding.selectRecipientBankField.setVisibility(View.VISIBLE);
            binding.sendButton.setEnabled(false);
        } else {

            // If Sender bank doesn't support same bank transfer
            binding.selectRecipientBankField.setVisibility(View.GONE);
            binding.selectRecipientBankField.getEditText().setText(null);
            recipientBank = null;
        }
        binding.senderBankField.getEditText().setText(senderBank.getName());

        /*if (nullified) {
            binding.sendButton.setEnabled(false);
        }*/
    }

    @Override
    public void onMobileSelected(Selectable.Item item) {
        this.recipientBank = item;
        binding.selectRecipientBankField.getEditText().setText(item.getName());
        afterTextChanged(null);
    }

    @Override
    public void onSelectionCanceled() {
        if (senderBank != null) {
            recipientBank = null;
            binding.selectRecipientBankField.getEditText().setText(null);
        } else {
            binding.senderBankField.getEditText().setText(null);
        }
        afterTextChanged(null);
    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if (
                !binding.amountField.getEditText().getText().toString().isEmpty() &&
                        !binding.accountNumberField.getEditText().getText().toString().isEmpty() &&
                        binding.accountNumberField.getEditText().getText().toString().length() == 10 &&
                        !binding.senderBankField.getEditText().getText().toString().isEmpty()
        ) {
            if (senderBank.getTransferItems() == null){

                // If selectRecipientBankField doesn't need to be filled
                binding.sendButton.setEnabled(true);
            } else if (!binding.selectRecipientBankField.getEditText().getText().toString().isEmpty()) {

                // If selectRecipientBankField needs to be filled and is not empty
                binding.sendButton.setEnabled(true);
            } else {
                binding.sendButton.setEnabled(false);
            }
        } else {
            binding.sendButton.setEnabled(false);
        }
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onMobileSelectionCanceled() {

    }
}
