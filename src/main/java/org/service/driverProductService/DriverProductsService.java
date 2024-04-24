package org.service.driverProductService;


import org.model.DriverProduct;
import org.model.Product;

import java.util.List;
import java.util.Map;

public interface DriverProductsService extends AutoCloseable {

    Map<String, List<String>> getNamesOfAllRouteByDatta(String datatime);

    List<DriverProduct> getRoutesByName(String name);

    void createTableNamesOfAllRoute();

    int addToNamesOfAllRoute(String bill, String datatime);

    void deleteTableRoute(String bill);

    void createTableRoute(String bill);

    int addProductToRoute(List<DriverProduct> products, String routeName, String data);

    void createDriverProduct(Product dataSet);

    void close() throws Exception;

    void deleteTableNamesOfAllRoute();
}
