package com.artisoft.watermarkdesktop.utils;

import java.io.File;

public final class GlobalDataHandler {
    private static File waterMarkFile;
    private static boolean pngFeature = false;
    private static boolean TextFeature = false;


    private GlobalDataHandler(){}

    public static File getFile(){
        return waterMarkFile;
    }

    public static void setFile(File waterMarkFile){
        GlobalDataHandler.waterMarkFile = (waterMarkFile != null) ? waterMarkFile : null;
    }
}
