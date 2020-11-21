package dev.goteam.sharpsend.ui.fragments.operations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentSelectAirtimeRecipientBinding;
import dev.goteam.sharpsend.db.entities.Selectable;
import dev.goteam.sharpsend.ui.adapters.MobileRVAdapter;
import dev.goteam.sharpsend.ui.listeners.OnMobileItemSelection;
import dev.goteam.sharpsend.ui.listeners.OnMobileSelection;

public class SelectAirtimeRecipientBottomSheetFragment extends RoundedBottomSheetFragment
    implements OnMobileItemSelection {

    private final String header;
    private OnMobileSelection mobileSelection;
    private ArrayList<Selectable.Item> items;

    private FragmentSelectAirtimeRecipientBinding binding;
    private MobileRVAdapter mobileRVAdapter;
    private Selectable.Item selectedItem;

    public SelectAirtimeRecipientBottomSheetFragment(
            OnMobileSelection mobileSelection,
            ArrayList<Selectable.Item> items, String header) {
        this.mobileSelection = mobileSelection;
        this.items = items;
        this.header = header;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_airtime_recipient, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mobileList.setHasFixedSize(true);
        binding.mobileList.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (header != null) {
            binding.selectorHeader.setText(header);
        }

        mobileRVAdapter = new MobileRVAdapter(requireContext(), items, this);
        binding.mobileList.setAdapter(mobileRVAdapter);

        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileSelection.onMobileSelected(selectedItem);
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
    public void onMobileSelected(Selectable.Item item) {
        selectedItem = item;
    }
}
