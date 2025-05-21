package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.fragment.BottomSheetFragment;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button openBottomSheetButton = findViewById(R.id.button_open_bottom_sheet);
        openBottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });
    }
}
