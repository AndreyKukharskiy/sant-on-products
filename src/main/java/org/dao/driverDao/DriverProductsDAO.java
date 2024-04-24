package org.dao.driverDao;

import org.model.DriverProduct;
import org.model.Product;

import java.util.List;
import java.util.Map;

public interface DriverProductsDAO {
    Map<String, List<String>> getNamesOfAllRouteByDatta(String datatime);

    int addProductToRoute(List<DriverProduct> products, String routeName, String data);

    List<DriverProduct> getRoutesByName(String name);

    void createTableNamesOfAllRoute();

    int addToNamesOfAllRoute(String bill, String datatime);

    void deleteTableRoute(String bill);

    void createTableRoute(String bill);

    void createDriverProduct(DriverProduct dataSet);

    void deleteTableNamesOfAllRoute();
}
