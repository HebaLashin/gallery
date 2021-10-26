package com.example.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Model.ImageFileModel;
import com.example.gallery.R;
import com.example.gallery.ViewImage;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class ImageFileAdapter extends RecyclerView.Adapter<ImageFileAdapter.ViewHolder> {
    private ArrayList<ImageFileModel> modelFile_ArrayList = new ArrayList<>();
    private Context mContext;

    public ImageFileAdapter(Context context, ArrayList<ImageFileModel> names) {
        mContext = context;
        modelFile_ArrayList = names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_imagefile_row,parent,false);
        return new ImageFileAdapter.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      //  holder.textView.setText(modelFile_ArrayList.get(position).getName());
        Glide.with(mContext).
                load(modelFile_ArrayList.get(position).getImageurl())
                .into(holder.imageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext.getApplicationContext(), ViewImage.class);
                i.putExtra("image_url",modelFile_ArrayList.get(position).getImageurl());
                i.putExtra("image_id" ,modelFile_ArrayList.get(position).getId());
           String x =      modelFile_ArrayList.get(position).getName();
                holder.itemView.getContext().startActivity(i);
               // makeText(mContext, modelFile_ArrayList.get(position).getName(),LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFile_ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView ;
        ImageView imageView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         //   textView = itemView.findViewById(R.id.tv_filerow2);
            imageView = itemView.findViewById(R.id.iv_imagefilerow);
            parentLayout = itemView.findViewById(R.id.relativelayout2);

        }
    }


}
