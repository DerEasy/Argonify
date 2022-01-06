package com.easy.argonify.Settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockPickerActivity;
import com.easy.argonify.Settings.Clear.DgClear;
import com.easy.argonify.Settings.DBManipulation.DgExport;
import com.easy.argonify.Settings.DBManipulation.DgImport;
import com.easy.argonify.Settings.DBManipulation.DgPermissionRW;
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

    public void clearDatabase(View view) {
        if (!DgClear.isOpen) {
            DgClear dgClear = new DgClear();
            dgClear.show(getSupportFragmentManager(), null);
        }
    }


    /** -1: None, 0: import, 1: export */
    private int databaseAction = -1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!hasWritePermission(this)) return;

        switch (databaseAction) {
            case 0:
                DgImport dgImport = new DgImport();
                dgImport.show(getSupportFragmentManager(), null);
                break;
            case 1:
                DgExport dgExport = new DgExport();
                dgExport.show(getSupportFragmentManager(), null);
                break;
        }

        databaseAction = -1;
    }

    public void importDatabase(View v) {
        if (!DgImport.isOpen) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                askWritePermission(0);
            else if (openPermissionsScreen(this))
                return;

            if (hasWritePermission(this)) {
                DgImport dgImport = new DgImport();
                dgImport.show(getSupportFragmentManager(), null);
            }
        }
    }

    public void exportDatabase(View v) {
        if (!DgExport.isOpen) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                askWritePermission(1);
            else if (openPermissionsScreen(this))
                return;

            if (hasWritePermission(this)) {
                DgExport dgExport = new DgExport();
                dgExport.show(getSupportFragmentManager(), null);
            }
        }
    }

    private boolean openPermissionsScreen(FragmentActivity context) {
        if (!hasWritePermission(this)) {
            if (!DgPermissionRW.isOpen) {
                DgPermissionRW dgPermissionRW = new DgPermissionRW();
                dgPermissionRW.show(context.getSupportFragmentManager(), null);
            }
            return true;
        }
        return false;
    }

    private void askWritePermission(int action) {
        databaseAction = action;
        if (!hasWritePermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1
            );
        }
    }

    private boolean hasWritePermission(FragmentActivity context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }
}