package com.example.jasenko.cwsboco_product_tool.Signature;

import android.graphics.Path;

/**
 * Created by Jasenko on 30/12/2017.
 */

public class FingerPath {

    public int color;
    public int strokeWidth;
    public Path path;

    public FingerPath(int color, boolean emboss, boolean blur, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}