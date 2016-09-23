# Apache POI LT
### Javaツール勉強会＠福岡 LT ネタ

____
## 自己紹介

<img class="logo" src="img/twitter_itokami_logo.png" style="border-radius:2em;height:2em;"/>
<br />
- twitter: @itoKami1123

- 福岡の中堅企業向け統合基幹業務システムをSaaSで提供している会社で働いてます。
子供たちが大きくなった時に福岡で仕事が困らない様に
福岡ITを盛り上げたいと思っています。

____
## POI って

Apache POI - the Java API for Microsoft Documents
https://poi.apache.org/

- Apache License 2.0
- JavaでExcel操作できるよ
- HSSF (Horrible SpreadSheet Format) XLS形式 (Excel 97以降) 対応
- XSSF (XML SpreadSheet Format) OOXML形式 (Excel 2007) 対応
- Word、PowerPointも可能（試してない

____
## Maven で POI のお試し

_- - -_
### 空のMaven Projectを用意

```
$ mvn archetype:generate \
 -DarchetypeGroupId=pl.org.miki \
 -DarchetypeArtifactId=java8-quickstart-archetype \
 -DinteractiveMode=false \
 -DgroupId=com.example.poi.lt \
 -DartifactId=helloworld
```


_- - -_
### Mavenの設定(pom.xml)にPOI追加

```xml
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.14</version>
</dependency>
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi-ooxml</artifactId>
  <version>3.14</version>
</dependency>
```

_- - -_
### ついでにmavenで実行できるようにする

```xml
<!-- http://www.mojohaus.org/exec-maven-plugin/usage.html -->
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>1.5.0</version>
  <configuration>
    <executable>java</executable>
    <arguments>
      <argument>-classpath</argument>
      <classpath/>
      <argument>com.example.poi.lt.App</argument>
    </arguments>
  </configuration>
</plugin>
```

    mvn compile && mvn exec:exec


_- - -_
### まずは、 App.java で POIの動作確認

```java
public class App {
    public static void main(String... args) {
        try {
            System.out.println(
              "resources is " + ClassLoader.getSystemResource("."));
            InputStream is = ClassLoader
                .getSystemResourceAsStream("Book1.xlsx");
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getSheetName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
```

_- - -_
### 起動

```
mvn compile && mvn exec:exec
```

```

...省略...

[INFO] --- exec-maven-plugin:1.5.0:exec (default-cli) @ helloworld ---
resources is file:/home/xxx/JavaToolStudyFukuoka.github.io/OtherTools/@itokami1123dev/sample/helloworld/target/classes/
シート1

...省略...

```

____

## 読み込んでみる

_- - -_
### Excelファイルを開く

interface Workbook を実装しているのは３つ

- -> HSSFWorkbook  元祖Excel形式(xls)
- -> XSSFWorkbook  今のExcel形式(xlsx)
- -> SXSSFWorkbook 今のExcel形式(xlsx)を機能制限する代わりメモリ消費量が少ない

```java
InputStream is = ClassLoader
    .getSystemResourceAsStream("Book1.xlsx");
Workbook workbook = WorkbookFactory.create(is);
```

- WorkbookFactory.create は自動判定でどれかの型にします

_- - -_
### シートを取得

interface Workbook のシート取得関連メソッド
- getNumberOfSheets():int  => シートの総数を取得
- getSheetAt(int index):Sheet => シートの取得
- getSheet(java.lang.String name):Sheet => シートの取得

https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Workbook.html

_- - -_
### シート取得のサンプル

```java
InputStream is = ClassLoader.getSystemResourceAsStream("Book1.xlsx");
Workbook workbook = WorkbookFactory.create(is);
int numOfSheets = workbook.getNumberOfSheets();
for (int i = 0; i < numOfSheets; i++) {
    Sheet sheet = workbook.getSheetAt(i);
    System.out.printf("\n %d/%d => %s \n", i, numOfSheets, sheet.getSheetName());
}
Sheet sheet = workbook.getSheet("シート2");
System.out.printf("\n %s の名前のシートを取得 \n", sheet.getSheetName());
```

```
0/3 => シート1
1/3 => シート2
2/3 => シート3
シート2 の名前のシートを取得
```

_- - -_
### セルの値取得

セルの種類

interface Cell

- CELL_TYPE_NUMERIC = 0: 数値 ※ 日付も数値として判定
- CELL_TYPE_STRING  = 1: 文字列
- CELL_TYPE_FORMULA = 2: 関数
- CELL_TYPE_BLANK   = 3: からっぽ
- CELL_TYPE_BOOLEAN = 4: true or false
- CELL_TYPE_ERROR   = 5: エラー


_- - -_

```java
int numOfRows = sheet.getLastRowNum();
System.out.printf("\n %d 行のデータ \n", numOfRows);

Iterator<Row> rowIterator = sheet.iterator();
while (rowIterator.hasNext()) {
    Row row = rowIterator.next();

    Iterator<Cell> cellIterator = row.cellIterator();
    while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        String colNm = CellReference.convertNumToColString(cell.getColumnIndex());
        System.out.printf(
                "cell info columnName=%s type=%d\n",
                colNm, cell.getCellType());
    }
}

```

_- - -_

