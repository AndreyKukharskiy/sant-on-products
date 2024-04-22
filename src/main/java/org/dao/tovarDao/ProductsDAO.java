package org.dao.tovarDao;

import org.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductsDAO {
    Map<String, List<String>> allNamesBillsOfLadingByDatta(String datatime);
    List<Product> getBillsByName(String name);
    void addToNamesBillsOfLading(String bill, String datatime);
    void dropTableBillOfLading(String bill);
    void createTableBillOfLading(String bill);
    int addProductToBillOfLading(List<Product> products);
    void createProduct(Product dataSet);
}
