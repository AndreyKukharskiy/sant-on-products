package org.util.parsers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.model.Product;
import org.service.productService.ProductsService;
import org.service.productService.ProductsServiceImp;
import org.util.SortedProduct;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ExelReader {
    //    static final Logger LOGGER = Logger.getLogger(ExelParser.class);
//
//    static final Logger loggetTovarServiceImp = Logger.getLogger(TovarServiceImp.class);
    private ProductsService productsService = new ProductsServiceImp();
    public ExelReader() throws SQLException {
    }
    private final String[] titles = {"наименование", "артикул", "к-во", "цена", "№ документа", "дата", "позиция"};

    public void readFromExcel(String file) throws IOException, SQLException {
        String billOfLandings = "";
        String dataTime = "";
        List<Product> products = new ArrayList<>();
        SortedProduct sortedProduct = new SortedProduct();
        Product novTovar = null;
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("TDSheet");
        XSSFRow row = myExcelSheet.getRow(0);
        Iterator<Row> itRow = myExcelSheet.iterator();

        while (itRow.hasNext()) {
            Row rowOriginal = itRow.next();
            if (rowOriginal.getCell(6) != null) {
                if (rowOriginal.getCell(6).getStringCellValue().contains("Н А К Л А Д Н А Я")) {
                    dataTime = String.valueOf(rowOriginal.getCell(9));
                    billOfLandings = String.valueOf(rowOriginal.getCell(7));
                    productsService.addToNamesBillsOfLading("bills_"+billOfLandings, dataTime);
                    productsService.dropTableBillOfLading(billOfLandings);
                    productsService.createTableBillOfLading(billOfLandings);
                    while (itRow.hasNext()) {
                        if (rowOriginal.getCell(1) != null) {
                            if (rowOriginal.getCell(1).getStringCellValue().contains("Отпустил")) {
                                if (products.get(products.size() - 1).getChislo() == products.size()) {
                                    productsService.addProductToBillOfLading(products);
                                    products = new ArrayList<>();
                                }
                                break;
                            }
                            if (rowOriginal.getCell(1).getStringCellValue().contains("1а")) {
                                rowOriginal = itRow.next();
                                while (itRow.hasNext()) {
                                    if (rowOriginal.getCell(1) == null) {
                                        break;
                                    }
                                    novTovar = new Product(
                                            rowOriginal.getCell(2).getStringCellValue(),
                                            rowOriginal.getCell(8).getStringCellValue(),
                                            rowOriginal.getCell(17).getNumericCellValue(),
                                            rowOriginal.getCell(24).getNumericCellValue(),
                                            Integer.parseInt(rowOriginal.getCell(1).getStringCellValue()),
                                            billOfLandings,
                                            dataTime
                                    );
                                    products.add(novTovar);
                                    Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
                                    rowOriginal = itRow.next();
                                }
                            }
                        }
                        rowOriginal = itRow.next();
                    }
                    rowOriginal = itRow.next();
                }
                int dfd = 0;
            }
        }
        myExcelBook.close();
    }


}