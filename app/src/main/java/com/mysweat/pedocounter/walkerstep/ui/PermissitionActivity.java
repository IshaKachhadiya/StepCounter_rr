package com.mysweat.pedocounter.walkerstep.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.AchiveDatabase;
import com.mysweat.pedocounter.walkerstep.Database.UserTable;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.R;


public class PermissitionActivity extends AppCompatActivity {
    TextView createProfile;
    Db_fun db;
    AchiveDatabase db_achieve;
    String name;
    EditText nameEditTxt;
    private static final String[] INITIAL_PERMS_NOTI = {"android.permission.POST_NOTIFICATIONS"};

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_profile);
        checkStepInfo();

        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        createProfile =  findViewById(R.id.createProfile);
        db = new Db_fun(this);
        db_achieve = new AchiveDatabase(this);
        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditTxt.getText().toString();
                if (PermissitionActivity.this.name.equals("")) {
                    if (Build.VERSION.SDK_INT > 28) {
                        if (ContextCompat.checkSelfPermission(PermissitionActivity.this, "android.permission.ACTIVITY_RECOGNITION") != 0) {
                            PermissitionActivity.this.checkActPermission();
                            return;
                        }
                       statussaveInfo();
                       startAct();
                        return;
                    }
                    statussaveInfo();
                    startAct();
                    return;
                }
                Toast.makeText(PermissitionActivity.this, getString(R.string.fill_fields), 0).show();
            }
        });
    }

    public void checkActPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACTIVITY_RECOGNITION") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACTIVITY_RECOGNITION")) {
                ActivityCompat.requestPermissions(PermissitionActivity.this, new String[]{"android.permission.ACTIVITY_RECOGNITION"}, 99);

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACTIVITY_RECOGNITION"}, 99);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 99) {
            return;
        }
        if (iArr.length > 0 && iArr[0] == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACTIVITY_RECOGNITION") == 0) {
            statussaveInfo();
            startAct();
        } else if (shouldShowRequestPermissionRationale("android.permission.ACTIVITY_RECOGNITION")) {
        } else {
            new AlertDialog.Builder(this).setTitle(getString(R.string.perm_header)).setMessage(getString(R.string.perm_msg)).setPositiveButton(getString(R.string.allow), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i2) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", PermissitionActivity.this.getPackageName(), null));
                    PermissitionActivity.this.startActivity(intent);
                }
            }).setNegativeButton(17039369, (DialogInterface.OnClickListener) null).setIcon(17301543).show();
        }
    }

    public void statussaveInfo() {
        SharedPreferences.Editor edit = getSharedPreferences("CurrentProfile", 0).edit();
        edit.putString("name", this.name);
        edit.apply();
        db.insertUsers(this.name, "3000", "Kilometers");
        db_achieve.insertAchieveSteps(this.name, "0");
        db_achieve.insertAchieveDistance(this.name, "0");
        db_achieve.insertAchieveCalories(this.name, "0");
        SharedPreferences.Editor edit2 = getSharedPreferences("InterStatus", 0).edit();
        edit2.putString("Show", "false");
        edit2.commit();
        SharedPreferences.Editor edit3 = getSharedPreferences("Goals", 0).edit();
        edit3.putString(UserTable.Goal, "3000");
        edit3.putString(UserTable.Unit, "Kilometers");
        edit3.apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
        finishAffinity();
    }

    public void checkStepInfo() {
        if (Build.VERSION.SDK_INT > 32) {
            requestPermissions(INITIAL_PERMS_NOTI, 126);
        }
    }

    public void startAct() {
        AdInterGD.getInstance().loadRewardInterOne(this, new AdInterGD.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(PermissitionActivity.this, MainActivity.class));
            }
        });
    }
}
