package com.example.abhinav_rapidbox.childdaycare.utill;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;

public class HashCodeFileNameWithDummyExtGenerator implements FileNameGenerator {
    @Override
    public String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode()) + ".png";
    }
}
