package org.util;

import org.model.Product;
import org.service.ProductsService;
import org.service.ProductsServiceImp;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortedProduct {
    private List<Product> allTovar;
    private List<Product> allTovarRemains;
    private ProductsService productsService = new ProductsServiceImp();

    public SortedProduct() throws SQLException {
    }

    public List<Product> getMergedAllBillsByData(String datatime) {
        Map<String, Integer> buba = new HashMap<>();
        List<Product> mergedAllBills = new ArrayList<>();
        Map<String, List<String>> allBillNames = productsService.getAllNamesBillsOfLadingByData(datatime);
        for (Map.Entry<String, List<String>> entry : allBillNames.entrySet()) {
            List<String> values = entry.getValue();
            for (String s : values) {
                List<Product> empty = productsService.getBillsByName(s);
                for (Product product : empty) {
                    mergedAllBills.add(product);
                }
                buba.put(s, empty.size());
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getName);
        mergedAllBills.sort(comparator);
        return mergedAllBills;
    }

    public List<Product> getSortedData() {
        allTovar = productsService.getAll();
        allTovar.sort(new CompareByName());
        allTovarRemains.sort(new CompareByName());
        return allTovar;
    }

    public List<Product> getAllSink(List<Product> products) {
        int sOrig = products.size();
        Pattern pattern = Pattern.compile("Раковина|Рукомойник|Мебельная раковина|Умывальник|Пьедестал|Полупьедестал");
        List<Product> allSink = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                allSink.add(products.get(i));
                products.remove(i);
            }
        }
        int s1 = products.size();
        int s2 = allSink.size();

        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        allSink.sort(comparator);
        return allSink;
    }

    public List<Product> getAllSmallSized(List<Product> products) {
        String smallSizedProductPatter = "вентилятор|" +
                "Гигиенический|" +
                "Гофра|" +
                "Держатель|" +
                "Дозатор|" +
                "Донный|" +
                "лейка|" +
                "Душевая стойка|" +
                "Душевой гарнитур|" +
                "Душевой комплект|" +
                "трап|" +
                "Ершик|" +
                "Картридж|" +
                "Кнопка|" +
                "Коврик|" +
                "Коландер|" +
                "Комплект крепления для инсталляций|" +
                "Кронштейн|" +
                "Крышка-сиденье|" +
                "Крючки|" +
                "Крючок|" +
                "Мыльница|" +
                "Набор STWORKI Эстерсунд 6 предметов, черный|" +
                "Полка|" +
                "Полотенцедержатель|" +
                "Поручень|" +
                "Проточный|" +
                "Ревизионный люк|" +
                "Салфетки|" +
                "Сифон|" +
                "Слив-перелив|" +
                "Смеситель|" +
                "Средство для|" +
                "Стакан|" +
                "Сушилка|" +
                "Телескопический карниз|" +
                "Теплый пол|" +
                "Термостат|" +
                "Ниппель|" +
                "Шланговое подключение|" +
                "Штора|" +
                "Шумоизоляционная";

        Pattern pattern = Pattern.compile(smallSizedProductPatter);
        List<Product> allSmallSazedProduct = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                allSmallSazedProduct.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getName);
        allSmallSazedProduct.sort(comparator);
        return allSmallSazedProduct;
    }

    public List<Product> getAllTovarBySpecicicPattern(String patern) {
        getSortedData();
        Pattern pattern = Pattern.compile(patern);
        List<Product> allSpecificTovar = new ArrayList<>();

        for (Product t : allTovar) {
            Matcher matcher = pattern.matcher(t.getName());
            while (matcher.find()) {
                allSpecificTovar.add(t);
            }
        }
        //происходит сортировка товара по накладной, по позиции и по имени

        return allSpecificTovar;
    }

    public List<Product> sort(List<Product> mergedAllBills) {
        Comparator<Product> comparator = Comparator.comparing(Product::getName);
        mergedAllBills.sort(comparator);
        return mergedAllBills;
    }

    public List<Product> getAllKomods(List<Product> products) {
        Pattern pattern = Pattern.compile("Тумба|Комод|Шкаф|тумба|комод|шкаф");
        List<Product> allKomods = new ArrayList<>();


        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i) .getName());
            while (matcher.find()) {
                allKomods.add(products.get(i));
                products.remove(products.get(i));
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        allKomods.sort(comparator);
        return allKomods;
    }

    public List<Product> getAllBowl(List<Product> products) {
        Pattern pattern = Pattern.compile("чаша|Чаша|унитаз-компакт|Унитаз-компакт|писуар|Писуар|Подвесной унитаз");
        List<Product> allBowl = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                allBowl.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        allBowl.sort(comparator);
        return allBowl;
    }

    public List<Product> getALLShowerCabin(List<Product> products) {
        Pattern pattern = Pattern.compile("Душевой уголок|Душевая кабина");
        List<Product> showerCabin = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                showerCabin.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        showerCabin.sort(comparator);
        return showerCabin;
    }

    public List<Product> getAllKitchenSink(List<Product> products) {
        Pattern pattern = Pattern.compile("Мойка|мойка");
        List<Product> kitchenSink = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                kitchenSink.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        kitchenSink.sort(comparator);
        return kitchenSink;
    }

    public List<Product> getAllMirrors(List<Product> products) {
        Pattern pattern = Pattern.compile("Зеркало-шкаф|Зеркало");
        List<Product> mirrors = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                mirrors.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        mirrors.sort(comparator);
        return mirrors;
    }

    public List<Product> getAllTowelDryer(List<Product> products) {
        Pattern pattern = Pattern.compile("Полотенцесушитель");
        List<Product> towelDryer = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                towelDryer.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        towelDryer.sort(comparator);
        return towelDryer;
    }

    public List<Product> getAllBath(List<Product> products) {
        Pattern pattern = Pattern.compile("ванна");
        List<Product> bath = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Matcher matcher = pattern.matcher(products.get(i).getName());
            while (matcher.find()) {
                bath.add(products.get(i));
                products.remove(i);
            }
        }
        Comparator<Product> comparator = Comparator.comparing(Product::getBillOflading);
        bath.sort(comparator);
        return bath;
    }

    class CompareByName implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    class CompareByNacladnaya implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getBillOflading().compareTo(o2.getBillOflading());
        }
    }

}
