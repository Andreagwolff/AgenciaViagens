package com.minhaagenciadeviagens.agenciaviagens;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Constantes;

public class MainActivity extends AppCompatActivity {

    //Array de Permissões
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handle = new Handler();

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(arePermissionsEnabled()){
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }else{
                        requestMultiplePermissions();
                    }
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        }, 2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean arePermissionsEnabled(){
        for(String permission : permissions){
            if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestMultiplePermissions(){
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), Constantes.PERMISSION_REQUEST_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constantes.PERMISSION_REQUEST_CODE){
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    if(shouldShowRequestPermissionRationale(permissions[i])){
                        new AlertDialog.Builder(this)
                                .setMessage("Para o aplicativo funcionar corretamente é necessário que você aceite as permissões requiridas.")
                                .setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        requestMultiplePermissions();
                                    }
                                })
                                .setNegativeButton("Cancelar e Sair", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                })
                                .create()
                                .show();
                    }
                    return;
                }
            }
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}

