 GRADLE: compile 'com.devfigas:marshmallow-permission:0.0.3'

Use when working with these permissions on marshmallow or next versions :

READ_CALENDAR,WRITE_CALENDAR,CAMERA,READ_CONTACTS,WRITE_CONTACTS,GET_ACCOUNTS,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION,RECORD_AUDIO,READ_PHONE_STATE,CALL_PHONE,READ_CALL_LOG,WRITE_CALL_LOG,ADD_VOICEMAIL,USE_SIP,PROCESS_OUTGOING_CALLS,BODY_SENSORS,SEND_SMS,RECEIVE_SMS,READ_SMS,RECEIVE_WAP_PUSH,RECEIVE_MMS,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE

//###EXAMPLE ###

import android.Manifest;
import android.os.Bundle;
import com.devfigas.permission.MarshmallowActivity;
import com.devfigas.permission.MarshmallowPermission;

public class MainActivity extends MarshmallowActivity {

    int CODE_CALENDAR = 1;
    int CODE_CONTACTS_AND_LOCATION = 2;
    int CODE_CALL_PHONE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //single permission not required (the application does work without this permission)
        //required is the boolean parameter in constructor
         
         setCallbackPermission(new MarshmallowPermission(this,Manifest.permission.WRITE_CALENDAR,CODE_CALENDAR,false,"access to  calendar is important because ..."){

           @Override
           public void granted(String permission) {
                // method to execute when permission is allowed
           }

           @Override
           public void denied(String permission){
               // method to execute when permission is denied (optional)
           }
       });

        //multiple permissions required (the application does not work without this permission)
        setCallbackPermission(new MarshmallowPermission(this, new String[]{Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION},
                CODE_CONTACTS_AND_LOCATION, true, ""access to contacts and location is important because ..."") {
            @Override
            public void granted(String permission) {
                // method to execute when permission is allowed
                 if(permission.equals(Manifest.permission.WRITE_CONTACTS)){
                    // method to execute when permission is WRITE_CONTACTS
                 }
                 else if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // // method to execute when permission is ACCESS_FINE_LOCATION
                 }
                
            }

        });
        
        //single permission  required with custom dialog
        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.CALL_PHONE,
                CODE_CALL_PHONE,true,"Access callphone is importante because ...") {
            @Override
            public void granted(String permission) {

            }
        };
        permission.configDialog("Custom message","custom text yes","custom text no");
        setCallbackPermission(permission);
    }
}

