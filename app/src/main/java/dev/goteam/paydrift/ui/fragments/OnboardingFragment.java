package dev.goteam.paydrift.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.ui.adapters.OnboardingAdapter;
import dev.goteam.paydrift.databinding.FragmentOnboardingBinding;

public class OnboardingFragment extends Fragment {

    private FragmentOnboardingBinding binding;
    private OnboardingAdapter onboardingAdapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onboardingAdapter = new OnboardingAdapter(requireActivity().getApplicationContext());

//        onpagechange listener for viewpager
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };

        binding.onboardingViewpager.addOnPageChangeListener(this.mOnPageChangeListener);
        binding.onboardingViewpager.setAdapter(onboardingAdapter);
        binding.setCurrentPage(binding.onboardingViewpager.getCurrentItem());
        binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OnboardingFragment.this).navigate(
                        R.id.action_onboardingFragment_to_registerFragment
                );
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.onboardingViewpager.removeOnPageChangeListener(this.mOnPageChangeListener);
    }

}
