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
import dev.goteam.sharpsend.databinding.FragmentSimpleBinding;
import dev.goteam.sharpsend.db.entities.BankItem;
import dev.goteam.sharpsend.db.entities.HomeItem;
import dev.goteam.sharpsend.db.entities.PayBillItem;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.adapters.PayBillRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnBankSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class SimpleFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private OperationsViewModel operationsViewModel;
    private FragmentSimpleBinding binding;
    private String code;
    private String title;

    SimpleFragment (String title, String code) {
        this.code = code;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSimpleBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operationsViewModel = new ViewModelProvider(getActivity()).get(OperationsViewModel.class);

        OperationsActivity.title.setText(title);

    }
}
