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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.diachenko.gallery.GalleryApplication;
import com.diachenko.gallery.R;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.ui.adapters.PhotoAdapter;
import com.diachenko.gallery.ui.dialogs.ListUrlDialog;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModel;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoAdapterListener {

    public static final int PORTRAIT_SPAN_COUNT = 3;
    public static final int LANDSCAPE_SPAN_COUNT = 5;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION_CODE = 101;
    private static final String DIALOG_TAG = "ListUrlDialog";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoGrid)
    RecyclerView photoGrid;
    @BindView(R.id.loading_photos)
    ImageView loadingPhotos;
    private PhotoViewModel photoViewModel;
    private PhotoAdapter adapter;

    @Override
    public void onClick(final Photo photo, final int position) {
        photoViewModel.uploadPhoto(photo, position);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.links:
                ListUrlDialog dialog = new ListUrlDialog();
                dialog.show(getSupportFragmentManager(), DIALOG_TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
        photoViewModel.getPhotos(this).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                stopLoading();
                adapter.setPhotos(photos);

//                DiffUtilPhoto diffUtilPhoto = new DiffUtilPhoto(adapter.getPhotos(),photos);
//                DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffUtilPhoto);
//                adapter.setPhotos(photos);
//                result.dispatchUpdatesTo(adapter);
            }
        });

    }

    private void stopLoading() {
        loadingPhotos.clearAnimation();
        loadingPhotos.setVisibility(View.GONE);
    }

    private void showPhotos() {
        showLoading();
        // get and init ViewModel
        PhotoRepository repository = GalleryApplication.getInstance().getRepository();
        photoViewModel = ViewModelProviders.of(this, new PhotoViewModelFactory(repository))
                .get(PhotoViewModel.class);

        // Check Orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            preparePhotoGrid(LANDSCAPE_SPAN_COUNT);
        } else {
            preparePhotoGrid(PORTRAIT_SPAN_COUNT);
        }
    }

    private void showLoading() {
        loadingPhotos.setVisibility(View.VISIBLE);
        loadingPhotos.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this,
                R.anim.progress_animation);
        loadingPhotos.startAnimation(rotation);
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
        } else {
            showPhotos();
        }
    }
}
