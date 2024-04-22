package org.service;

import org.dao.tovarDao.ProductsDAO;
import org.dao.tovarDao.ProductsDAOImp;
import org.model.Product;
import org.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductsServiceImp implements ProductsService {
    private final Connection connection;
    private ProductsDAO dao;

    public ProductsServiceImp() throws SQLException {
        connection = ConnectionHelper.getConnection();
        dao = new ProductsDAOImp(connection);
    }

    @Override
    public void dropTableBillOfLading(String bill) {
        dao.dropTableBillOfLading(bill);
    }

    @Override
    public void createTableBillOfLading(String bill) {
        dao.createTableBillOfLading(bill);
    }

    /**
     * return product of bill
     *
     * @param name of bill
     * @return list of all product from current bill
     */
    @Override
    public List<Product> getBillsByName(String name) {
        List<Product> products = dao.getBillsByName(name);
        return products;
    }

    @Override
    public int addProductToBillOfLading(List<Product> products) {
        dao.addProductToBillOfLading(products);
        return products.size();
    }

    @Override
    public void addToNamesBillsOfLading(String bill, String datatime) {
        dao.addToNamesBillsOfLading(bill, datatime);
    }

    @Override
    public Map<String, List<String>> getAllNamesBillsOfLadingByData(String datatime) {
        return dao.allNamesBillsOfLadingByDatta(datatime);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
