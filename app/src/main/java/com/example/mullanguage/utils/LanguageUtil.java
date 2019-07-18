package com.example.mullanguage.utils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;


/**
 * Created by sunnyDay on 2019/7/17 17:48
 * 修改app内部的语言工具类
 */
public class LanguageUtil {

    /*语言类型：
    * 此处支持10种语言类型，更多可以自行添加。
    * */
    private static final String ENGLISH = "en";
    private static final String GERMAN = "de";
    private static final String SPANISH = "es";
    private static final String FRANCE = "fr";
    private static final String HINDI = "hi";
    private static final String INDONESTIAN = "in";
    private static final String PORTUGUESS = "pt";
    private static final String RUSSIAN = "ru";
    private static final String SIMPLIFIED_CHINESE = "zh_rCN";
    private static final String TRADITIONAL_CHINESE = "zh_rTW";

    private static HashMap<String, Locale> mAllLanguages = new HashMap<String, Locale>(8) {{
        put(ENGLISH, Locale.ENGLISH);
        put(GERMAN, Locale.GERMAN);
        put(SPANISH, LanguageLocal.Spanish);
        put(FRANCE, Locale.FRANCE);
        put(HINDI, LanguageLocal.Hindi);
        put(INDONESTIAN, LanguageLocal.Indonestian);
        put(PORTUGUESS, LanguageLocal.Portuguess);
        put(RUSSIAN, LanguageLocal.Russian);
        put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
    }};


    /**
     *
     * 修改语言
     * @param context 上下文
     * @param newLanguage 要修改为哪种语言：例如修改为 英文传“en”，参考上文字符串常量
     * */
    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    public static void changeAppLanguage(Context context, String newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        // app locale
        Locale locale = getLocaleByLanguage(newLanguage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }


        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
        PrefUtils.setLanguage(context, newLanguage);
    }


    private static boolean isSupportLanguage(String language) {
        return mAllLanguages.containsKey(language);
    }


    public static String getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return language;
        }

        if (null == language) {//为空则表示首次安装或未选择过语言，获取系统默认语言
            Locale locale = Locale.getDefault();
            for (String key : mAllLanguages.keySet()) {
                if (TextUtils.equals(mAllLanguages.get(key).getLanguage(), locale.getLanguage())) {
                    return locale.getLanguage();
                }
            }
        }
        return ENGLISH;
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在{@link #mAllLanguages}，返回本机语言，如果本机语言不是语言集合中的一种{@link #mAllLanguages}，返回英语
     *
     * @param language language
     * @return
     */
    private static Locale getLocaleByLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mAllLanguages.get(language);
        } else {
            Locale locale = Locale.getDefault();
            for (String key : mAllLanguages.keySet()) {
                if (TextUtils.equals(mAllLanguages.get(key).getLanguage(), locale.getLanguage())) {
                    return locale;
                }
            }
        }
        return Locale.ENGLISH;
    }


    /**
     * Application 的attachBaseContext方法中注册下
     * */
    public static Context attachBaseContext(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        } else {
            return context;
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = getLocaleByLanguage(language);

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

}