package com.artisoft.watermarkdesktop.utils;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BrowserUtils {

    public static final Map<String, String> links = new HashMap<>() {{
        put("github", "https://github.com/arti-ivnv");
        put("linkedIn", "https://www.linkedin.com/in/artem-ivanov-9815a2172/");
        put("telegram", "https://telegram.me/FoxyHikka/");
        put("icons8", "https://icons8.ru/");
    }};

    public static boolean openWebpage(URI uri){
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        try {
            desktop.browse(uri);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean openWebpage(URL url){
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return false;
    }
}
