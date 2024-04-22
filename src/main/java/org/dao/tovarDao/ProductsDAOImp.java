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
    private static final String SELECT_BILLS_BY_DATA = "SELECT * FROM %s WHERE datatime = $$%s$$;";
    private static final String DROP_BILL_OF_LADING_TABLE = "DROP TABLE IF EXISTS %s;";
    private static final String CREATE_Bill_OF_LADING_TABLE =
            "CREATE TABLE IF NOT EXISTS %s (id SERIAL, name VARCHAR(255), articul VARCHAR(255), count1 REAL, cena REAL, id_tovar INT, nacladnaya VARCHAR(255), dataTime VARCHAR(255),PRIMARY KEY (id));";
    private static final String INSERT_INTO_PRODUCT_TO_Bill_OF_LADING_TABLE =
            "INSERT INTO %s(name, articul, count1, cena, id_tovar, nacladnaya, dataTime) VALUES($$%s$$, '%s', '%s', '%s', '%s', '%s', '%s')";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS tovar (id SERIAL, name VARCHAR(255), articul VARCHAR(255), count1 REAL, cena REAL, id_tovar INT, nacladnaya VARCHAR(255), dataTime VARCHAR(255),PRIMARY KEY (id));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS tovar;";

    private static final String INSERT_TOVAR = "INSERT INTO tovar(name, articul, count1, cena, id_tovar, nacladnaya, dataTime) VALUES($$%s$$, '%s', '%s', '%s', '%s', '%s', '%s')";
    private static final String DELETE_TOVAR = "DELETE FROM tovar WHERE id ='%s'";
    private static final String SELECT_TOVAR_BY_ID = "SELECT * FROM tovar WHERE id='%s';";
    private static final String SELECT_TOVAR_BY_NAME = "SELECT * FROM tovar WHERE name LIKE '%s';";
    private static final String UPDATE_TOVAR = "UPDATE tovars SET name='%s',articul='%s',count1='%s' WHERE id = '%s';";
    private static final String SELECT_ALL_TOVARS = "SELECT * FROM tovar;";

    private static final String SELECT_ALL_TOVARS_SORTED_BY_NAME = "SELECT * FROM tovar ORDER BY name;";
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
        String query = "SELECT * FROM %s";
        String fd = String.format(query, name);
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

    /**
     * Return all schemas in base by time
     * @param data
     * @return all bill by current time
     */
    @Override
    public List<Product> getAllBillsByData(String data) {
        List<Product> products = new ArrayList<>();
        String query = String.format(SELECT_BILLS_BY_DATA, data);

        execQuery(SELECT_ALL_TOVARS, resultSet -> {
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
    public void dropTableBillOfLading(String bill) {
        String query = String.format(DROP_BILL_OF_LADING_TABLE, "bills_" + bill);
        execUpdate(query);
    }

    public void createTableBillOfLading(String bill) {
        String query = String.format(CREATE_Bill_OF_LADING_TABLE, "bills_" + bill);
        execUpdate(query);
    }

    @Override
    public void createTable() {
        execUpdate(CREATE_TABLE);
    }

    @Override
    public void deleteTable() {
        execUpdate(DROP_TABLE);
    }

    @Override
    public void createTovar(Product dataSet) {
        String query = String.format(INSERT_TOVAR, dataSet.getName(), dataSet.getArticul(),
                dataSet.getKolichestvo(), dataSet.getCena(), dataSet.getChislo(), dataSet.getBillOflading(), dataSet.getDataTime());
        execUpdate(query);
    }

    @Override
    public void deleteTovar(long id) {
        String query = String.format(DELETE_TOVAR, id);
        execUpdate(query);
    }

    @Override
    public void updateTovar(Product dataSet) {
        String query = String.format(UPDATE_TOVAR, dataSet.getName(), dataSet.getArticul(),
                dataSet.getKolichestvo(), dataSet.getCena(), dataSet.getChislo());
        execUpdate(query);
    }

    @Override
    public Product getById(long id) {
        return execQuery(String.format(SELECT_TOVAR_BY_ID, id), resultSet -> {
            resultSet.next();

            return new Product(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8));
        });
    }

    @Override
    public Product getByName(String name) {
        return execQuery(String.format(SELECT_TOVAR_BY_NAME, name), resultSet -> {
            resultSet.next();
            return new Product(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8));
        });
    }

    @Override
    public List<Product> getAll() {
        List<Product> Tovars = new ArrayList<>();
        execQuery(SELECT_ALL_TOVARS, resultSet -> {
            while (resultSet.next()) {
                Tovars.add(new Product(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8))
                );
            }
            return Tovars;
        });
        return Tovars;
    }

    @Override
    public List<Product> getSorted() {
        List<Product> Tovars = new ArrayList<>();
        execQuery(SELECT_ALL_TOVARS_SORTED_BY_NAME, resultSet -> {
            while (resultSet.next()) {
                Tovars.add(new Product(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8))
                );
            }
            return Tovars;
        });
        return Tovars;
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
