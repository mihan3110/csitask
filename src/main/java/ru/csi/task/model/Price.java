package ru.csi.task.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Price implements Cloneable{
    private long id;
    private String productCode;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;
    Date dateNow = new Date();
    SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Price clone() throws CloneNotSupportedException{

        return (Price) super.clone();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return  id == price.id &&
                number == price.number &&
                depart == price.depart &&
                value == price.value &&
                Objects.equals(productCode, price.productCode) &&
                Objects.equals(begin, price.begin) &&
                Objects.equals(end, price.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCode, number, depart, begin, end, value, dateNow, formatForDateNow);
    }

    public Long getId() {
        return id;
    }


    public Price() {
    }

    @Override
    public String toString() {
        return productCode +' ' + '|' +
                " " + number + ' ' +'|' +
                " " + depart +' ' +'|' +
                " " + formatForDateNow.format(begin) +' ' +'|' +
                " " + formatForDateNow.format(end) +' ' +'|' +
                " " + value;
    }

    public Price(Long id, String productCode, Integer number, Integer depart, Date begin, Date end, Long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDepart() {
        return depart;
    }

    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
