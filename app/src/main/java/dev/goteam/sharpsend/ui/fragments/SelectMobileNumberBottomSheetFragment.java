package dev.goteam.sharpsend.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectMobileNumberBinding;
import dev.goteam.sharpsend.db.entities.MobileItem;
import dev.goteam.sharpsend.ui.adapters.MobileRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnMobileItemSelection;
import dev.goteam.sharpsend.ui.listeners.OnMobileSelection;

public class SelectMobileNumberBottomSheetFragment extends RoundedBottomSheetFragment
    implements OnMobileItemSelection {

    private OnMobileSelection mobileSelection;
    private ArrayList<MobileItem.Mobile> mobiles;

    private FragmentSelectMobileNumberBinding binding;
    private MobileRVAdapter mobileRVAdapter;
    private MobileItem.Mobile selectedMobile;

    public SelectMobileNumberBottomSheetFragment(
            OnMobileSelection mobileSelection,
            ArrayList<MobileItem.Mobile> mobiles
    ) {
        this.mobileSelection = mobileSelection;
        this.mobiles = mobiles;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_mobile_number, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mobileList.setHasFixedSize(true);
        binding.mobileList.setLayoutManager(new LinearLayoutManager(requireContext()));

        mobileRVAdapter = new MobileRVAdapter(requireContext(), mobiles, this);
        binding.mobileList.setAdapter(mobileRVAdapter);

        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileSelection.onMobileSelected(selectedMobile);
                dismiss();
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileSelection.onMobileSelectionCanceled();
                dismiss();
            }
        });
    }

    @Override
    public void onMobileSelected(MobileItem.Mobile mobile) {
        selectedMobile = mobile;
    }
}
