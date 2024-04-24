package service.driverProductService;

import org.apache.logging.log4j.core.appender.routing.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.model.DriverProduct;
import org.service.driverProductService.DriverProductsServiceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DriverProductsServiceImpTest {
    private DriverProductsServiceImp driverProductsService;
    String route1 = "285392";
    String route2 = "285383";
    String route3 = "285385";
    String data = "19.04.2014";
    DriverProduct product1 = new DriverProduct(
            "1",
            "Слив-перелив Radomir Vannesa хром",
            "2-19-0-0-0-000",
            "4610013483774",
            1.0,
            "НЕ ИЩИ!",
            "'6958262"
    );
    DriverProduct product2 = new DriverProduct(
            "1",
            "Смеситель для ванны с душем Kludi Bozz 386503976 черный матовый",
            "386503976",
            "4021344098276",
            1.0,
            "НЕ ИЩИ!",
            "'6876892"
    );

    @Before
    public void openConnection() throws SQLException {
        driverProductsService = new DriverProductsServiceImp();
        driverProductsService.createTableNamesOfAllRoute();
        driverProductsService.createTableRoute(route1);
        driverProductsService.createTableRoute(route2);
        driverProductsService.createTableRoute(route3);
    }

    @After
    public void closeConnection() throws Exception {
        driverProductsService.deleteTableRoute(route1);
        driverProductsService.deleteTableRoute(route2);
        driverProductsService.deleteTableRoute(route3);
        driverProductsService.deleteTableNamesOfAllRoute();
        driverProductsService.close();
    }

    @Test
    public void addToNamesOfAllRoute() {
        int result = driverProductsService.addToNamesOfAllRoute(route1, data);
        assertNotEquals(result, 0);
    }

    @Test
    public void getNamesOfAllRouteByDatta() {
        driverProductsService.addToNamesOfAllRoute(route1, data);
        driverProductsService.addToNamesOfAllRoute(route2, data);
        driverProductsService.addToNamesOfAllRoute(route3, data);
        List<String> expectedList = new ArrayList<>();
        expectedList.add("route_" + route1);
        expectedList.add("route_" + route2);
        expectedList.add("route_" + route3);

        Map<String, List<String>> allRoutsByData = driverProductsService.getNamesOfAllRouteByDatta(data);
        List<String> resultRouts = allRoutsByData.get(data);

        assertThat(resultRouts, is(expectedList));
    }

    @Test
    public void addProductToRoute() {
        List<DriverProduct> products = new ArrayList<>();
        products.add(product1);
        products.add(product1);
        products.add(product2);
        products.add(product2);

        driverProductsService.addProductToRoute(products, route1, data);

    }

    @Test
    public void getRoutesByName() {
        driverProductsService.getRoutesByName(route1);
    }
}
