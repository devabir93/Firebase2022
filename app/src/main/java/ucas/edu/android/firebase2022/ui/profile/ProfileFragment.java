package ucas.edu.android.firebase2022.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import ucas.edu.android.firebase2022.databinding.FragmentSlideshowBinding;

public class ProfileFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String name = binding.editTextTextName.getText().toString();
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName("Abir Abdullah")
                .setPhotoUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Android_logo_2019.png/800px-Android_logo_2019.png"))
                .build();

        binding.updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(userProfileChangeRequest);
            }
        });

        binding.updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail();
            }
        });

        binding.updatePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePersonalInfo();
            }
        });
        return root;
    }

    private void updatePersonalInfo() {

    }

    private void updateProfile(UserProfileChangeRequest userProfileChangeRequest) {
        currentUser.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.reload();
                            Log.d("currentUser name", currentUser.getDisplayName());
                            Log.d("currentUser photo", currentUser.getPhotoUrl().toString());
                            Toast.makeText(getActivity(), currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void updateEmail() {
        //You should Allow user to enter his email and password manually.
        //don't use a static values.
        AuthCredential authCredential = EmailAuthProvider.getCredential("abirabullah@gmail.com", "123456");
        currentUser.reauthenticate(authCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateEmailAfterAuthnicated();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateEmailAfterAuthnicated() {
        currentUser.updateEmail("abir1@gmail.com")
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.reload();
                            Log.d("currentUser email", currentUser.getEmail());
                            Toast.makeText(getActivity(), currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}