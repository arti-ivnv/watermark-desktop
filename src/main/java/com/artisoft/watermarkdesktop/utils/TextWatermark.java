package com.artisoft.watermarkdesktop.utils;

import java.awt.*;

public class TextWatermark {
    private String text;
    private Integer fontSize;
    private Color color;
    private String pattern;
    public TextWatermark(String text, Integer fontSize, Color color, String pattern){
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
        this.pattern = pattern;

    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public String getPattern() {
        return pattern;
    }

    public Integer getFontSize(){
        return fontSize;
    }


}
