package com.devfigas.permission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Andre Figas on 21/12/2015.
 */
public abstract class MarshmallowActivity  extends AppCompatActivity {

    MarshmallowPermission p;


    public void setCallbackPermission(MarshmallowPermission p){
       this.p = p;
        p.getPermission();
    }

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){

        p.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(p.allowed()){
            if(requestCode ==  p.getRequestCode()){

                p.granted();
            }
        }
        else{
            if(requestCode ==  p.getRequestCode()){

                p.denied();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        p.onActivityResult(requestCode, resultCode, data);
    }
    private void configDialog(String text, String positive, String negative){
        p.configDialog(text,positive,negative);
    }
}
