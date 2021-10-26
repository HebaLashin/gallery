package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.Adapter.ImageFileAdapter;
import com.example.Model.ImageFileModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class ImagesFile extends AppCompatActivity {
   private String foldername;
    private RecyclerView recyclerView_IF ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference,databaseReference2;
    private DatabaseReference getimage_folder;
    private ImageFileAdapter imageFileAdapter ;
    private ImageFileModel imageFileModel;
    private ArrayList<ImageFileModel> imagefilelist ;
    private Button  add , choose ;
    private Uri imgUri   ;
    private StorageReference storageReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_file);

        add = findViewById(R.id.addbutton);
        choose = findViewById(R.id.choosebutton);
        storageReference = FirebaseStorage.getInstance().getReference();


         Intent intent = getIntent();
         foldername = intent.getStringExtra("name");
      //   makeText(ImagesFile.this,"Heba  " + foldername, Toast.LENGTH_LONG).show();

        imagefilelist =  new ArrayList<>();
        clearall();
        recyclerView_IF = findViewById(R.id.recyclerView_IF);
      //  RecyclerView.LayoutManager layoutmanager= new GridLayoutManager(this,2);
      //  recyclerView_IF.setLayoutManager(layoutmanager);
       // recyclerView_IF.addItemDecoration(new DividerItemDecoration(recyclerView_IF.getContext(),DividerItemDecoration.VERTICAL));


        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i  = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,2);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUri != null)
                {
                    uploadtofirebase(imgUri);
                }
                else
                {
                    makeText(ImagesFile.this,"please select image ", Toast.LENGTH_LONG).show();
                }
            }
        });



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        getimage_folder =databaseReference.child("images/"+foldername);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("/images/" +foldername+"/");




        SharedPreferences sr = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sr.edit();
        editor.putString("foldername",foldername);
        editor.commit();

        getimage_folder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            //    makeText(ImagesFile.this,"good", Toast.LENGTH_LONG).show();
                for (DataSnapshot i  : snapshot.getChildren() )
                {
                     imageFileModel = new ImageFileModel();

                    imageFileModel.setImageurl(i.child("imageurl").getValue().toString());
                   // imageFileModel.setName(i.child("name").getValue().toString());

                    imagefilelist.add(imageFileModel);

                }
                imageFileAdapter = new ImageFileAdapter(getApplicationContext(),imagefilelist);
                recyclerView_IF.setAdapter(imageFileAdapter);
                imageFileAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                makeText(ImagesFile.this,"error loading " , Toast.LENGTH_LONG).show();

            }
        });


    }

    private void clearall(){
        if(imagefilelist!= null)
        {
            imagefilelist.clear();
            if (imageFileAdapter!=null)
            {
                imageFileAdapter.notifyDataSetChanged();
            }
        }
        else
            imagefilelist = new ArrayList<>();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 2 && resultCode==RESULT_OK && data!= null){
            imgUri = data.getData();
           // imageView.setImageURI(imgUri);
        }
        else
        {
            makeText(ImagesFile.this,"Error selecting", Toast.LENGTH_LONG).show();

        }
    }

    private  void uploadtofirebase( Uri uri) {

        // StorageReference storageReference2  = storageReference.
        //        child(System.currentTimeMillis()+ "."+ getFileExtention(uri));

        StorageReference storageReference2  = storageReference.child("images/"+ foldername+"/"+System.currentTimeMillis()+ "."+ getFileExtention(uri));
        storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String id = databaseReference2.push().getKey();

                        ImageFileModel imageFileModel = new ImageFileModel(uri.toString(),System.currentTimeMillis()+ "."+
                                getFileExtention(uri),id);
                        databaseReference2.child(id).setValue(imageFileModel);

                       /* SharedPreferences sr = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = sr.edit();
                        editor.putString("imageId",id);
                        editor.commit();*/

                        //makeText(ImagesFile.this,id, Toast.LENGTH_LONG).show();
                        makeText(ImagesFile.this,"Uploading Successfully", Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeText(ImagesFile.this,"Uploading Failed", Toast.LENGTH_LONG).show();

            }
        });

    }

    private String  getFileExtention(Uri imgUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgUri));

    }



}