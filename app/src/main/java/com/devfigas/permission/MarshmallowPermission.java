package com.devfigas.permission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Andre Figas on 21/12/2015.
 */
public abstract class MarshmallowPermission {

    public abstract void granted();
    public void denied(){};

    public MarshmallowPermission(AppCompatActivity activity, String[] permission, int requestCode, boolean required, String detail){

        this.activity=activity;
        this.required = required;
        this.requestCode = requestCode;
        this.permission=permission;
        this.deitail=detail;

    }
    public MarshmallowPermission(AppCompatActivity activity, String permission, int requestCode, boolean required, String detail){

        this.activity=activity;
        this.required = required;
        this.requestCode = requestCode;
        this.permission=new String[]{permission};
        this.deitail=detail;


        //getPermission(activity,permission,requestCode,required, detail);
    }


    private boolean required=false;
    private AppCompatActivity activity = null;

    private String[] permission;
    private String deitail;

    private int requestCode =0;
    public int getRequestCode(){
       return this.requestCode;
    }

    protected boolean response = false;

    private String dlgText="Click Permissions and allow to continue", dlgPositive="Continue", dlgNegative="Exit";
    public void configDialog(String text, String positive, String negative){
        this.dlgText=text;
        this.dlgPositive=positive;
        this.dlgNegative=negative;
    }


   protected void getPermission(AppCompatActivity activity, String[] permission,int requestCode, boolean required, String detail){





     if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
         boolean granted = true;

         for(String p :permission){
             if(activity.checkSelfPermission(p)!= PackageManager.PERMISSION_GRANTED){
                 granted = false;
             }
         }

         if(granted){
            //acesso permitido
         }
         else{
             boolean rationale = false;

             for(String p :permission){
                 if(activity.shouldShowRequestPermissionRationale(p)){
                     rationale = true;
                 }
             }

             if (rationale){
                 //se ja foi negado anteriormente
                 if(detail!=null && !detail.equals(""))
                 Toast.makeText(activity, detail, Toast.LENGTH_LONG).show();

             }


             activity.requestPermissions(permission, requestCode);


         }
     }

 }


    protected void getPermission(){
        getPermission(this.activity,this.permission,this.requestCode, this.required,this.deitail);
    }

    protected void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        boolean exit = false;

        for (int result : grantResults) {

            if (result == PackageManager.PERMISSION_DENIED && required && !exit) {
                dialog();
                exit = true;
            }
            if (exit==false) response = true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode== this.requestCode){

            getPermission(activity,permission, this.requestCode,required,deitail);
        }
    }
    protected boolean allowed(){
        return this.response;
    }
    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(deitail);
        builder.setMessage(dlgText);
        builder.setPositiveButton(dlgPositive, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(i, requestCode);
            }
        });
        builder.setNegativeButton(dlgNegative, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });

        builder.setCancelable(false);

        AlertDialog alerta = builder.create();
        alerta.show();


    }
}
