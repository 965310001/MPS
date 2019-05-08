package com.mingpinmall.classz.ui.service;

interface IBackService {
       boolean sendMessage(String message);

       String getUrl();

       void setUrl(String url);

       void setMemberInfo(String uId,String uName,String avatar,String sId,String sName,String sAvatar);

       String getUid();
       String getUname();
       String getAvatar();
       String getSid();
       String getSname();
       String getsAvatar();
}
