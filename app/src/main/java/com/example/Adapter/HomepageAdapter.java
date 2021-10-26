package com.example.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.ImagesFile;
import com.example.Model.ModelName;
import com.example.gallery.R;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {
    private  static final String tag = "RecycleView";
    private ArrayList<ModelName> modelNameArrayList = new ArrayList<>();
    private Context mContext;

    public HomepageAdapter(Context context, ArrayList<ModelName> names) {
        mContext = context;
        modelNameArrayList = names;
    }

    @NonNull
    @Override
    public HomepageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view) {
        };

    }

    @Override
    public void onBindViewHolder(@NonNull HomepageAdapter.ViewHolder holder, int position) {
        holder.textView.setText(modelNameArrayList.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext.getApplicationContext(), ImagesFile.class);
                i.putExtra("name",modelNameArrayList.get(position).getName());
                holder.itemView.getContext().startActivity(i);
                makeText(mContext, modelNameArrayList.get(position).getName(),LENGTH_LONG).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelNameArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView ;
       RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_Recycleviewrow);
            parentLayout = itemView.findViewById(R.id.relativelayout);

        }
    }
}





/*
    private  static final String tag = "RecycleView";
    private ArrayList<ModelName> modelNameArrayList = new ArrayList<>();
    private Context mContext;




    public HomepageAdapter(Context context, ArrayList<ModelName> names) {
        mContext = context;
        modelNameArrayList = names;
    }

    @NonNull
    @Override
    public HomepageAdapter.HomepageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row,parent,false);
        return new HomepageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageAdapter.HomepageViewHolder holder, int position) {

        holder.textView.setText(modelNameArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HomepageViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;
        ImageView imageView ;

        public HomepageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_Recycleviewrow);
          //  imageView = itemView.findViewById(R.id.iv_Recycleviewrow);


        }
    }


}
*/
