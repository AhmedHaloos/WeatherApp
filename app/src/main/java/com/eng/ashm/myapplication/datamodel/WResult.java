package com.eng.ashm.myapplication.datamodel;

public interface WResult<T> {

    void onResult(T t, int  code);
}
