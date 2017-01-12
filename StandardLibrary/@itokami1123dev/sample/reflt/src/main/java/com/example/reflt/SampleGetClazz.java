package com.example.reflt;

public class SampleGetClazz {
    public void lunch() {

        try {
            Class clazz = Class.forName("com.example.reflt.Hoge");
            System.out.printf("Class.forName ... %s ", clazz.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
