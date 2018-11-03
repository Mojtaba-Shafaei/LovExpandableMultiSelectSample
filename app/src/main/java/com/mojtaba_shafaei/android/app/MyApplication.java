package com.mojtaba_shafaei.android.app;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication{

@Override
public void onCreate(){
  super.onCreate();
  MultiDex.install(this);
}
}
