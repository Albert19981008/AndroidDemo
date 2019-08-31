package com.example.readhub;


/**
 * MVP架构模式 View接口
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
}