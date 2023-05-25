package com.mycompany.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.app.Models.Carpet;

public class BuyCarpet {
  private Map<Long, String> carpets = new HashMap<>();
  private ArrayList<Long> prices = new ArrayList<>();

  public ArrayList<String> showLargestNumOfCarpet(long money) throws FileNotFoundException {
    Map<Long, String> instanceCarpets = carpets;
    long totalPrice = 0;
    ArrayList<String> chosenCarpets = new ArrayList<>();
    while (totalPrice < money) {
      if (prices.size() == 0)
        break;
      Long minPrice = min(prices);
      if (minPrice + totalPrice <= money) {
        totalPrice += minPrice;
        chosenCarpets.add(instanceCarpets.get(minPrice));
        instanceCarpets.remove(minPrice);
        prices.remove(minPrice);
      } else
        break;
    }
    return chosenCarpets;
  }

  BuyCarpet(ArrayList<Carpet> carpetsArr) {
    carpetsArr.forEach(carpet -> {
      carpets.put(Long.valueOf(carpet.price), carpet.name);
      prices.add(Long.valueOf(carpet.price));
    });

  }

  private Long min(ArrayList<Long> prices) {
   Long answer = prices.get(0);
    for (int i = 0; i < prices.size(); i++) {
      if (prices.get(i) < answer)
        answer = prices.get(i);
    }
    return answer;
  }
}
