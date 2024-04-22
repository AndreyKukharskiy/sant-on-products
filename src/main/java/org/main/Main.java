package org.main;

import org.util.ExelDriverReader;
import org.util.SortedProduct;

import java.util.Properties;

import java.io.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Hello world!");
        SortedProduct sortedProduct = new SortedProduct();
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            //загружается произвольный паттерн
            //String specificSelect = property.getProperty("free.pattern");

            String readPath = property.getProperty("read.main.xlsx.files.drivers");
            ExelDriverReader reader = new ExelDriverReader();
            reader.readFromExcel(readPath);

//            String readPath = property.getProperty("read.main.xlsx.files");
//            ExelReader reader = new ExelReader();
//            //reader.readFromExcel(readPath);
//
//            //get all bill by datatime
//            String dataTime = property.getProperty("bills.data.time");
//            List<Product> mergedAllBills =  sortedProduct.getMergedAllBillsByData(dataTime);
//
//            List<Product> sortedMergedALLBills = sortedProduct.sort(mergedAllBills);
//            List<Product> copyList = new ArrayList<>();
//            copyList.addAll(sortedMergedALLBills);

//            List<Product> allSink = sortedProduct.getAllSink(sortedMergedALLBills);
//            List<Product> allKomods = sortedProduct.getAllKomods(mergedAllBills);
//            List<Product> allSmallSizedProduct = sortedProduct.getAllSmallSized(mergedAllBills);
//            List<Product> allBowls = sortedProduct.getAllBowl(mergedAllBills);
//
//            List<Product> allShowerCabin = sortedProduct.getALLShowerCabin(mergedAllBills);
//            List<Product> allKitchenSink = sortedProduct.getAllKitchenSink(mergedAllBills);
//            List<Product> allMirrors = sortedProduct.getAllMirrors(mergedAllBills);
//            List<Product> allTowelDryer = sortedProduct.getAllTowelDryer(mergedAllBills);
//            List<Product> allBath = sortedProduct.getAllBath(mergedAllBills);


            //идет запись в файл
//            String writePath = property.getProperty("write.main.xlsx.files");
            //ExelWriter writer = new ExelWriter();

//            writer.write(String.format(writePath,"allSink"), allSink);
//            writer.write(String.format(writePath,"allKomods"),allKomods);
//            writer.write(String.format(writePath,"allSmall"), allSmallSizedProduct);
//            writer.write(String.format(writePath,"allBowls"), allBowls);
//            writer.write(String.format(writePath,"allShowerCabin"), allShowerCabin);
//            writer.write(String.format(writePath,"allKitchenSink"), allKitchenSink);
//            writer.write(String.format(writePath,"allMirrors"), allMirrors);
//            writer.write(String.format(writePath,"allBath"), allBath);
//            writer.write(String.format(writePath,"allRest"),mergedAllBills);
//            writer.write(String.format(writePath,"firstList"),copyList);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
}