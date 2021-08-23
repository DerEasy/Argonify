package com.easy.argonify.Settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockPickerActivity;
import com.easy.argonify.Settings.Clear.DgClear;
import com.easy.argonify.Settings.Export.DgExport;
import com.easy.argonify.Settings.Import.DgImport;
import com.easy.argonify.Settings.PassGenConfig.PassGenConfigActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void applock(View v) {
        Intent applockPickerIntent = new Intent(this, ApplockPickerActivity.class);
        startActivity(applockPickerIntent);
    }

    public void passgenSettings(View v) {
        Intent passgenConfigIntent = new Intent(this, PassGenConfigActivity.class);
        startActivity(passgenConfigIntent);
    }

    public void importDatabase(View v) {
        if (!DgImport.isOpen) {
            askWritePermission();
            DgImport dgImport = new DgImport();
            dgImport.show(getSupportFragmentManager(), null);
        }
    }

    public void exportDatabase(View v) {
        if (!DgExport.isOpen) {
            askWritePermission();
            DgExport dgExport = new DgExport();
            dgExport.show(getSupportFragmentManager(), null);
        }
    }

    private void askWritePermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1
            );
    }

    public void clearDatabase(View view) {
        if (!DgClear.isOpen) {
            DgClear dgClear = new DgClear();
            dgClear.show(getSupportFragmentManager(), null);
        }
    }
}