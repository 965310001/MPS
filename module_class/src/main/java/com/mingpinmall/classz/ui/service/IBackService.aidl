package com.mingpinmall.classz.ui.service;

//aidl接口，用于线程通讯
interface IBackService{
	boolean sendMessage(String message);
}