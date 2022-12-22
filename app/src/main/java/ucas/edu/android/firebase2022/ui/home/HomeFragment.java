package ucas.edu.android.firebase2022.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import ucas.edu.android.firebase2022.databinding.FragmentHomeBinding;
import ucas.edu.android.firebase2022.ui.Model.Product;
import ucas.edu.android.firebase2022.ui.ProductAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        addProduct();
        getProducts();
        getInfoByUserUUId();
        return root;
    }

    private void getInfoByUserUUId() {
        DocumentReference reference = firebaseFirestore.collection("users")
                .document(currentUser.getUid());
        reference.collection("info")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot :
                                queryDocumentSnapshots.getDocuments()) {
                            Log.d(HomeFragment.class.getSimpleName(), documentSnapshot.getData().toString());
                        }
                    }
                });
    }

    private void addProduct() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("product" + (i + 1));
            product.setPrice(100 + 5);
            product.setCategory("Food");
            firebaseFirestore.collection("products")
                    .add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(HomeFragment.class.getSimpleName(), documentReference.getId().toString());
                        }
                    });
        }
    }

    private void getProducts() {
        firebaseFirestore.collection("products")
//                .whereGreaterThan("price",30)
                .orderBy("price", Query.Direction.DESCENDING)
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            List<Product> productList = task.getResult().toObjects(Product.class);
                            ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList);
                            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            binding.rv.setAdapter(productAdapter);
//                            Log.d(HomeFragment.class.getSimpleName(),productList.toString());
                        } else {
                            Log.d(HomeFragment.class.getSimpleName(), task.getException().getMessage());
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