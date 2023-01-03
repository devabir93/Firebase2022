package ucas.edu.android.firebase2022.ui.coins;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ucas.edu.android.firebase2022.R;
import ucas.edu.android.firebase2022.ui.Model.Coins;
import ucas.edu.android.firebase2022.ui.Model.Product;


public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.MyViewHolder> {

    List<Coins> products;
    Context context;

    public CoinsAdapter(Context context, List<Coins> products) {
        this.context=context;
        this.products = products;
    }

    public CoinsAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coins, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        Coins user = products.get(position);
        holder.name.setText(user.getName());
        holder.price.setText("Sheikl:" +user.getSheikl());
    }


    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void setProducts(List<Coins> products) {
        this.products = products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        TextView name, email,price;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            price = (TextView) itemView.findViewById(R.id.price);
            imageView = (ImageView) itemView.findViewById(R.id.imageview1);
        }
    }
}
