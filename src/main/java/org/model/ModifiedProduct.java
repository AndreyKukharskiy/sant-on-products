package org.model;

import java.util.Comparator;

public class ModifiedProduct implements Comparator<ModifiedProduct> {
  private String name;
  private double kolichestvo;
  private String articul;
  private double cena;
  private int chislo;
  private String dataTime;
  private String billOflading;

    public ModifiedProduct(String name, String articul, double kolichestvo, double cena, int chislo, String nacladnaya, String dataTime) {
        this.name = name;
        this.kolichestvo = kolichestvo;
        this.articul = articul;
        this.cena = cena;
        this.chislo = chislo;

        this.dataTime = dataTime;
        this.billOflading = nacladnaya;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getBillOflading() {
        return billOflading;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void setBillOflading(String billOflading) {
        this.billOflading = billOflading;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKolichestvo(double kolichestvo) {
        this.kolichestvo = kolichestvo;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setChislo(int chislo) {
        this.chislo = chislo;
    }

    public String getName() {
        return name;
    }

    public double getKolichestvo() {
        return kolichestvo;
    }

    public String getArticul() {
        return articul;
    }

    public double getCena() {
        return cena;
    }

    public int getChislo() {
        return chislo;
    }

    public ModifiedProduct(int chislo, String name, String articul, double kolichestvo, double cena) {
        this.name = name;
        this.kolichestvo = kolichestvo;
        this.articul = articul;
        this.cena = cena;
        this.chislo = chislo;
    }

    @Override
    public String toString() {
        return "Tovar{" +
                "chislo=" + chislo +
                "| name='" + name + '\'' +
                "| articul='" + articul + '\'' +
                "| kolichestvo=" + kolichestvo +
                "| cena=" + cena +
                "| nacladnaya=" + billOflading +
                "| dataTime=" + dataTime +
                '}' +"\n";
    }

    @Override
    public int compare(ModifiedProduct o1, ModifiedProduct o2) {
        return o1.getName().compareTo(o2.getName());
    }
}