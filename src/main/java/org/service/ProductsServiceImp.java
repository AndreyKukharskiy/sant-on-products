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
    public void createTable() {
        dao.createTable();
    }

    @Override
    public void deleteTable() {
        dao.deleteTable();
    }

    @Override
    public void create(Product dataSet) {
        dao.createTovar(dataSet);
    }

    @Override
    public void delete(long id) {
        dao.deleteTovar(id);
    }

    @Override
    public void update(Product dataSet) {
        dao.updateTovar(dataSet);
    }

    @Override
    public Product getById(long id) {
        return dao.getById(id);
    }

    public Product getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Product> getAll() {
        return dao.getAll();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public List<Product> getSorted() {
        return dao.getSorted();
    }

}
