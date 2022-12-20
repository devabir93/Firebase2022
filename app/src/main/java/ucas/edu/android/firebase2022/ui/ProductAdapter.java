package ucas.edu.android.firebase2022.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ucas.edu.android.firebase2022.R;
import ucas.edu.android.firebase2022.ui.Model.Product;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    List<Product> products;
    Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context=context;
        this.products = products;
    }

    public ProductAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        Product user = products.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getCategory());
        holder.price.setText(user.getPrice()+"");
    }


    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email,price;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
