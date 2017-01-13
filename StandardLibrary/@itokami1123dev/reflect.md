# java.lang.reflect LT
## Javaツール勉強会＠福岡 2017/01

____
## 自己紹介

<img class="logo" src="img/twitter_itokami_logo.png" style="border-radius:2em;height:2em;"/>
<br />
- twitter: @itoKami1123

- 福岡の中堅企業向け統合基幹業務システムをSaaSで提供している会社で働いてます。
子供たちが大きくなった時に福岡で仕事が困らない様に
福岡ITを盛り上げたいと思っています。

____
## java.lang.reflect

- クラスとオブジェクトに関するリフレクト情報を取得するための、クラスとインタフェースを提供します。


_- - -_

### 資料

- パッケージ java.lang.reflect
  http://docs.oracle.com/javase/jp/8/docs/api/java/lang/reflect/package-summary.html

____
### 文字列からクラスを取得してインスタンスを生成

```java
public class Hoge {
    public static String TEISU = "定数だよ";
    public String call(){
        return String.format("%s をよんだよ",this.getClass().getName());
    }
}
```

_- - -_

```java
try {
    Class clazz = Class.forName("com.example.reflt.Hoge");
    // newInstance()でコンストラクタを呼んでいる
    Hoge hoge = (Hoge)clazz.newInstance();
    System.out.printf("[%s]#call [%s]", clazz.getName(),hoge.call());
} catch (ClassNotFoundException e) {
    e.printStackTrace();
} catch (InstantiationException e) {
    e.printStackTrace();
} catch (IllegalAccessException e) {
    e.printStackTrace();
}
```

```
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge をよんだよ]
```

_- - -_

### 文字列からメソッドを取得して実行

```java
public class Hoge {
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
```

_- - -_

```java
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

```

```
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge をよんだよ] 
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge public method] 
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge method] 
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge private mothod] 
[com.example.reflt.Hoge]#call [com.example.reflt.Hoge protected mothod] 
```

_- - -_

### フィールド情報取得

```java
// 親クラス
public class Robo {
    String defaultName = "robo**defaultName**";
    private String privateName = "robo**privateName**";
    protected String protectedName = "robo**protectedName**";
    public String publicName = "robo**publicName**";
}
```

```java
// 調査対象クラス
public class Doramon extends Robo {
    String defaultName = "**defaultName**";
    private String privateName = "**privateName**";
    protected String protectedName = "**protectedName**";
    public String publicName = "**publicName**";
}
```

_- - -_

```java
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
```

```
[publicName] = [**publicName**] 
[publicName] = [robo**publicName**] 
```

- getFieldsでpublicが取得される。
- 親クラスの取得する

____
## デモ

mvn archetype:generate -DarchetypeGroupId=pl.org.miki -DarchetypeArtifactId=java8-quickstart-archetype -DinteractiveMode=false -DgroupId=com.example.reflt -DartifactId=reflt

[demo](index.html)

____

## まとめ

- xxx

- yyy

____
## おしまい
