import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuyCarpet {
   private Map<Long,String> carpets = new HashMap<>();
    private ArrayList<Long> prices = new ArrayList<>();

    private void addCarpet() throws FileNotFoundException {
        File text = new File("list of carpets.txt");
        Scanner scr = new Scanner(text);
        int i =0;
        while (scr.hasNextLine()){
            String str = scr.nextLine();
            String[] str2 = str.split(" ");
            carpets.put(Long.valueOf(str2[1]), str2[0]);
            prices.add( Long.parseLong(str2[1]));
        }

    }

    public ArrayList<String> showLargestNumOfCarpet(long money) throws FileNotFoundException {
        addCarpet();
        Map<Long,String> instanceCarpets = carpets;
        long totalPrice=0;
        ArrayList<String> chosenCarpets = new ArrayList<>();
        while (totalPrice<money){
            if (prices.size()==0) break;
            Long minPrice = min(prices);
            if (minPrice+totalPrice<=money){
                totalPrice+=minPrice;
                chosenCarpets.add(instanceCarpets.get(minPrice));
                instanceCarpets.remove(minPrice);
                prices.remove(minPrice);
            }else break;
        }
        return chosenCarpets;
    }

    private Long min(ArrayList<Long> prices){
        Long answer = prices.get(0);
        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i)<answer)
                answer=prices.get(i);
        }
        return answer;
    }
}
