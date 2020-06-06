package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber,mAddress;
    Button mUpdateAdrressbtn,mUploadProfilePic;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    FirebaseFirestore fStore;
    ImageView mProfilePictore;

    private static final String USERS = "EDMT_FIREBASE";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();

       // final String userID1 = bundle.getString("emailadd");

        final String userID1 = MainActivity.emailID;

        pd = new ProgressDialog(this);

        final Intent myIntent = new Intent(Profile.this,MainActivity.class);
        final Intent Address_intent = new Intent(Profile.this,Update_Adress.class);

        myIntent.putExtra("emailadd",userID1);
        mUpdateAdrressbtn = findViewById(R.id.update_address);
        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);
        mAddress = findViewById(R.id.Address1);
        mUploadProfilePic = findViewById(R.id.ProfilePictureBTN);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);
        mProfilePictore = findViewById(R.id.ProfileImage);
        //Init Database
        fStore = FirebaseFirestore.getInstance();

        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        show(userID1);


/*

        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc: task.getResult()){
                            String dbuser = doc.getString("Email").toString().trim();
                            if(dbuser.equals(userID1)){
                                mName.setText(doc.getString("FullName"));
                                mEmail.setText(doc.getString("Email"));
                                mPhonenumber.setText(doc.getString("PhoneNumber"));
                                mAddress.setText(doc.getString("Address"));
                                Address_intent.putExtra("Address",doc.getString("Email"));
                                pd.dismiss();
                                break;
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Profile.this,"שגיאה בטעינת נתונים!",Toast.LENGTH_SHORT).show();
                    }
                }) ;
*/
        mUpdateAdrressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Address_intent);
            }
        });
        mUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallaryIntent,1000);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(myIntent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.games:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(requestCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                mProfilePictore.setImageURI(imageUri);
            }
        }
    }

    public void show(final String email) {


        UserRef = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE");
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1:dataSnapshot.getChildren())
                    if(email.equals( data1.child("email").getValue().toString())){
                        mName.setText(data1.child("FullName").getValue().toString());
                        mEmail.setText(data1.child("email").getValue().toString());
                        mPhonenumber.setText(data1.child("PhoneNumber").getValue().toString());
                        mAddress.setText(data1.child("address").getValue().toString());
                        pd.dismiss();
                        break;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
