package ucas.edu.android.firebase2022.ui.coins;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ucas.edu.android.firebase2022.R;
import ucas.edu.android.firebase2022.databinding.FragmentHomeBinding;
import ucas.edu.android.firebase2022.ui.Model.Coins;
import ucas.edu.android.firebase2022.ui.Model.Dinar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoinsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoinsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentHomeBinding binding;

    public CoinsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoinsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoinsFragment newInstance(String param1, String param2) {
        CoinsFragment fragment = new CoinsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    FirebaseDatabase db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance();
        getRealTimeCoins();
    }

    private void getRealTimeCoins() {
        db.getReference("coins")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.progressBar.setVisibility(View.GONE);
                        Log.d("getRealTimeCoins", snapshot.toString());
                        Log.d("getRealTimeCoins2", snapshot.getValue().toString());
                        List<Coins> coinList = new ArrayList<>();
                        for (DataSnapshot sn : snapshot.getChildren()) {
                            Coins coins = sn.getValue(Coins.class);
                            coinList.add(coins);
                            Log.d("getRealTimeCoins coins", coins.toString());
                        }

                        CoinsAdapter coinsAdapter = new CoinsAdapter(getActivity(), coinList);
                        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        binding.rv.setAdapter(coinsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        error.toException().printStackTrace();
                    }
                });
    }

}