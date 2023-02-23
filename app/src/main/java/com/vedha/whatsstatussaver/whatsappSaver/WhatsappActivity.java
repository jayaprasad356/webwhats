package com.vedha.whatsstatussaver.whatsappSaver;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.vedha.whatsstatussaver.whatsappSaver.util_items.Utils.createFileFolder;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.vedha.whatsstatussaver.R;
import com.vedha.whatsstatussaver.databinding.ActivityWhatsappBinding;
import com.vedha.whatsstatussaver.databinding.DialogWhatsappPermissionBinding;
import com.vedha.whatsstatussaver.whatsappSaver.fragments.WhatsappImageFragment;
import com.vedha.whatsstatussaver.whatsappSaver.fragments.WhatsappQImageFragment;
import com.vedha.whatsstatussaver.whatsappSaver.fragments.WhatsappQVideoFragment;
import com.vedha.whatsstatussaver.whatsappSaver.fragments.WhatsappVideoFragment;
import com.vedha.whatsstatussaver.whatsappSaver.util_items.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WhatsappActivity extends AppCompatActivity {
    private ActivityWhatsappBinding binding;
    private WhatsappActivity activity;
//    private InterstitialAd interstitialAd;

    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    ProgressDialog progressDialog;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhatsappBinding.inflate(getLayoutInflater());
        activity = this;
        createFileFolder();
        setContentView(binding.getRoot());
//        loadAd();

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        // choose colour on icon bars by this line
//        navigationView.setItemIconTintList(null);
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.mGallery:
                        callGalleryActivity();
                        break;
                    case R.id.mWa:
                        if (Build.VERSION.SDK_INT >= 29) {
                            Utils.OpenApp(activity,"com.whatsapp");
                            //allowacess();
                        } else {
                            Utils.OpenApp(activity,"com.whatsapp");
                        }
                        break;



                    default:
                        return true;
                }
                return true;
            }
        });
        


        binding.imBack.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= 23) {
                checkPermissions(105);
            } else {
                callGalleryActivity();
            }
        });


        binding.LLOpenWhatsapp.setOnClickListener(v -> {
            Utils.OpenApp(activity,"com.whatsapp");
        });

        fileArrayList = new ArrayList<>();
        initProgress();

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q){
            if (getContentResolver().getPersistedUriPermissions().size() > 0) {
                progressDialog.show();
                new LoadAllFiles().execute();
                binding.tvAllowAccess.setVisibility(View.GONE);
            }else{
                binding.tvAllowAccess.setVisibility(View.VISIBLE);
            }
        }else {
            setupViewPager(binding.viewpager);
            binding.tabs.setupWithViewPager(binding.viewpager);

            for (int i = 0; i < binding.tabs.getTabCount(); i++) {
                TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
                binding.tabs.getTabAt(i).setCustomView(tv);
            }
        }

        binding.tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowacess();
            }

        });
    }

    private void allowacess() {
        Dialog dialog = new Dialog(activity, R.style.SheetDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogWhatsappPermissionBinding dialogWhatsappPermissionBinding =
                DialogWhatsappPermissionBinding.inflate(LayoutInflater.from(this),null,false);
        dialog.setContentView(dialogWhatsappPermissionBinding.getRoot());
        dialogWhatsappPermissionBinding.tvAllow.setOnClickListener(v -> {
            try {
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
                    StorageManager sm = (StorageManager) activity.getSystemService(Context.STORAGE_SERVICE);
                    Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String startDir = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
                    Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                    String scheme = uri.toString();
                    scheme = scheme.replace("/root/", "/document/");
                    scheme += "%3A" + startDir;
                    uri = Uri.parse(scheme);
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                    startActivityForResult(intent, 2001);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            dialog.dismiss();
        });

        dialogWhatsappPermissionBinding.tvAllowBusiness.setOnClickListener(v -> {
            try {
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
                    StorageManager sm = (StorageManager) activity.getSystemService(Context.STORAGE_SERVICE);
                    Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String startDir = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses";
                    Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                    String scheme = uri.toString();
                    scheme = scheme.replace("/root/", "/document/");
                    scheme += "%3A" + startDir;
                    uri = Uri.parse(scheme);
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                    startActivityForResult(intent, 2002);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private boolean checkPermissions(int type) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) (activity),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), type);
            return false;
        } else {
            if (type == 102) {
                callWhatsappActivity();
            } else if (type == 105) {
                callGalleryActivity();
            }

        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callWhatsappActivity();
            }
            return;
        } else if (requestCode == 105) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callGalleryActivity();
            }
            return;
        }


    }



    public void callWhatsappActivity() {
        Intent i = new Intent(activity, WhatsappActivity.class);
        startActivity(i);
    }

    public void callGalleryActivity() {
        Intent i = new Intent(activity, GalleryActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }



    public void initProgress(){
        progressDialog = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Status. Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new WhatsappImageFragment(), getResources().getString(R.string.photos));
        adapter.addFragment(new WhatsappVideoFragment(), getResources().getString(R.string.videos));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2001 || requestCode == 2002 && resultCode == RESULT_OK) {
                Uri dataUri = data.getData();
                if (dataUri.toString().contains(".Statuses")) {
                    getContentResolver().takePersistableUriPermission(dataUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    progressDialog.show();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        new LoadAllFiles().execute();
                    }
                }else{
                    Utils.infoDialog(activity,activity.getResources().getString(R.string.wrong_folder), activity.getResources().getString(R.string.selected_wrong_folder));
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    class LoadAllFiles extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... furl) {
            DocumentFile documentFile = DocumentFile.fromTreeUri(activity, activity.getContentResolver().getPersistedUriPermissions().get(0).getUri());
            for (DocumentFile file : documentFile.listFiles()) {
                if (file.isDirectory()) {

                } else {
                    if (!file.getName().equals(".nomedia")) {
                        fileArrayList.add(file.getUri());
                    }
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... progress) {

        }
        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            adapter.addFragment(new WhatsappQImageFragment(fileArrayList), getResources().getString(R.string.photos));
            adapter.addFragment(new WhatsappQVideoFragment(fileArrayList), getResources().getString(R.string.videos));
            binding.viewpager.setAdapter(adapter);
            binding.viewpager.setOffscreenPageLimit(1);
            binding.tabs.setupWithViewPager(binding.viewpager);
            binding.tvAllowAccess.setVisibility(View.GONE);
            for (int i = 0; i < binding.tabs.getTabCount(); i++) {
                TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
                binding.tabs.getTabAt(i).setCustomView(tv);
            }
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
//        else if (interstitialAd != null) {
//            interstitialAd.show(this);
//            interstitialAd.setFullScreenContentCallback(
//                    new FullScreenContentCallback() {
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            WhatsappActivity.this.interstitialAd = null;
//                            onBackPressed();
//
//                        }
//
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(AdError adError) {
//                            WhatsappActivity.this.interstitialAd = null;
//                        }
//
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//                        }
//                    });
//        }
        else {
            super.onBackPressed();
        }

    }

//    public void loadAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(
//                this,
//                getString(R.string.InterstitialAd),
//                adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        WhatsappActivity.this.interstitialAd = interstitialAd;
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        interstitialAd = null;
//                    }
//                });
//    }
}
