package ru.csi.task.services;

import ru.csi.task.model.Price;
import ru.csi.task.utils.Utils;

import java.util.*;

/**
 * Логика работы следующая:
 * Метод принимает в себя 2 параметра:
 *
 * @param pricesOld коллекция ArrayList, содержащая текущие цены
 * @param pricesNew коллекция ArrayList, содержащая новые цены
 * @return actualPrices коллекция ArrayList, содержащая обновленные цены
 * В первую очередь метод проверяет полученные коллекции на null и пустоту, затем
 * метод пробегается каждой новой ценой п окаждой старой цене для того, чтобы определить коррелируют ли они.
 * Если номер цены и номер товара обеих коллекций совпали, то определяется схема расположения коллекций друг относительно друга.
 * и в зависимости от расположения даты и цены сдвигаются.
 * Сложность O(n^2).
 */
public class PriceMergerImpl implements PriceMergerer {
    public ArrayList<Price> mergePrices(ArrayList<Price> pricesOld, ArrayList<Price> pricesNew) {
        ArrayList<Price> actualPrices = new ArrayList<Price>();
        if (null == pricesNew && null == pricesOld) {
            return actualPrices;
        } else if (null == pricesNew) {
            return pricesOld;
        } else if (null == pricesOld) {
            return pricesNew;
        }
        if (pricesNew.isEmpty() && pricesOld.isEmpty()) {
            return actualPrices;
        } else if (pricesNew.isEmpty()) {
            return pricesOld;
        } else if (pricesOld.isEmpty()) {
            return pricesNew;
        }
        for (
                Price oldPrice : pricesOld) {
            Date beginPrice = oldPrice.getBegin();
            Date endPrice = oldPrice.getEnd();
            for (Price newPrice : pricesNew) {
                if (oldPrice.getProductCode() == newPrice.getProductCode()
                        && oldPrice.getNumber() == newPrice.getNumber()
                        && oldPrice.getDepart() == newPrice.getDepart()) {
                    Date beginNewPrice = newPrice.getBegin();
                    Date endNewPrice = newPrice.getEnd();
                    if (Utils.isOldContainsNew(beginPrice, endPrice, beginNewPrice, endNewPrice)) {
                        if (newPrice.getValue().equals(oldPrice.getValue())) {
                            actualPrices.add(oldPrice);
                        } else {
                            try {
                                Price lastPrice = oldPrice.clone();
                                oldPrice.setEnd(Utils.setTime(beginNewPrice, -1));
                                lastPrice.setBegin(Utils.setTime(endNewPrice, 1));
                                actualPrices.add(oldPrice);
                                actualPrices.add(lastPrice);
                                actualPrices.add(newPrice);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }

                        }
                    } else if (Utils.isNewContainsOld(beginPrice, endPrice, beginNewPrice, endNewPrice)) {
                        if (newPrice.getValue().equals(oldPrice.getValue())) {
                            actualPrices.add(newPrice);
                        } else {
                            try {
                                Price lastPrice = newPrice.clone();
                                newPrice.setEnd(Utils.setTime(beginPrice, -1));
                                lastPrice.setBegin(Utils.setTime(endPrice, 1));
                                actualPrices.add(oldPrice);
                                actualPrices.add(lastPrice);
                                actualPrices.add(newPrice);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (Utils.isNewRight(beginPrice, endPrice, beginNewPrice, endNewPrice)) {
                        if (newPrice.getValue().equals(oldPrice.getValue())) {
                            newPrice.setBegin(oldPrice.getBegin());
                            actualPrices.add(newPrice);
                        } else {
                            oldPrice.setEnd(Utils.setTime(newPrice.getBegin(), -1));
                            actualPrices.add(oldPrice);
                            actualPrices.add(newPrice);
                        }
                    } else if (Utils.isNewLeft(beginPrice, endPrice, beginNewPrice, endNewPrice)) {
                        if (newPrice.getValue().equals(oldPrice.getValue())) {
                            oldPrice.setBegin(newPrice.getBegin());
                            actualPrices.add(oldPrice);
                        } else {
                            oldPrice.setBegin(Utils.setTime(newPrice.getEnd(), 1));
                            actualPrices.add(oldPrice);
                            actualPrices.add(newPrice);
                        }
                    }
                }
            }
        }
        return actualPrices;
    }
}
