package com.mycompany.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.app.Models.Carpet;

public class BuyCarpet {
 // private Map<Long, String> carpets = new HashMap<>();
 // private ArrayList<Long> prices = new ArrayList<>();



  public ArrayList<String> showLargestNumOfCarpet(long money,ArrayList<Carpet> carpetsArr)  {
    //Map<Long, String> instanceCarpets = carpets;
    long totalPrice = 0;
    ArrayList<String> chosenCarpets = new ArrayList<>();
    while (totalPrice < money) {
      if (carpetsArr.size() == 0)
        break;
      Long minPrice = min(carpetsArr);
      if (minPrice + totalPrice <= money) {
        totalPrice += minPrice;
        Carpet carpetWithMinPrice = getCarpet(minPrice,carpetsArr);
        chosenCarpets.add(carpetWithMinPrice.name);
        carpetsArr.remove(carpetWithMinPrice);

      } else
        break;
    }
    return chosenCarpets;
  }

  public Carpet getCarpet(Long price, ArrayList<Carpet> carpetsArr){
    for (int i = 0; i <carpetsArr.size() ; i++) {
     if (carpetsArr.get(i).price == price){
       return carpetsArr.get(i);

     }
    }
    return null;
  }


  private Long min(ArrayList<Carpet> carpets) {
   Long answer = Long.valueOf(carpets.get(0).price);
    for (int i = 0; i < carpets.size(); i++) {
      if (carpets.get(i).price < answer)
        answer = Long.valueOf(carpets.get(i).price);
    }
    return answer;
  }
}
