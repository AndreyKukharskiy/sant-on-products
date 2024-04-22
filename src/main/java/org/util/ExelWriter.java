package org.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ExelWriter {
    private SortedProduct sortedTovar = new SortedProduct();
    private List<Product> tovars;

    public ExelWriter() throws SQLException {
    }

    //метод работает прослокой между main и xlsx файлом
    public void write(String file, List<Product> products) throws IOException {
        //tovars = sortedTovar.getMergedAllBillsByData("18.04.2024"); //.getSortedData();
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Sclad");
        writeIntoExcel(sheet, products);
        book.write(new FileOutputStream(file));
        book.close();
    }

    public void write(String file, String pattern) throws IOException {
        //write(file);
        //List<Tovar> tovars = sortedTovar.getSortedData();
        //List<Tovar> tovars = sortedTovar.getAllSink();
        //tovars = sortedTovar.getAllTovarBySpecicicPattern(pattern);
    }

    //производит непосредственную запись в файл
    @SuppressWarnings("deprecation")
    private void writeIntoExcel(Sheet sheet, List<Product> products) throws FileNotFoundException, IOException {
        int countTitle = 0;
        writeCellTitles(sheet.createRow(0));
        for (int i = 0; i < products.size(); i++) {
            if (countTitle % 20 == 0) {
                sheet.createRow(countTitle++);
                sheet.createRow(countTitle++);
                writeCellTitles(sheet.createRow(countTitle++));
            }
            writeCell(sheet.createRow(countTitle), products.get(i));
            countTitle++;
        }
        sheet.autoSizeColumn(1);
    }

    //запись отдельных полей в файл из java объекта
    private void writeCell(Row row, Product tovar) {
        row.createCell(0).setCellValue(tovar.getBillOflading());
        row.createCell(1).setCellValue(tovar.getChislo());
        row.createCell(2).setCellValue(tovar.getName());
        row.createCell(3).setCellValue(tovar.getArticul());
        row.createCell(4).setCellValue(tovar.getKolichestvo());
        row.createCell(5).setCellValue(tovar.getCena());
        //row.createCell(6).setCellValue(tovar.getDataTime());
    }

    //заголовки
    private void writeCellTitles(Row row) {
        row.createCell(0).setCellValue("№ док.");
        row.createCell(1).setCellValue("позиция");
        row.createCell(2).setCellValue("наименование");
        row.createCell(3).setCellValue("артикул");
        row.createCell(4).setCellValue("к-во");
        row.createCell(5).setCellValue("цена");
        //row.createCell(6).setCellValue("дата");
    }
}
