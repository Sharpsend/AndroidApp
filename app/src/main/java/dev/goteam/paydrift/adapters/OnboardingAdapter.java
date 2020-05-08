package dev.goteam.paydrift.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import dev.goteam.paydrift.R;

public class OnboardingAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private int[] images = {
            R.drawable.onboarding_image_one, R.drawable.onboarding_image_two, R.drawable.onboarding_image_three
    };

    private int[] labels = {
            R.string.transfer_funds_text, R.string.buy_airtime_text, R.string.check_airtime_text
    };

    private int[] details = {
        R.string.transfer_funds_details, R.string.buy_airtime_details, R.string.check_airtime_details
    };

    public OnboardingAdapter(Context context) {
        this.mContext = context;
//        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View onboardingView = this.mLayoutInflater.inflate(R.layout.item_onboarding, container, false);

        TextView label = onboardingView.findViewById(R.id.onboarding_item_label);
        ImageView image = onboardingView.findViewById(R.id.onboarding_item_image);
        TextView detail = onboardingView.findViewById(R.id.onboarding_item_details);

        label.setText(this.mContext.getResources().getString(labels[position]));
        detail.setText(this.mContext.getResources().getString(details[position]));
        image.setImageResource(images[position]);

        container.addView(onboardingView);

        return onboardingView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }
}
