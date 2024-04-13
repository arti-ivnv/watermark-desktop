package com.artisoft.watermarkdesktop.utils;

import java.io.File;

public final class GlobalDataHandler {
    private static File waterMarkFile;
    private static boolean pngFeature = false;
    private static boolean TextFeature = false;

    private static String[] patterns = {"Zig-zag", "Tree"};

    private static TextWatermark textWatermark;



    private GlobalDataHandler(){}

    public static File getFile(){
        return waterMarkFile;
    }

    public static void setFile(File waterMarkFile){
        if(getTextWatermark() != null)
            setTextWatermark(null);
        GlobalDataHandler.waterMarkFile = (waterMarkFile != null) ? waterMarkFile : null;
    }

    public static TextWatermark getTextWatermark(){
        return textWatermark;
    }

    public static void setTextWatermark(TextWatermark textWatermark){
        if(getFile() != null)
            setFile(null);
        GlobalDataHandler.textWatermark = (textWatermark != null) ? textWatermark : null;
    }

    public static String[] getPatterns(){
        return patterns;
    }

}
