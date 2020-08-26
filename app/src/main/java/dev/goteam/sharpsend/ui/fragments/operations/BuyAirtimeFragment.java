package dev.goteam.sharpsend.ui.fragments.operations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hover.sdk.api.HoverParameters;

import dev.goteam.sharpsend.databinding.FragmentBuyAirtimeBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.db.entities.Selectable;
import dev.goteam.sharpsend.models.StartActivityModel;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.ui.listeners.OnContactSelectionListener;
import dev.goteam.sharpsend.ui.listeners.OnMobileSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.utils.Utils;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class BuyAirtimeFragment extends Fragment implements OnBankSelection, OnMobileSelection, TextWatcher {
    private final String TAG = getClass().getSimpleName();
    private FragmentBuyAirtimeBinding binding;
    private BankItem.Bank senderBank;
    private Selectable.Item recipientItem;
    private OperationsViewModel operationsViewModel;

    private OnContactSelectionListener mOnContactSelectionListener;

    public BuyAirtimeFragment(OnContactSelectionListener mOnContactSelectionListener) {
        this.mOnContactSelectionListener = mOnContactSelectionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyAirtimeBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operationsViewModel = new ViewModelProvider(getActivity()).get(OperationsViewModel.class);

        OperationsActivity.title.setText("Buy Airtime");

        binding.selectBankField.getEditText().setOnClickListener(view1 -> launchBankSelection());
        binding.selectBankField.setEndIconOnClickListener(view1 -> launchBankSelection());
        binding.phoneNumberField.setEndIconOnClickListener((phoneNumberView) -> {
            openContacts();
        });

        binding.selectMobileNumberField.getEditText().setOnClickListener(view11 -> launchMobileSelection());
        binding.selectMobileNumberField.setEndIconOnClickListener(view11 -> launchMobileSelection());

        binding.phoneNumberField.getEditText().addTextChangedListener(this);
        binding.amountField.getEditText().addTextChangedListener(this);

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.closeKeyboard(requireActivity().getCurrentFocus(), requireContext());
                // TODO Validate Input
                if (senderBank != null && recipientItem != null) {
                    String code = null;
                    Intent intent = null;
                    switch (recipientItem.getId()) {
                        case Constants.MOBILE_NUMBER_SELF:

                            Intent i = new HoverParameters.Builder(requireActivity())
                                    .request(senderBank.getSelfRechargeAction().getActionID())
                                    .extra("Amount", binding.amountField.getEditText().getText().toString())
                                    .finalMsgDisplayTime(0)
                                    .setSim(operationsViewModel.getNetworkFromSlot(OperationsActivity.user.getSlotIdx()).getNetworkOperatorCode())
                                    .buildIntent();
                            ((OperationsActivity) requireActivity()).getStartActivityModel()
                                    .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));

                            // New method
                           /* code = senderBank.getSelfRechargeCode(binding.amountField.getEditText().getText().toString());

                            intent = operationsViewModel.getCallIntent(code, OperationsActivity.user.getSlotIdx());

                            if (intent != null) {
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
                            }*/

                            break;
                        case Constants.MOBILE_NUMBER_THIRD_PARTY:

                            code = senderBank.getOthersRechargeCode(binding.amountField.getEditText().getText().toString(), binding.phoneNumberField.getEditText().getText().toString());

                            if (code != null) {
                                intent = operationsViewModel.getCallIntent(code, OperationsActivity.user.getSlotIdx());

                                if (intent != null) {
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getContext(), "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(requireActivity(), "Application dosen't support 3rd party recharge for this bank yet, Thank you", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            }
        });
    }

    private void openContacts() {
        mOnContactSelectionListener.openContacts();
    }

    private void launchBankSelection() {
        SelectBankBottomSheetFragment selectBankBottomSheetFragment = new SelectBankBottomSheetFragment(this);
        selectBankBottomSheetFragment.show(getParentFragmentManager(), "buyAirtimeFragment");
    }

    private void launchMobileSelection() {
        SelectAirtimeRecipientBottomSheetFragment selectAirtimeRecipientBottomSheetFragment =
                new SelectAirtimeRecipientBottomSheetFragment(this, new Selectable().getMobileItems(), "Mobile number");
        selectAirtimeRecipientBottomSheetFragment.show(getParentFragmentManager(), "buyAirtimeFragment");
    }

    @Override
    public void onBankSelected(BankItem.Bank bank) {
        this.senderBank = bank;
        binding.selectBankField.getEditText().setText(this.senderBank.getName());
    }

    @Override
    public void onSelectionCanceled() {
        this.senderBank = null;
        binding.selectBankField.getEditText().setText(null);
    }

    @Override
    public void onMobileSelected(Selectable.Item item) {
        this.recipientItem = item;
        binding.selectMobileNumberField.getEditText().setText(this.recipientItem.getName());
        setUpPhone(false);
    }

    public void setThirdPartyMobileNumber(String number) {
        binding.phoneNumberField.getEditText().setText(number);
    }

    @Override
    public void onMobileSelectionCanceled() {
        this.recipientItem = null;
        binding.selectMobileNumberField.getEditText().setText(null);

        setUpPhone(true);
    }

    private void setUpPhone(boolean nullified) {
        if (this.recipientItem == null || this.recipientItem.getId().equals(Constants.MOBILE_NUMBER_SELF)) {
            binding.phoneNumberField.setVisibility(View.GONE);
            binding.phoneNumberHelperText.setVisibility(View.GONE);
        } else if (this.recipientItem.getId().equals(Constants.MOBILE_NUMBER_THIRD_PARTY)) {
            binding.phoneNumberField.setVisibility(View.VISIBLE);
            binding.phoneNumberHelperText.setVisibility(View.VISIBLE);
            binding.sendButton.setEnabled(false);
        }

        if (nullified) {
            binding.sendButton.setEnabled(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (
                this.recipientItem != null && this.senderBank != null
                        && !binding.amountField.getEditText().getText().toString().isEmpty()) {

            if (this.recipientItem.getId().equals(Constants.MOBILE_NUMBER_THIRD_PARTY) &&
                    binding.phoneNumberField.getEditText().getText().toString().length() == 11) {
                binding.sendButton.setEnabled(true);
            } else if (this.recipientItem.getId().equals(Constants.MOBILE_NUMBER_THIRD_PARTY)) {
                binding.sendButton.setEnabled(false);
            } else {
                binding.sendButton.setEnabled(true);
            }
        } else {
            binding.sendButton.setEnabled(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
