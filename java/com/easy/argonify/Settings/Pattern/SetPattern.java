package com.easy.argonify.Settings.Pattern;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.argonify.R;

import java.math.BigDecimal;

public class SetPattern extends Pattern {
    private final SetPatternActivity setPatternActivity;

    SetPattern(SetPatternActivity.SetPatternInitialisation initData) {
        super(initData);
        setPatternActivity = (SetPatternActivity) initData.parentActivity;
    }

    @Override
    void onPatternConfirmation() {
        Intent requestPatternIntent = new Intent(setPatternActivity, RequestPatternActivity.class);
        requestPatternIntent.putExtra(REQUESTED_PATTERN, getHashedPattern());
        requestPatternIntent.putExtra(ACTION_ON_CONFIRM, SAVE_PATTERN);
        requestPatternIntent.putExtra(REQUEST_REASON, PATTERN_REASON_REDRAW_CONFIRM);

        setPatternActivity.startActivity(requestPatternIntent);
        setPatternActivity.finish();
    }

    @Override
    void onControlStateChange() {
        setConfirmationButtonVisibility();
        updateRequirementDisplay();
    }

    private void updateRequirementDisplay() {
        ImageView imgLength = setPatternActivity.findViewById(R.id.img_setPatternRequirementLength);
        ImageView imgFavourite = setPatternActivity.findViewById(R.id.img_setPatternRequirementFavourite);
        ImageView imgTraffic = setPatternActivity.findViewById(R.id.img_setPatternRequirementTrafficlight);
        ImageView imgSliders = setPatternActivity.findViewById(R.id.img_setPatternRequirementSliders);

        updateRequirement(imgLength, getStatusLength());
        updateRequirement(imgFavourite, getStatusFavourite());
        updateRequirement(imgTraffic, getStatusTraffic());
        updateRequirement(imgSliders, getStatusSliders());
    }

    private void updateRequirement(ImageView img, boolean status) {
        if (status)
            img.setImageDrawable(AppCompatResources.getDrawable(setPatternActivity, R.drawable.circle_status_true));
        else
            img.setImageDrawable(AppCompatResources.getDrawable(setPatternActivity, R.drawable.circle_status_false));
    }

    private void setConfirmationButtonVisibility() {
        View confirmButton = setPatternActivity.findViewById(R.id.fab_setPatternConfirm);
        if (fulfillsRequirements())
            confirmButton.setVisibility(View.VISIBLE);
        else
            confirmButton.setVisibility(View.INVISIBLE);
    }

    private boolean fulfillsRequirements() {
        return getStatusLength() &&
                getStatusFavourite() &&
                getStatusTraffic() &&
                getStatusSliders();
    }

    private boolean getStatusLength()    { return selectedCells >= MIN_SELECTABLE_CELLS; }
    private boolean getStatusFavourite() { return !favCell.equals(EMPTY); }
    private boolean getStatusTraffic()   { return trafficData >= 0; }
    private boolean getStatusSliders()   { return usedSeekBar[TOP_BAR_INDEX] && usedSeekBar[BOTTOM_BAR_INDEX]; }
}
