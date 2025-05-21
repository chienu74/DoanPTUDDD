package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nhom24.doanptuddd.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public interface OnSpeedChangeListener {
        void onSpeedChanged(float speed);
    }

    private OnSpeedChangeListener speedChangeListener;

    public void setOnSpeedChangeListener(OnSpeedChangeListener listener) {
        this.speedChangeListener = listener;
    }
    public static BottomSheetFragment newInstance(float currentSpeed) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putFloat("current_speed", currentSpeed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);

        Button closeButton = view.findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        SeekBar seekBar = view.findViewById(R.id.skb_speed);
        TextView txtSpeed = view.findViewById(R.id.txt_speed);
        float currentSpeed = getArguments().getFloat("current_speed");
        int progress = (int) ((currentSpeed - 0.1f) / 1.9f * 100);
        seekBar.setProgress(progress);
        txtSpeed.setText(String.format("%.1fx", currentSpeed));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float speed = 0.1f + (progress / 100.0f) * 1.9f;
                txtSpeed.setText(String.format("%.1fx", speed));
                if (speedChangeListener != null) {
                    speedChangeListener.onSpeedChanged(speed);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }
}
