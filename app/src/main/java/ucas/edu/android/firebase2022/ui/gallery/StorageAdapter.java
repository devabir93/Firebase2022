package ucas.edu.android.firebase2022.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import ucas.edu.android.firebase2022.R;
import ucas.edu.android.firebase2022.ui.Model.Product;


public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    List<StorageReference> lists;
    Context context;

    public StorageAdapter(Context context, List<StorageReference> lists) {
        this.context=context;
        this.lists = lists;
    }

    public StorageAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        StorageReference storageReference = lists.get(position);
        Glide.with(context)
                .load(storageReference)
                .into(holder.imageview);
    }


    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    public void setProducts(List<StorageReference> products) {
        this.lists = products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}
