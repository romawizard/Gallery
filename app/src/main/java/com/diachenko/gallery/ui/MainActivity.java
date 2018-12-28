package com.diachenko.gallery.ui;


import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.diachenko.gallery.R;
import com.diachenko.gallery.data.ExternalUsersPhoto;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.PhotoRepositoryImpl;
import com.diachenko.gallery.ui.adapters.PhotoAdapter;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int PORTRAIT_SPAN_COUNT = 3;
    public static final int LANDSCAPE_SPAN_COUNT = 5;
    private static final int REQUEST_PERMISSION_CODE = 101;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoGrid)
    RecyclerView photoGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
       getPermission();
    }

    private void showPhotoGrid(PhotoViewModel photoViewModel, int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        final PhotoAdapter adapter = new PhotoAdapter();
        photoGrid.setLayoutManager(layoutManager);
        photoGrid.setAdapter(adapter);
        photoViewModel.getPhoto(this).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                adapter.setPhotos(photos);
            }
        });

    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this
                    , Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , REQUEST_PERMISSION_CODE);
            }
        }else {
            showPhoto();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPhoto();
                } else {
                    Toast.makeText(this, "Unfortunately you don't allow to save image"
                            , Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    private void showPhoto() {
        // get and init ViewModel
        PhotoViewModel photoViewModel = ViewModelProviders.of(this)
                .get(PhotoViewModel.class);
        PhotoRepository photoRepository = new PhotoRepositoryImpl(new ExternalUsersPhoto());
        photoViewModel.init(photoRepository);

        // Check Orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showPhotoGrid(photoViewModel, LANDSCAPE_SPAN_COUNT);
        } else {
            showPhotoGrid(photoViewModel, PORTRAIT_SPAN_COUNT);
        }
    }
}
