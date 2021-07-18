package au.edu.jcu.flatOnRent.onrent.FirebaseHelper;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.startingPages.LocationActivity;
import au.edu.jcu.flatOnRent.onrent.startingPages.RegisterActivity;

public class EnternNumber extends AppCompatActivity {

    TextInputEditText et_number, otp;
    Button bt_login, register, verify;
    CountryCodePicker ccp;
    FirebaseAuth mAuth;
    DatabaseReference check_child,fb_data;
    Toolbar toolbar;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    LinearLayout otp_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entern_number);

        //custom tool bar
        toolbar=findViewById(R.id.toolbarlogin);
        toolbar.setTitle("Login");

        //used to authenticate the firebase or to initiallize the auth instance
        mAuth=FirebaseAuth.getInstance();
        fb_data=FirebaseDatabase.getInstance().getReference();
        check_child=FirebaseDatabase.getInstance().getReference().child("Register");

        otp_layout=findViewById(R.id.otp_layout);

        //id of edit text
        et_number=findViewById(R.id.et_number);
        otp=findViewById(R.id.otp);
        //id of buttons
        bt_login=findViewById(R.id.bt_login);
        verify=findViewById(R.id.bt_verify);
        register=findViewById(R.id.register);

        ccp=findViewById(R.id.ccp);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mob_number=et_number.getText().toString();
                String code=ccp.getSelectedCountryCodeWithPlus();
                if (mob_number.isEmpty())
                {
                  et_number.setError("Enter a valid mobile number");
                  et_number.setFocusable(true);
                }
                else
                {
                    checkData(code+mob_number);
                }
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp_code=otp.getText().toString();
                if(!TextUtils.isEmpty(otp_code))
                {
                    otp.setError("Can't be null");
                    otp.setFocusable(true);
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp_code);

                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to switch between platforms intent is used
                Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void checkData(final String s)
    {
        fb_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String mm=mVerificationId.toString();
                if (dataSnapshot.hasChild(mVerificationId)) {
                Toast.makeText(EnternNumber.this, "Register First", Toast.LENGTH_SHORT).show();
                } else {
                      login(s);
                      }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void login(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otp_layout.setVisibility(View.VISIBLE);
                        mVerificationId = s;
                        mResendToken = forceResendingToken;

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(EnternNumber.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                                Intent i=new Intent(getApplicationContext(),LocationActivity.class);
                                startActivity(i);
                                finish();
                            // ...
                        }
                        else
                            {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}

