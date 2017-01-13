package com.example.reflt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SampleGetClazz {
    public void test1() {
        try {
            Class clazz = Class.forName("com.example.reflt.Hoge");
            Hoge hoge = (Hoge) clazz.newInstance();
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), hoge.call());
            Method method1 = clazz.getMethod("call1");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method1.invoke(hoge));
            Method method2 = clazz.getDeclaredMethod("call2");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method2.invoke(hoge));
            Method method3 = clazz.getDeclaredMethod("call3");
            method3.setAccessible(true);
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method3.invoke(hoge));
            Method method4 = clazz.getDeclaredMethod("call4");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method4.invoke(hoge));
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

        try {
            Class clazz = Class.forName("com.example.reflt.Hoge");
            Hoge hoge = (Hoge) clazz.newInstance();
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), hoge.call());
            Method method1 = clazz.getMethod("call1");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method1.invoke(hoge));
            Method method2 = clazz.getDeclaredMethod("call2");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method2.invoke(hoge));
            Method method3 = clazz.getDeclaredMethod("call3");
            method3.setAccessible(true);
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method3.invoke(hoge));
            Method method4 = clazz.getDeclaredMethod("call4");
            System.out.printf("[%s]#call [%s] \n", clazz.getName(), method4.invoke(hoge));
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

    public void test2() {
        Doramon doramon = new Doramon();
        Class<Doramon> clazz = (Class<Doramon>) doramon.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            try {
                System.out.printf("[%s] = [%s] \n", field.getName(), field.get(doramon));
            } catch (IllegalAccessException e) { // field.get(doramon)
                e.printStackTrace();
            }
        }

//        class SuperClass { }
//        class SubClass extends SuperClass { }
//        SuperClass.class.isAssignableFrom(SubClass.class);   // true
//        SubClass.class.isAssignableFrom(SuperClass.class);   // false

        Field[] fieldsAll = clazz.getDeclaredFields();
        System.out.println("--自分のだけ--");
        for (Field field : fieldsAll) {
            try {
                int modifier = field.getModifiers();
                if (Modifier.isPublic(modifier)) {
                    System.out.printf("[%s] = [%s] \n", field.getName(), field.get(doramon));
                }
            } catch (IllegalAccessException e) { // field.get(doramon)
                e.printStackTrace();
            }
        }

        System.out.println("--自分の全部--");
        for (Field field : fieldsAll) {
            try {
                int modifier = field.getModifiers();
                field.setAccessible(true);
                System.out.printf("[%s] = [%s] \n", field.getName(), field.get(doramon));
            } catch (IllegalAccessException e) { // field.get(doramon)
                e.printStackTrace();
            }
        }


    }
}
