package org.service.driverProductService;

import org.dao.driverDao.DriverProductsDAOImp;
import org.model.DriverProduct;
import org.model.Product;
import org.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DriverProductsServiceImp implements DriverProductsService {
    private final Connection connection;
    private DriverProductsDAOImp dao;

    public DriverProductsServiceImp() throws SQLException {
        connection = ConnectionHelper.getConnection();
        dao = new DriverProductsDAOImp(connection);
    }


    @Override
    public Map<String, List<String>> getNamesOfAllRouteByDatta(String datatime) {
        return dao.getNamesOfAllRouteByDatta(datatime);
    }

    @Override
    public List<DriverProduct> getRoutesByName(String name) {
        return dao.getRoutesByName(name);
    }

    @Override
    public void createTableNamesOfAllRoute() {
        dao.createTableNamesOfAllRoute();

    }

    @Override
    public int addToNamesOfAllRoute(String route, String datatime) {
       return dao.addToNamesOfAllRoute(route, datatime);
    }

    @Override
    public void deleteTableRoute(String route) {
        dao.deleteTableRoute(route);
    }

    @Override
    public void createTableRoute(String route) {
        dao.createTableRoute(route);
    }

    @Override
    public int addProductToRoute(List<DriverProduct> products, String routeName, String data) {
        return dao.addProductToRoute(products, routeName, data);
    }

    @Override
    public void createDriverProduct(Product dataSet) {

    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public void deleteTableNamesOfAllRoute() {
        dao.deleteTableNamesOfAllRoute();
    }
}
