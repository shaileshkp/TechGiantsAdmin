package com.techgiants.admin.techgiantsadmin.model;

/**
 * Created by Shailesh on 10/1/2017.
 */

public class Posts {
//    private String postId;
//    private String userId;
//    private String timestamp;
    private String desc;
    private String imgUrl;

    public Posts() {
    }

    public Posts(String desc, String imgUrl) {
        this.desc = desc;
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