```
3 行のデータ
cell info columnName=A type=1
cell info columnName=B type=1
cell info columnName=C type=1
cell info columnName=D type=1
cell info columnName=E type=1
cell info columnName=A type=0
cell info columnName=B type=1
cell info columnName=C type=0
cell info columnName=D type=0
cell info columnName=E type=2
cell info columnName=A type=0
...省略...
```

_- - -_
### セルの値取得の注意

- 数値の入っている所を文字列取得すると異常終了する
- 数値と日付が同じタイプになってる

```java
boolean isDate = org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell);
```

_- - -_
### 日付と数値の値取得

```java
private String getVal(Cell cell) {
    switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC: // 数値, 日付も
            return getNumOrDate(cell);
// ... 省略 ...
    }
    return null;
}
private String getNumOrDate(Cell cell) {
    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
        Date dt = cell.getDateCellValue();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
        final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);
        return zdt.format(YYYY_MM_DD);
    }
    return String.valueOf(cell.getNumericCellValue());
}
```

_- - -_
### 文字列の取得

```java
private String getVal(Cell cell) {
    switch (cell.getCellType()) {
// ... 省略 ...
        case Cell.CELL_TYPE_STRING: // 文字列
            return cell.getStringCellValue();
// ... 省略 ...
        default:
            break;
    }

    return null;
}
```

_- - -_
### 関数実行結果の取得

```java
private String getVal(Cell cell) {
     switch (cell.getCellType()) {
         case Cell.CELL_TYPE_NUMERIC: // 数値, 日付も
             return getNumOrDate(cell);

         case Cell.CELL_TYPE_FORMULA: // 関数
             //return cell.getCellFormula();  =>　C2*D2  ( ;∀;)
             CreationHelper hlp = cell.getSheet().getWorkbook().getCreationHelper();
             FormulaEvaluator ev = hlp.createFormulaEvaluator();
             return getVal(ev.evaluateInCell(cell));

         case Cell.CELL_TYPE_BOOLEAN: // 真偽
             break;

         case Cell.CELL_TYPE_STRING: // 文字列
             return cell.getStringCellValue();

         case Cell.CELL_TYPE_BLANK: // 空
             break;
         default:
             break;
     }

     return null;
 }
```

http://www.ne.jp/asahi/hishidama/home/tech/apache/poi/cell.html#h_getFormulaValue
http://shin-kawara.seesaa.net/article/159878953.html

_- - -_
### 試しに実行

```java
int numOfRows = sheet.getLastRowNum();
System.out.printf("\n %d 行のデータ \n", numOfRows);
Iterator<Row> rowIterator = sheet.iterator();
while (rowIterator.hasNext()) {
    Row row = rowIterator.next();
    String date = getVal(row.getCell(0)); // A
    String name = getVal(row.getCell(1)); // B
    String unitPrice = getVal(row.getCell(2)); // C
    String num = getVal(row.getCell(3)); // D
    String price = getVal(row.getCell(4)); // E
    System.out.printf(" %s,  %s,  %s,  %s,  %s\n", date, name, unitPrice, num, price);
}
```

```
3 行のデータ
日付,  商品,  単価,  数量,  金額
2016/11/02,  あんぱん,  80.0,  3.0,  240.0
2016/11/03,  かれーぱん,  120.0,  8.0,  960.0
2016/11/04,  しょくぱん,  160.0,  1.0,  160.0
```

____

## 書き込んでみる

_- - -_
### Sheet新規作成

interface Workbook のシート取得関連メソッド
- cloneSheet(int sheetNum):Sheet  => コピーして作成
- createSheet():Sheet => 新規シート作成
- createSheet(java.lang.String sheetname):Sheet => 名前を指定して作成

```java
// ↓使うとシート名に使える安全な文字列に変換してくれる
WorkbookUtil.createSafeSheetName
```
http://www.ne.jp/asahi/hishidama/home/tech/apache/poi/sheet.html

_- - -_
### 行(ROW)新規作成

```Java
String safeName = WorkbookUtil.createSafeSheetName("hellosheet");
Sheet newSheet = workbook.createSheet(safeName);
int rowNo = 0; // エクセルの行番号
Row row = newSheet.createRow(rowNo);
```

_- - -_
### セル(Cell)新規作成

まずはスタイル

```java
CellStyle rightStyle = workbook.createCellStyle();
```

- Apache POIでスタイルを作りすぎると開けないExcelファイルが出来てしまう
  http://qiita.com/yakumo/items/5cc64aed4db5197bbeb6

xlsxの場合 64000個以上のスタイルを作成してはダメらしい
スタイルインスタンスを再利用するのが良い様子

_- - -_
そしてタイプと値を設定

```java
CellStyle rightStyle = workbook.createCellStyle();
rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);

Cell cellA = row.createCell(0);
cellA.setCellStyle(rightStyle);
cellA.setCellType(Cell.CELL_TYPE_STRING);
cellA.setCellValue("hoge");

Cell cellB = row.createCell(1);
cellB.setCellStyle(rightStyle);
cellB.setCellType(Cell.CELL_TYPE_NUMERIC);
cellB.setCellValue(123);

```

_- - -_
### 保存！

```
OutputStream out = new FileOutputStream("Book2.xlsx");
workbook.write(out);
```

____
## ここまでしかできてないです...

----
## おしまい
