package ru.csi.task.services;

import ru.csi.task.model.Price;

import java.util.ArrayList;
import java.util.List;

public interface PriceMergerer {
    ArrayList<Price> mergePrices(ArrayList<Price> existingPrices, ArrayList<Price> newPrices) throws CloneNotSupportedException;
}
