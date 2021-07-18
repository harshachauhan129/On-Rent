package au.edu.jcu.flatOnRent.onrent.upload_data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import au.edu.jcu.flatOnRent.onrent.Seller.ImageUploadData;
import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.helper.ViewPagerAdapter;

public class UploadForms extends AppCompatActivity {

    private static final int GALLERY_PIC = 1;

    Toolbar toolbar2;
    TabLayout tab_layout;
    ViewPager view_pager;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_forms);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar1);
        tab_layout = (TabLayout) findViewById(R.id.tablayout_upload);
        view_pager = (ViewPager) findViewById(R.id.viewpager_upload);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new BooksUpload(), "Books");
        viewPagerAdapter.addFragments(new PgSellProfile(), "Pg/Flats");
        viewPagerAdapter.addFragments(new ElectronicsUpload(), "Electronics");
        view_pager.setAdapter(viewPagerAdapter);
        tab_layout.setupWithViewPager(view_pager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Uri image1 = data.getData();
            BooksUpload.image(image1);
            ElectronicsUpload.imageViewww(image1);
            PgSellProfile.imagePG(image1);

        } else {
            Toast.makeText(this, "oo. no", Toast.LENGTH_SHORT).show();
        }

    }

    public void imageUploadB(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select an image"), GALLERY_PIC);
    }

    public void imageUploadE(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select an image"), GALLERY_PIC);
    }

    public void imageUploadP(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select an image"), GALLERY_PIC);
    }

}
