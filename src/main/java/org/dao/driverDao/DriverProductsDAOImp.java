package org.dao.driverDao;

import org.dao.ResultHandler;
import org.model.DriverProduct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverProductsDAOImp implements DriverProductsDAO {
    private static final String SELECT_ALL_FROM_ALL_NAMES_OF_ROUTES = "SELECT * FROM namesOfAllRoute";
    private static final String INSERT_INTO_PRODUCT_TO_Bill_OF_LADING_TABLE =
            "INSERT INTO %s (routeName, position, name, article, barcode, count, notFound, order1) VALUES($$%s$$, $$%s$$, $$%s$$, $$%s$$, $$%s$$, $$%s$$, $$%s$$, $$%s$$)";

    protected final Connection connection;

    public DriverProductsDAOImp(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void createTableNamesOfAllRoute() {
        String query = "CREATE TABLE IF NOT EXISTS namesOfAllRoute (dataTime VARCHAR(255), nameOfRoute VARCHAR(255) UNIQUE)";
        execUpdate(query);
    }

    @Override
    public void createTableRoute(String route) {
        String query = "CREATE TABLE IF NOT EXISTS %s (	id SERIAL, routeName VARCHAR(255), position VARCHAR(255), name VARCHAR(255), article VARCHAR(255), barcode VARCHAR(255), count VARCHAR(255), notFound VARCHAR(255), order1 VARCHAR(255), PRIMARY KEY (id), FOREIGN KEY (routeName) REFERENCES namesOfAllRoute(nameOfRoute));";
        String buba = String.format(query, "route_" + route);
        execUpdate(buba);
    }

    @Override
    public void deleteTableRoute(String route) {
        String query = "DROP TABLE %s";
        String buba = String.format(query, "route_" + route);
        execUpdate(buba);
    }


    @Override
    public int addToNamesOfAllRoute(String nameOfRoute, String datatime) {
        String query = "INSERT INTO namesOfAllRoute (dataTime, nameOfRoute) VALUES ($$%s$$, '%s')";
        String buba = String.format(query, datatime, "route_" + nameOfRoute);
        return execUpdate(buba);
    }

    @Override
    public Map<String, List<String>> getNamesOfAllRouteByDatta(String datatime) {
        List<String> valuesBills = new ArrayList<>();
        Map<String, List<String>> allBills = new HashMap<>();
        execQuery(SELECT_ALL_FROM_ALL_NAMES_OF_ROUTES, resultSet -> {
            while (resultSet.next()) {
                String routeName = resultSet.getString(2);
                String data = resultSet.getString(1);
                //valuesBills.add(routeName);
                if (data.equals(datatime)) {
                    valuesBills.add(routeName);
                    allBills.put(resultSet.getString(1), valuesBills);
                }
            }
            return allBills;
        });
        return allBills;
    }

    @Override
    public int addProductToRoute(List<DriverProduct> products, String routeName, String data) {
        addToNamesOfAllRoute(routeName, data);
        for (DriverProduct product : products) {
            String query = String.format(INSERT_INTO_PRODUCT_TO_Bill_OF_LADING_TABLE,
                    "route_" + routeName,
                    "route_" + routeName,
                    product.getPosition(),
                    product.getName(),
                    product.getArticle(),
                    product.getBarcode(),
                    product.getCount(),
                    product.getNotFound(),
                    product.getOrder());
            execUpdate(query);
        }
        return products.size();
    }

    @Override
    public List<DriverProduct> getRoutesByName(String name) {
        String query = "SELECT * FROM route_%s";
        String buba = String.format(query, name);
        List<DriverProduct> products = new ArrayList<>();
        execQuery(buba, resultSet -> {
            while (resultSet.next()) {
                String position = resultSet.getString(2);
                String nam2e = resultSet.getString(3);
                String article = resultSet.getString(4);
                String barcode = resultSet.getString(5);
                double count = resultSet.getDouble(6);
                String notFound = resultSet.getString(7);
                String order = resultSet.getString(8);
            }
            return products;
        });
        return products;
    }

    @Override
    public void createDriverProduct(DriverProduct dataSet) {

    }

    @Override
    public void deleteTableNamesOfAllRoute() {
        String query = "DROP TABLE namesOfAllRoute";
        execUpdate(query);
    }

    private int execUpdate(String update) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(update);
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T execQuery(String query, ResultHandler<T> handler) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
