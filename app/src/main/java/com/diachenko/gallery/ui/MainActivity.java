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

import com.diachenko.gallery.GalleryApplication;
import com.diachenko.gallery.R;
import com.diachenko.gallery.data.ExternalUsersPhoto;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.PhotoRepositoryImpl;
import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.ui.adapters.PhotoAdapter;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModel;
import com.diachenko.gallery.utils.MyLog;
import com.diachenko.gallery.utils.Resource;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName() ;
    public static final int PORTRAIT_SPAN_COUNT = 3;
    public static final int LANDSCAPE_SPAN_COUNT = 5;
    private static final int REQUEST_PERMISSION_CODE = 101;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoGrid)
    RecyclerView photoGrid;
    private PhotoViewModel photoViewModel;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
       getPermission();
    }

    private void preparePhotoGrid(int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        adapter = new PhotoAdapter();
        adapter.setListener(this);
        photoGrid.setLayoutManager(layoutManager);
        photoGrid.setAdapter(adapter);
        photoViewModel.getPhoto(this).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                adapter.setPhotos(photos);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPhotos();
                } else {
                    Toast.makeText(this, "Unfortunately you don't allow to save image"
                            , Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    private void showPhotos() {
        // get and init ViewModel
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        ImgurApi imgurApi = GalleryApplication.getInstance().getImgurApi();
        Executor executor = Executors.newSingleThreadExecutor();
        PhotoRepository photoRepository = new PhotoRepositoryImpl(new ExternalUsersPhoto(),imgurApi,executor);
        photoViewModel.init(photoRepository);

        // Check Orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            preparePhotoGrid(LANDSCAPE_SPAN_COUNT);
        } else {
            preparePhotoGrid(PORTRAIT_SPAN_COUNT);
        }
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
            showPhotos();
        }
    }

    @Override
    public void onClick(final Photo photo, final int position) {
        photoViewModel.uploadPhoto(photo).observe(this, new Observer<Resource<UploadPhotoResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<UploadPhotoResponse> uploadPhotoResponseResource) {
                if (uploadPhotoResponseResource == null){
                    return;
                }
               switch (uploadPhotoResponseResource.status){
                   case LOADING:
                       MyLog.log(TAG,uploadPhotoResponseResource.toString());
                       break;
                   case ERROR:
                       MyLog.log(TAG,uploadPhotoResponseResource.toString());
                       break;
                   case SUCCESS:
                       MyLog.log(TAG,uploadPhotoResponseResource.toString());
                       adapter.stopAnimate(position);
                       break;
               }
            }
        });
    }
}
