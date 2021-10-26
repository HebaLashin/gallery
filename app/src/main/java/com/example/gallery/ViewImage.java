package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Constants;
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

import java.security.PrivateKey;
import java.security.cert.Extension;

import static android.widget.Toast.makeText;

public class ViewImage extends AppCompatActivity {
    private Button add , delete ;
    private ImageView imageView;
    private  String imageurl , imageid;
    private  String foldername ;
    private Uri imgUri   ;
    private String id , key;
    private  Context context;


    private StorageReference storageReference,storageReference2;
    private  FirebaseStorage firebaseStorage;

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    private DatabaseReference getchild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        //add = findViewById(R.id.add);
       // delete = findViewById(R.id.delete);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        imageurl = intent.getStringExtra("image_url");
        imageid  = intent.getStringExtra("image_id");

        //  SharedPreferences sr = PreferenceManager.getDefaultSharedPreferences(this);
      //  foldername = sr.getString("foldername"," empty");
       // key = sr.getString("imageId"," empty2");

      //  databaseReference = FirebaseDatabase.getInstance().getReference("/images/" +foldername+"/");
        //storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        getchild =databaseReference.child("images/"+foldername);

        Glide.with(getApplicationContext()).
                load(imageurl).into(imageView);


/*
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i  = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,2);

                 if (imgUri != null)
                {
                 uploadtofirebase(imgUri);
                }
                 else
                {
                    makeText(ViewImage.this,"please select image ", Toast.LENGTH_LONG).show();

                }



                makeText(ViewImage.this,foldername, Toast.LENGTH_LONG).show();

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseStorage = FirebaseStorage.getInstance();
                storageReference = firebaseStorage.getReferenceFromUrl(imageurl);

               getchild.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                     // String x  = snapshot.getChildren().iterator().next().getKey();
                         String x = snapshot.child("id").getValue().toString();

                       makeText(ViewImage.this,x, Toast.LENGTH_LONG).show();
                      // databaseReference.child(x).removeValue();

                       //   String x = snapshot.child("id").getValue().toString();

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
          //  String x =    databaseReference.push().getKey();
                       // databaseReference.child().setValue(null);
          //       databaseReference.child(x).setValue(null);
//
               // DatabaseReference xx = FirebaseDatabase.getInstance().getReference(foldername).child(imageid);

               // xx.removeValue();

               // storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                //    @Override
                  //  public void onSuccess(Void aVoid) {
                    //    makeText(ViewImage.this,"Image Deleted Successfully2", Toast.LENGTH_LONG).show();

                    //}
                //});

            }
        });
    */

}

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 2 && resultCode==RESULT_OK && data!= null){
            imgUri = data.getData();
            imageView.setImageURI(imgUri);
        }
        else
        {
            makeText(ViewImage.this,"Error selecting", Toast.LENGTH_LONG).show();

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
                        ImageFileModel imageFileModel = new ImageFileModel(uri.toString(),System.currentTimeMillis()+ "."+
                                getFileExtention(uri));
                       String id = databaseReference.push().getKey();
                        databaseReference.child(id).setValue(imageFileModel);

                       // SharedPreferences sr = PreferenceManager.getDefaultSharedPreferences(context);
                     //   SharedPreferences.Editor editor = sr.edit();
                    //    editor.putString("imageId",id);
                      //  editor.commit();

                        makeText(ViewImage.this,id, Toast.LENGTH_LONG).show();
                        makeText(ViewImage.this,"Uploading Successfully", Toast.LENGTH_LONG).show();

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
                makeText(ViewImage.this,"Uploading Failed", Toast.LENGTH_LONG).show();

            }
        });

    }

   private String  getFileExtention(Uri imgUri) {
       ContentResolver contentResolver = getContentResolver();
       MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
       return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgUri));

   }

*/

}