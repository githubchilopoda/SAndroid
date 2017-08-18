package com.ss.ssframework.utils;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;

import android.support.annotation.NonNull;

import android.support.v4.util.SimpleArrayMap;


import com.ss.ssframework.SApplication;

import java.util.Collections;

import java.util.Map;

import java.util.Set;

/**
 * Created by 健 on 2017/8/4.
 */

public class SSpStorageMgr {
    private static final String PATH = "SSp_store";
    private SharedPreferences mPreferences;

    private static class SingletonClassInstance {
        private static final SSpStorageMgr self = new SSpStorageMgr();
    }

    public static SSpStorageMgr getInstance() {
        return SingletonClassInstance.self;
    }

    public SSpStorageMgr() {
        mPreferences =
                SApplication.getAppContext().getSharedPreferences(PATH, Activity.MODE_PRIVATE);
    }

    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */

    public void put(@NonNull final String key, @NonNull final String value) {
        mPreferences.edit().putString(key, value).apply();
    }


    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }


    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }


    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */

    public void put(@NonNull final String key, final int value) {
        mPreferences.edit().putInt(key, value).apply();
    }


    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */

    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }


    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public int getInt(@NonNull final String key, final int defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }


    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */

    public void put(@NonNull final String key, final long value) {
        mPreferences.edit().putLong(key, value).apply();
    }


    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */

    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }


    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public long getLong(@NonNull final String key, final long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }


    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */

    public void put(@NonNull final String key, final float value) {
        mPreferences.edit().putFloat(key, value).apply();
    }


    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */

    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }


    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }


    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */

    public void put(@NonNull final String key, final boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }


    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }


    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }


    /**
     * SP中写入String集合
     *
     * @param key    键
     * @param values 值
     */

    public void put(@NonNull final String key, @NonNull final Set<String> values) {
        mPreferences.edit().putStringSet(key, values).apply();
    }


    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code Collections.<String>emptySet()}
     */

    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }


    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */

    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValue) {
        return mPreferences.getStringSet(key, defaultValue);
    }


    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */

    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }


    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */

    public boolean contains(@NonNull final String key) {
        return mPreferences.contains(key);
    }


    /**
     * SP中移除该key
     *
     * @param key 键
     */

    public void remove(@NonNull final String key) {
        mPreferences.edit().remove(key).apply();
    }


    /**
     * SP中清除所有数据
     */

    public void clear() {
        mPreferences.edit().clear().apply();
    }
}
