package com.deepifydroid30.app.walleo;

public class ImageList {
    private int resImage;
    private String mTitle;
    private String pkgName;
    public ImageList(int res){
        resImage = res;
    }

    public ImageList(int res, String title, String p){
        pkgName = p;
        resImage = res;
        mTitle = title;
    }
    public int getResImage(){
        return resImage;
    }
    public String getText(){
        return mTitle;
    }

    public String getPackage() {
        return pkgName;
    }
}
