package dev.goteam.sharpsend.ui.fragments.operations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.AccessibilityTipsFragment;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentPayBillsBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.db.entities.HomeItem;
import dev.goteam.sharpsend.db.entities.MobileItem;
import dev.goteam.sharpsend.db.entities.PayBillItem;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.adapters.PayBillRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class PayBillsFragment extends Fragment implements OnBankSelection, PayBillRVAdapter.OnPayBillItemSelectedListener {
    private final String TAG = getClass().getSimpleName();
    private FragmentPayBillsBinding binding;
    private BankItem.Bank senderBank;
    private List<PayBillItem> payBillItems;
    private OperationsViewModel operationsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayBillsBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operationsViewModel = new ViewModelProvider(getActivity()).get(OperationsViewModel.class);

        OperationsActivity.title.setText("Pay Bills");

        binding.selectBankField.getEditText().setOnClickListener(view1 -> launchBankSelection());

        payBillItems = new ArrayList<>();
        payBillItems.add(new PayBillItem("Data Services", Constants.DATA_SERVICES));
        payBillItems.add(new PayBillItem("DSTV Subscription", Constants.DSTV_SUB));
        payBillItems.add(new PayBillItem("GOTV Subscription", "dataSvc"));
        payBillItems.add(new PayBillItem("Ikeja Electric", "dataSvc"));
        payBillItems.add(new PayBillItem("Ibadan Electric - IBIDEC", "dataSvc"));
        payBillItems.add(new PayBillItem("WAEC Result Checker PIN", "dataSvc"));

        PayBillRVAdapter payBillRVAdapter = new PayBillRVAdapter(this, payBillItems);
        binding.payBillRV.setAdapter(payBillRVAdapter);

/*
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Validate Input
                if (senderBank != null && recipientMobile != null) {
                    String code = null;
                    Intent intent = null;
                    switch (recipientMobile.getId()) {
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
                           */
/* code = senderBank.getSelfRechargeCode(binding.amountField.getEditText().getText().toString());

                            intent = operationsViewModel.getCallIntent(code, OperationsActivity.user.getSlotIdx());

                            if (intent != null) {
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
                            }*//*


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

                            // New method
                            */
/*if (senderBank.getOthersRechargeAction().getActionID() != null) {
                                Intent j = new HoverParameters.Builder(requireActivity())
                                        .request(senderBank.getOthersRechargeAction().getActionID()) // Add your action ID here
                                        .extra("Amount", binding.amountField.getEditText().getText().toString())
                                        .extra("PhoneNumber", binding.phoneNumberField.getEditText().getText().toString())
                                        .setSim(operationsViewModel.getNetworkFromSlot(OperationsActivity.user.getSlotIdx()).getNetworkOperatorCode())
                                        .finalMsgDisplayTime(0)
                                        .buildIntent();
                                ((OperationsActivity) requireActivity()).getStartActivityModel()
                                        .postValue(new StartActivityModel(j, Constants.OPERATIONS_CODE));
                            } else {
                                Toast.makeText(requireActivity(), "Application dosen't support 3rd party recharge for this bank yet, Thank you", Toast.LENGTH_SHORT).show();
                            }*//*

                            break;
                    }
                }
            }
        });
*/
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        Fragment fragment;

        PayBillItem payBillItem = payBillItems.get(position);

        switch (payBillItem.getId()) {
            case Constants.DATA_SERVICES:

                fragment = new SimpleFragment("Buy Data", Constants.DATA_SERVICES);
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "SimpleFragment");
                fragmentTransaction.commit();
                break;
            case Constants.DSTV_SUB:

                fragment = new SimpleFragment("Buy Data", Constants.DATA_SERVICES);
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "SimpleFragment");
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }

    private void launchBankSelection() {
        SelectBankBottomSheetFragment selectBankBottomSheetFragment = new SelectBankBottomSheetFragment(this);
        selectBankBottomSheetFragment.show(getParentFragmentManager(), "buyAirtimeFragment");
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
}
