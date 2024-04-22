package org.service;


import org.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductsService extends AutoCloseable {

    void dropTableBillOfLading(String bill);
    void createTableBillOfLading(String bill);
    List<Product> getBillsByName(String name);
    int addProductToBillOfLading(List<Product> products);
    void addToNamesBillsOfLading(String bill, String datatime);
    Map<String, List<String>> getAllNamesBillsOfLadingByData(String datatime);
    void close() throws Exception;
}
