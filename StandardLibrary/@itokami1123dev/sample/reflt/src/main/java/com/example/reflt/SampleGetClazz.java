package com.example.reflt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SampleGetClazz {
    public void lunch() {

        try {
            Class clazz = Class.forName("com.example.reflt.Hoge");
            Hoge hoge = (Hoge) clazz.newInstance();
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), hoge.call());

            Method method1 = clazz.getMethod("call1");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method1.invoke(hoge));
            Method method2 = clazz.getDeclaredMethod("call2"); // -> NoSuchMethodException
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method2.invoke(hoge));
            Method method3 = clazz.getDeclaredMethod("call3"); // -> NoSuchMethodException
            method3.setAccessible(true);
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method3.invoke(hoge));
        } catch (ClassNotFoundException e) { // Class.forName
            e.printStackTrace();
        } catch (InstantiationException e) { // clazz.newInstance
            e.printStackTrace();
        } catch (IllegalAccessException e) { // clazz.newInstance
            e.printStackTrace();
        } catch (NoSuchMethodException e) { // clazz.getMethod
            e.printStackTrace();
        } catch (InvocationTargetException e) { // method.invoke
            e.printStackTrace();
        }

    }
}
