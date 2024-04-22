package org.dao.tovarDao;

import org.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductsDAO {
    Map<String, List<String>> allNamesBillsOfLadingByDatta(String datatime);

    List<Product> getBillsByName(String name);
    void addToNamesBillsOfLading(String bill, String datatime);
    List<Product> getAllBillsByData(String data);
    void dropTableBillOfLading(String bill);
    void createTableBillOfLading(String bill);
    int addProductToBillOfLading(List<Product> products);
    void createTable();

    void deleteTable();

    void createTovar(Product dataSet);

    void deleteTovar(long id);

    void updateTovar(Product dataSet);

    Product getById(long id);

    Product getByName(String name);

    List<Product> getAll();
    List<Product> getSorted();
}
