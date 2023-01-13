package ucas.edu.android.firebase2022.ui.gallery;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import ucas.edu.android.firebase2022.MainActivity;
import ucas.edu.android.firebase2022.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    private static final int GALLERY_INTENT = 1000;

    private FragmentGalleryBinding binding;
    private Uri mainImageURI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        getFiles();
        return root;
    }

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), GALLERY_INTENT);
    }

    private void getFiles() {
        ((MainActivity) getActivity()).showLoader();
        ((MainActivity) getActivity()).firebaseStorage.getReference().child("images/" + ((MainActivity) getActivity()).currentUser.getUid())
                .listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        ((MainActivity) getActivity()).dismissDialog();
                        setAdapter(listResult.getItems());
                    }
                })

        ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mainImageURI = data.getData();
            uploadImageToFirebase();
/*            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mainImageURI);
                binding.iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void uploadImageToFirebase() {
        ((MainActivity) getActivity()).showLoader();
        StorageReference reference = ((MainActivity) getActivity()).firebaseStorage.getReference()
                .child("images/" + ((MainActivity) getActivity()).currentUser.getUid()+"/"+mainImageURI.getLastPathSegment());
        UploadTask uploadTask = reference
                .putFile(mainImageURI);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        ((MainActivity) getActivity()).progressDialog.setMessage("loading " + progress + "%");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ((MainActivity) getActivity()).dismissDialog();
                    }
                });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                getFiles();
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d(" downloadUri",downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    private void setAdapter(List<StorageReference> items) {
        StorageAdapter storageAdapter = new StorageAdapter(getContext(), items);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rv.setAdapter(storageAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}