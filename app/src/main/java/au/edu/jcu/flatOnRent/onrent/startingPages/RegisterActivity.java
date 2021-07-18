package au.edu.jcu.flatOnRent.onrent.startingPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import au.edu.jcu.flatOnRent.onrent.FirebaseHelper.EnternNumber;
import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.Seller.TabbedViewUser;
import au.edu.jcu.flatOnRent.onrent.helper.Validations;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference fb_db, fb_db1;
    String string;
    EditText name, email, number, otp;
    Button btn_register, btn_verify;
    LinearLayout ll;
    String mVerificationId;
    CountryCodePicker ccp;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    HashMap<String, String> hash_register = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        fb_db = FirebaseDatabase.getInstance().getReference();
        fb_db1 = FirebaseDatabase.getInstance().getReference().child("Register");

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.p_number);
        otp = findViewById(R.id.otp);
        ccp = findViewById(R.id.ccp);
        btn_register = findViewById(R.id.register);
        btn_verify = findViewById(R.id.verify_otp);
        ll=findViewById(R.id.layoutlinear);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations vv = new Validations();

                String u_name = name.getText().toString();
                String u_email = email.getText().toString();
                String u_number = number.getText().toString();
                String code = ccp.getSelectedCountryCodeWithPlus();

                if (u_name.isEmpty()) {
                    name.setError("Enter  a valid name");
                    name.setFocusable(true);
                } else if (!vv.email_validate(u_email)) {
                    email.setError("Enter  a valid email ID");
                    email.setFocusable(true);
                } else if (u_number.isEmpty()) {
                    number.setError("enter a valid number");
                    number.setFocusable(true);
                } else {
                    hash_register.put("email", u_email);
                    hash_register.put("name", u_name);
                    hash_register.put("profilepic","kjjk");
                    hash_register.put("number", "" + code + u_number);
                    string = code + u_number;

                    check("" + code + u_number);
                }


            }
        });


    }

    private void addval(String mVerificationId) {

        fb_db.child("Register").child(mVerificationId).setValue(hash_register).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Intent i = new Intent(getApplicationContext(), TabbedViewUser.class);
                    i.putExtra("number", string);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void check(final String mVerificationId) {
        fb_db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mVerificationId)) {
                    Toast.makeText(RegisterActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                } else {
                    registration(string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void registration(String phoneNumber) {

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
                        ll.setVisibility(View.VISIBLE);
                        mVerificationId = s;
                        mResendToken = forceResendingToken;

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(RegisterActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            addval(string);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


}
