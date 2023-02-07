package com.vedha.whatsweb.fragments;

import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.vedha.whatsweb.adapter.StorieSaverrrVideoAdapter;
import com.vedha.whatsweb.models.VideosModel;

import java.io.File;
import java.util.ArrayList;

public class StorieSaverTabVideos extends Fragment {
    ErrorView mErrorView;
    RecyclerView mTVideoRvVideosList;
    SwipeRefreshLayout mTVideoSrlVideoView;
    StorieSaverrrVideoAdapter mVideoAdapter;
    public ArrayList<VideosModel> mVideoList;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.tab_video, viewGroup, false);


        this.mTVideoRvVideosList = (RecyclerView) inflate.findViewById(R.id.tVideo_rvVideosList);
        this.mTVideoSrlVideoView = (SwipeRefreshLayout) inflate.findViewById(R.id.tVideo_srlVideoView);
        this.mErrorView = (ErrorView) inflate.findViewById(R.id.error_view);
        this.mVideoList = new ArrayList<>();
        copyAllStatusIntoFile();
        this.mVideoAdapter = new StorieSaverrrVideoAdapter(getActivity(), this.mVideoList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        this.mTVideoRvVideosList.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.photos_list_spacing));
        this.mTVideoRvVideosList.setLayoutManager(gridLayoutManager);
        this.mTVideoRvVideosList.setNestedScrollingEnabled(true);
        ((SimpleItemAnimator) this.mTVideoRvVideosList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mTVideoRvVideosList.setAdapter(this.mVideoAdapter);
        this.mVideoAdapter.notifyDataSetChanged();
        doStatusCheck();
        this.mTVideoSrlVideoView.setColorSchemeColors(SupportMenu.CATEGORY_MASK, -16711936, -16776961, -16711681);
        this.mTVideoSrlVideoView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                        if (StorieSaverTabVideos.this.mTVideoRvVideosList != null && StorieSaverTabVideos.this.mVideoAdapter != null) {
                            StorieSaverTabVideos.this.mTVideoRvVideosList.getRecycledViewPool().clear();
                            StorieSaverTabVideos.this.mVideoList.clear();
                            StorieSaverTabVideos.this.mVideoAdapter.notifyDataSetChanged();
                            StorieSaverTabVideos.this.copyAllStatusIntoFile();
                        }
            }
        });
        return inflate;
    }

    private void doStatusCheck() {
        if (this.mVideoList.size() == 0) {
            doCheckFile();
        } else {
            this.mErrorView.setVisibility(View.GONE);
            this.mTVideoSrlVideoView.setRefreshing(false);
        }
        StorieSaverrrVideoAdapter storieSaverrrVideoAdapter = this.mVideoAdapter;
        if (storieSaverrrVideoAdapter != null) {
            storieSaverrrVideoAdapter.notifyDataSetChanged();
        }
    }

    public void copyAllStatusIntoFile() {
        this.mTVideoSrlVideoView.setRefreshing(false);
        if (SdCardHelper.isSdCardPresent()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                this.mErrorView.setVisibility(View.GONE);
                if (listFiles == null || listFiles.length == 0) {
                    doCheckFile();
                    return;
                }
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].getName().contains(".mp4") || listFiles[i].getName().contains(".flv") || listFiles[i].getName().contains(".mkv")) {
                        VideosModel videosModel = new VideosModel();
                        videosModel.setVideoName(listFiles[i].getName());
                        videosModel.setVideoPath(listFiles[i].getAbsolutePath());
                        videosModel.setBitmap(ThumbnailUtils.createVideoThumbnail(listFiles[i].getAbsolutePath(), 3));
                        this.mVideoList.add(videosModel);
                    }
                    if (this.mVideoList.size() == 0) {
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

    private void doCheckFile() {
        this.mTVideoSrlVideoView.setRefreshing(false);
        this.mErrorView.setVisibility(View.VISIBLE);
        this.mErrorView.setImageVisibility(8);
        this.mErrorView.setTitle(getString(R.string.no_videos_found));
        this.mErrorView.setSubtitle("");
        this.mErrorView.showRetryButton(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
