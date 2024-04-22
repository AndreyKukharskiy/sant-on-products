package org.dao.tovarDao;


import org.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsDAOImp implements ProductsDAO {

    private static final String SELECT_ALL_FROM_ALL_NAMES_BILLS_OF_LADING
            = "SELECT * FROM allNamesBillsOfLading";
    private static final String SELECT_BILLS_BY_NAME
            = "SELECT * FROM %s";
    private static final String INSERT_INTO_TO_ALL_NAMES_BILLS_OF_LADIND =
            "INSERT INTO allNamesBillsOfLading(biils_name, bills_datatime) VALUES('%s', '%s')";
    private static final String DROP_BILL_OF_LADING_TABLE = "DROP TABLE IF EXISTS %s;";
    private static final String CREATE_Bill_OF_LADING_TABLE =
            "CREATE TABLE IF NOT EXISTS %s (id SERIAL, name VARCHAR(255), articul VARCHAR(255), count1 REAL, cena REAL, id_tovar INT, nacladnaya VARCHAR(255), dataTime VARCHAR(255),PRIMARY KEY (id));";
    private static final String INSERT_INTO_PRODUCT_TO_Bill_OF_LADING_TABLE =
            "INSERT INTO %s(name, articul, count1, cena, id_tovar, nacladnaya, dataTime) VALUES($$%s$$, '%s', '%s', '%s', '%s', '%s', '%s')";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS tovar (id SERIAL, name VARCHAR(255), articul VARCHAR(255), count1 REAL, cena REAL, id_tovar INT, nacladnaya VARCHAR(255), dataTime VARCHAR(255),PRIMARY KEY (id));";
    private static final String INSERT_PRODUCT = "INSERT INTO tovar(name, articul, count1, cena, id_tovar, nacladnaya, dataTime) VALUES($$%s$$, '%s', '%s', '%s', '%s', '%s', '%s')";
    protected final Connection connection;

    public ProductsDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * method add list of items to database
     *
     * @param products list of items to add in base
     * @return size of items list
     */
    @Override
    public int addProductToBillOfLading(List<Product> products) {
        for (Product product : products) {
            String query = String.format(INSERT_INTO_PRODUCT_TO_Bill_OF_LADING_TABLE, "bills_" + product.getBillOflading(),
                    product.getName(),
                    product.getArticul(),
                    product.getKolichestvo(),
                    product.getCena(),
                    product.getChislo(),
                    product.getBillOflading(),
                    product.getDataTime());
            execUpdate(query);
        }
        return products.size();
    }

    @Override
    public Map<String, List<String>> allNamesBillsOfLadingByDatta(String datatime) {
        List<String> valuesBills = new ArrayList<>();
        Map<String, List<String>> allBills = new HashMap<>();

        execQuery(SELECT_ALL_FROM_ALL_NAMES_BILLS_OF_LADING, resultSet -> {
            while (resultSet.next()) {
                String key = resultSet.getString(2);
                String value = resultSet.getString(1);
                if(key.equals(datatime)) {
                    valuesBills.add(value);
                    allBills.put(resultSet.getString(2), valuesBills);
                }
            }
            return allBills;
        });
        return allBills;
    }

    @Override
    public List<Product> getBillsByName(String name) {
        String fd = String.format(SELECT_BILLS_BY_NAME, name);
        List<Product> products = new ArrayList<>();
        execQuery(fd, resultSet -> {
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8))
                );
            }
            return products;
        });
        return products;
    }

    @Override
    public void addToNamesBillsOfLading(String bill, String datatime) {
        String query = String.format(INSERT_INTO_TO_ALL_NAMES_BILLS_OF_LADIND, bill, datatime);
        execUpdate(query);
    }

    @Override
    public void dropTableBillOfLading(String bill) {
        String query = String.format(DROP_BILL_OF_LADING_TABLE, "bills_" + bill);
        execUpdate(query);
    }

    public void createTableBillOfLading(String bill) {
        String query = String.format(CREATE_Bill_OF_LADING_TABLE, "bills_" + bill);
        execUpdate(query);
    }

    @Override
    public void createProduct(Product dataSet) {
        String query = String.format(INSERT_PRODUCT, dataSet.getName(), dataSet.getArticul(),
                dataSet.getKolichestvo(), dataSet.getCena(), dataSet.getChislo(), dataSet.getBillOflading(), dataSet.getDataTime());
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
