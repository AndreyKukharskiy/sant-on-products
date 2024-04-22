package org.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.model.DriverProduct;
import org.model.Product;
import org.service.ProductsService;
import org.service.ProductsServiceImp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ExelDriverReader {
    private ProductsService productsService = new ProductsServiceImp();
    public ExelDriverReader() throws SQLException {
    }
    private final String[] titles = {"наименование", "артикул", "к-во", "цена", "№ документа", "дата", "позиция"};
    public void readFromExcel(String file) throws IOException, SQLException {
    List<DriverProduct> drivers = new ArrayList<>();
        String billOfLandings = "";
        String dataTime = "";
        List<Product> products = new ArrayList<>();
        SortedProduct sortedProduct = new SortedProduct();
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("TDSheet");
        XSSFRow row = myExcelSheet.getRow(0);
        Iterator<Row> itRow = myExcelSheet.iterator();
        String route = "";
        while (itRow.hasNext()) {
            Row rowOriginal = itRow.next();
            if (rowOriginal.getCell(0) != null) {
                if (rowOriginal.getCell(0).getStringCellValue().contains("Маршрутный лист")) {
                    route = rowOriginal.getCell(5).getStringCellValue();
                    rowOriginal = itRow.next();
                    System.out.println(route);
                }
                if (rowOriginal.getCell(1) != null) {
                    if (rowOriginal.getCell(1) != null) {
                        if (!rowOriginal.getCell(1).getStringCellValue().contains("Наименование") &&
                                !rowOriginal.getCell(1).getStringCellValue().isEmpty()
                                && !rowOriginal.getCell(9).getStringCellValue().isEmpty()) {
                            while (itRow.hasNext()) {
                                if (rowOriginal.getCell(0) == null || rowOriginal.getCell(9).getStringCellValue().isEmpty()) {
                                    break;
                                }
                                String position = rowOriginal.getCell(0).getStringCellValue();
                                String name = rowOriginal.getCell(1).getStringCellValue();
                                String article = rowOriginal.getCell(9).getStringCellValue();
                                String barcode = rowOriginal.getCell(14).getStringCellValue();
                                double count = rowOriginal.getCell(16).getNumericCellValue();
                                String notFound = rowOriginal.getCell(22).getStringCellValue();
                                String order = rowOriginal.getCell(27).getStringCellValue();

                                Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
                                DriverProduct driverProduct = null;
                                driverProduct = new DriverProduct(position, name, article, barcode, count, notFound, order);

                                rowOriginal = itRow.next();
                            }
                        }
                    }
                }
            }
        }
        myExcelBook.close();
    }
}