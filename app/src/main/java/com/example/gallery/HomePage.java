package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.Adapter.HomepageAdapter;
import com.example.Model.ModelName;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class HomePage extends AppCompatActivity {

    private StorageReference listRef;
    private DatabaseReference dRef;

   private RecyclerView recyclerView ;
   private ArrayList<ModelName> listitems ;
   private HomepageAdapter homepageAdapter ;
    private Context context ;
   private ModelName modelName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       recyclerView = findViewById(R.id.recyclerview1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        dRef = FirebaseDatabase.getInstance().getReference();
        listitems= new ArrayList<>();
        clearall();


        listRef = FirebaseStorage.getInstance().getReference("/images/");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        Log.d("1", listRef.getName());
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            Log.d("1", prefix.getName());
                            //makeText(HomePage.this, prefix.getName(),LENGTH_LONG).show();

                            modelName = new ModelName();
                            modelName.setName(prefix.getName());
                            listitems.add(modelName);

                            // makeText(HomePage.this,"listitems",LENGTH_LONG).show();

                            // listitems.add(prefix.getName().toString());
                            //adapter.notifyDataSetChanged();


                            //  dataList.add(prefix.getName().toString());
                        }
                        for (StorageReference item : listResult.getItems()) {
                        }
                        homepageAdapter=new HomepageAdapter(getApplicationContext(),listitems);
                        recyclerView.setAdapter(homepageAdapter);
                        homepageAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });


    }
    private void clearall(){
        if(listitems!= null)
        {
            listitems.clear();
            if (homepageAdapter!=null)
            {
                homepageAdapter.notifyDataSetChanged();
            }
        }
        else
            listitems = new ArrayList<>();

    }
}