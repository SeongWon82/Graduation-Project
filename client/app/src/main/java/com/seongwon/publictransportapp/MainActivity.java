package com.seongwon.publictransportapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity{

    private SearchView searchView;
    private ViewPager2 viewPager2;
    private ImageButton navigation;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private TabLayout tabs;
    private NavigationView nav_view;
    private static final int PERMISSION_REQUEST = 0x0000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            new Task("init").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            // 서비스 실행
            Intent intent = new Intent(getApplicationContext(),SystemService.class);
            startService(intent);

            for(int i=1;i<=3;i++) {
                Static.alramMap.put(i, new Alram(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            //네비게이션 버튼 객체 가져오기
            navigation = (ImageButton) findViewById(R.id.btn_navigation);
            //검색 뷰 객체 설정
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) findViewById(R.id.searchView);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //뷰 페이저 객체 가져오기
            viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
            // 드로우 레이아웃, 뷰 가져오기
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerView = (View) findViewById(R.id.nav_view);
            //탭바 가져오기
            tabs = (TabLayout) findViewById(R.id.tabs);
            // 네비게이션바 가져오기
            nav_view = (NavigationView) findViewById(R.id.nav_view);
        }catch(NullPointerException e){
            Log.d("NullPointerException","참조 실패함");
        }
        // 권한 부여가 되었는지 확인
        onCheckPermission();

        // 네비게이션 이벤트 리스너 추가
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        //restart();
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.nav_favorites:
                        Static.changeBusList();
                        restart();
                        break;
                    case R.id.nav_alram:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                drawerLayout.closeDrawer(drawerView);
                return false;
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fm,getLifecycle());
        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(adapter);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                updateItems(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    Static.clearBuses();
                    Static.clearTrains();
                    new Task("busRouteList",query).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                    new Task("SearchInfoBySubwayNameService",query).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                } catch (Exception e) {
                    Log.d("Exception",e.getMessage());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void restart()
    {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    private void updateItems(int number){
        switch(number)
        {
            case 0:
                BusFragment.update();
                break;
            case 1:
                TrainFragment.update();
                break;
            case 2:
                AlramFragment.update();
                break;
        }
    }
    private void  onCheckPermission()
    {
        boolean checkAccessFineLocation =
                ( ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED);
        boolean checkAccessCoarseLocation=
                ( ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED);

        // 위치 접근 권한 없음
        if(checkAccessFineLocation || checkAccessCoarseLocation)
        {
            //이전에 거부한 경우 필요한 이유 설명
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(this,"서비스 이용을 위해서는 권한이 필요합니다.",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_REQUEST);
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_REQUEST);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"앱 실행 권한이 설정됨",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"앱 실행 권한이 취소됨",Toast.LENGTH_LONG).show();
                break;
        }
    }
}