package com.vedha.whatsweb.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vedha.whatsweb.ErrorView;
import com.vedha.whatsweb.ItemOffsetDecoration;
import com.vedha.whatsweb.R;
import com.vedha.whatsweb.SdCardHelper;
import com.vedha.whatsweb.adapter.StorieSaverImageeeAdapter;
import com.vedha.whatsweb.models.ImagesModel;

import java.io.File;
import java.util.ArrayList;

public class StorieSaverTabImages extends Fragment {
    ErrorView mErrorView;
    StorieSaverImageeeAdapter mImageAdapter;
    public ArrayList<ImagesModel> mImageList;
    RecyclerView mTImageRvImagesList;
    SwipeRefreshLayout mTImageSrlImageView;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.tab_image, viewGroup, false);
        this.mTImageRvImagesList = (RecyclerView) inflate.findViewById(R.id.tImage_rvImagesList);
        this.mTImageSrlImageView = (SwipeRefreshLayout) inflate.findViewById(R.id.tImage_srlImageView);
        this.mErrorView = (ErrorView) inflate.findViewById(R.id.error_view);
        check();
        this.mImageList = new ArrayList<>();
        copyAllStatusIntoFile();
        this.mImageAdapter = new StorieSaverImageeeAdapter(getContext(), this.mImageList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        this.mTImageRvImagesList.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.photos_list_spacing));
        this.mTImageRvImagesList.setLayoutManager(gridLayoutManager);
        this.mTImageRvImagesList.setNestedScrollingEnabled(true);
        ((SimpleItemAnimator) this.mTImageRvImagesList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mTImageRvImagesList.setAdapter(this.mImageAdapter);
        this.mImageAdapter.notifyDataSetChanged();
        doStatusCheck();

        this.mTImageSrlImageView.setColorSchemeColors(SupportMenu.CATEGORY_MASK, -16711936, -16776961, -16711681);
        this.mTImageSrlImageView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                if (StorieSaverTabImages.this.mTImageRvImagesList != null && StorieSaverTabImages.this.mImageAdapter != null) {
                    StorieSaverTabImages.this.mTImageRvImagesList.getRecycledViewPool().clear();
                    StorieSaverTabImages.this.mImageList.clear();
                    StorieSaverTabImages.this.mImageAdapter.notifyDataSetChanged();
                    StorieSaverTabImages.this.copyAllStatusIntoFile();
                    StorieSaverTabImages.this.doStatusCheck();
                }
            }
        });
        return inflate;
    }


    public void doStatusCheck() {
        if (this.mImageList.size() == 0) {
            doCheckFile();
            this.mErrorView.showRetryButton(false);
        } else {
            this.mTImageSrlImageView.setRefreshing(false);
        }
        StorieSaverImageeeAdapter storieSaverImageeeAdapter = this.mImageAdapter;
        if (storieSaverImageeeAdapter != null) {
            storieSaverImageeeAdapter.notifyDataSetChanged();
        }
    }

    private void doCheckFile() {
        this.mTImageSrlImageView.setRefreshing(false);
        this.mErrorView.setVisibility(View.VISIBLE);
        this.mErrorView.setImageVisibility(8);
        this.mErrorView.setTitle(getString(R.string.no_image_found));
        this.mErrorView.setSubtitle("");
    }

    public void copyAllStatusIntoFile() {
        this.mTImageSrlImageView.setRefreshing(false);
        if (SdCardHelper.isSdCardPresent()) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    doCheckFile();
                    return;
                }
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].getName().contains(".jpg") || listFiles[i].getName().contains(".jpeg") || listFiles[i].getName().contains(".png")) {
                        ImagesModel imagesModel = new ImagesModel();
                        imagesModel.setImageName(listFiles[i].getName());
                        imagesModel.setImagePath(listFiles[i].getAbsolutePath());
                        this.mImageList.add(imagesModel);
                    }
                    if (this.mImageList.size() == 0) {
                        doCheckFile();
                    } else {
                        this.mErrorView.setVisibility(View.GONE);
                    }
                }
                return;
            }
            doCheckFile();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void check() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
            Toast.makeText(getContext(), "Permission needed to save images and videos", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 0) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        } else if (iArr.length == 1 && iArr[0] == 0) {
            Toast.makeText(getContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Storage permission required\nto save images & videos", Toast.LENGTH_SHORT).show();
            requestStoragePermission();
        }
    }
}
