package ucas.edu.android.firebase2022;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by abeer on 22,December,2022
 */
class BaseActivity extends AppCompatActivity {

    public FirebaseAuth auth;
    public FirebaseStorage firebaseStorage;
    public FirebaseFirestore firebaseFirestore;
    public ProgressDialog progressDialog;
    public FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void showLoader() {
        progressDialog = new ProgressDialog(this);
//        progressDialog.setView(R.layout.loader_layout);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
