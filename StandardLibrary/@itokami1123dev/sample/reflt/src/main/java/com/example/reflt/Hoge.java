package com.example.reflt;

public class Hoge {
    public static String TEISU = "定数だよ";

    public String call() {
        return String.format("%s をよんだよ", this.getClass().getName());
    }

    public String call1() {
        return String.format("%s public method", this.getClass().getName());
    }

    String call2() {
        return String.format("%s method", this.getClass().getName());
    }

    private String call3() {
        return String.format("%s private mothod", this.getClass().getName());
    }
    
    protected String call4() {
        return String.format("%s protected mothod", this.getClass().getName());
    }
}
