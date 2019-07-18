package com.example.mullanguage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import com.example.mullanguage.utils.LanguageUtil;
import com.example.mullanguage.utils.PrefUtils;

import java.util.Locale;

/**
 * Created by sunnyDay on 2019/7/18 18:57
 */
public class MainActivity extends Activity {

    private NavigationView nav;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LanguageUtil.changeAppLanguage(this, PrefUtils.getLanguage(this)); // onCreate 之前调用 否则不起作用
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initLeftMenu();
    }

    private void initLeftMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);

        findViewById(R.id.tv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);

            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_english:
                        setLanguage("en");
                        break;
                    case R.id.menu_hi:
                        setLanguage("hi");
                        break;
                    case R.id.menu_in:
                        setLanguage("in");
                        break;
                    case R.id.menu_es:
                        setLanguage("es");
                        break;
                    case R.id.menu_pt:
                        setLanguage("pt");
                        break;
                    case R.id.menu_ru:
                        setLanguage("ru");
                        break;
                    case R.id.menu_de:
                        setLanguage("de");
                        break;
                    case R.id.menu_fr:
                        setLanguage("fr");
                        break;
                    case R.id.menu_zh_rCN:
                        setLanguage("zh_rCN");
                        break;
                    case R.id.menu_zh_rTW:
                        setLanguage("zh_rTW");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置语言
     */
    private void setLanguage(String language) {
        // 切换
        LanguageUtil.changeAppLanguage(MainActivity.this, language);
        // 存入sp
        PrefUtils.setLanguage(MainActivity.this, language);
        // 重启app
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
