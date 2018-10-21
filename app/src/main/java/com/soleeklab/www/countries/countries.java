package com.soleeklab.www.countries;

/**
 * Created by mohamed on 10/19/2018.
 */

public class countries {
    private String name;
    private String ImageUri;
    private String region;

    countries(String nName,String mImageUri, String mRegion){

        this.name=nName;
        this.ImageUri=mImageUri;
        this.region=mRegion;
    }

    public String getName() {
        return name;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public String getRegion() {
        return region;
    }
}
