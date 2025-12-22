// Main.java — Students version
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Math.abs;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    public static int[][][] profitData = new int[12][28][5];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner reader = null;
        for (int Month=0 ; Month<=11 ; Month++){
            String fileName = "Data_Files/" + months[Month] +".txt";
            try {
                File file = new File(fileName);
                reader = new Scanner(Paths.get(file.getAbsolutePath()));
                while (reader.hasNextLine()) {
                    try {
                        String[] parts = reader.nextLine().split(",");
                        int day = Integer.parseInt(parts[0]);
                        String commodityName = parts[1];
                        int profit = Integer.parseInt(parts[2]);

                        int dayIndex = day - 1; // for day 1-28 but index 0-27

                        int commodityIndex = -1;
                        for (int i = 0; i < commodities.length; i++) {
                            if (commodities[i].equals(commodityName)) {
                                commodityIndex = i;
                                break;
                            }
                        }
                        if (commodityIndex != -1 && dayIndex >= 0 && dayIndex < 28) {
                            profitData[Month][dayIndex][commodityIndex] = profit;
                        }
                    }
                    catch (NumberFormatException e){
                        continue ;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month >= 0 && month <= 11) {
            int sumMax = 0;
            int sumGold = 0;
            int sumOil = 0;
            int sumSilver = 0;
            int sumWheat = 0;
            int sumCopper = 0;
            for(int i=1 ; i<=28 ; i++){
                sumGold += profitData[month][i-1][0];
                sumOil += profitData[month][i-1][1];
                sumSilver += profitData[month][i-1][2];
                sumWheat += profitData[month][i-1][3];
                sumCopper += profitData[month][i-1][4];
            }
            int[] arr = {sumGold,sumOil,sumSilver,sumWheat,sumCopper};
            String Max_Com = "";
            for(int j=0; j<arr.length ; j++) {
                if (arr[j] > sumMax) {
                    sumMax = arr[j];
                    Max_Com = commodities[j];
                }
            }
            return Max_Com + " " + sumMax;
        }
        return  "INVALID_MONTH";
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month >= 0 && month <= 11 && day >= 1 && day <= 28){
                int sum = 0;
                for(int i=0 ; i<=4 ; i++){
                    sum += profitData[month][day-1][i];
                }
                return sum ;
        }
        return -99999;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        if(fromDay <= toDay && fromDay >= 1 && fromDay <= 28 && toDay >= 1 && toDay <= 28){
            boolean control = false;
            int commodityIndex = -1;
            for(int i=0 ; i<commodities.length ; i++){
                if (commodities[i].equals(commodity)) {
                    commodityIndex = i;
                    control = true;
                    break;
                }
            }
            if (!control){return -99999;}
            int sum = 0;
            for(int i=0 ; i<=11 ; i++){
                for(int j=fromDay ; j<=toDay ; j++){
                    sum += profitData[i][j-1][commodityIndex];
                }
            }
            return sum;
        }
        return -99999;
    }

    public static int bestDayOfMonth(int month) {
        if(month >= 0 && month <= 11){
            int Max = 0;
            int day = -1;
            for(int i=1 ; i<=28 ; i++){
                int sum = 0;
                for (int j=0 ; j<commodities.length ; j++) {
                    sum += profitData[month][i - 1][j];
                }
                if(sum > Max){
                    Max = sum ;
                    day = i ;
                }
            }
            return day;
        }
        return -1;
    }

    public static String bestMonthForCommodity(String comm) {
        boolean control = false;
        int commIndex = -1;
        for(int i=0 ; i<commodities.length ; i++){
            if (commodities[i].equals(comm)) {
                commIndex = i;
                control = true;
                break;
            }
        }
        if (!control){return "INVALID_COMMODITY";}
        int bestmonth = -1;
        int max = 0;
        for(int i=0 ; i<=11 ; i++){
            int sum = 0;
            for(int j=1 ; j<=28 ; j++){
                sum += profitData[i][j-1][commIndex];
            }
            if(sum > max){
                max = sum ;
                bestmonth = i ;
            }
        }

        return months[bestmonth] ;
    }

    public static int consecutiveLossDays(String comm) {
        boolean control = false;
        int commIndex = -1;
        for(int i=0 ; i<commodities.length ; i++){
            if (commodities[i].equals(comm)) {
                commIndex = i;
                control = true;
                break;
            }
        }
        if (!control){return -1;}
        int Longest_Streak = 0;
        int current_Streak = 0;
        for(int i=0 ; i<=11 ; i++){
            for(int j=1 ; j<=28 ; j++){
                if(profitData[i][j-1][commIndex] < 0){current_Streak++ ;
                    if(current_Streak > Longest_Streak){
                        Longest_Streak = current_Streak ;
                    }
                }
                if (profitData[i][j-1][commIndex] >= 0){current_Streak = 0 ;}
            }
        }

        return  Longest_Streak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        boolean control = false;
        int comIndex = -1;
        for(int i=0 ; i<commodities.length ; i++){
            if (commodities[i].equals(comm)) {
                comIndex = i;
                control = true;
                break;
            }
        }
        if (!control){return -1;}
        int days = 0;
        for(int i=0 ; i<=11 ; i++){
            for(int j=1 ; j<=28 ; j++){
                if(profitData[i][j-1][comIndex] > threshold){
                    days++ ;
                }
            }
        }

        return days;
    }

    public static int biggestDailySwing(int month) {
        if(month >= 0 && month <= 11){
            int abs_difference = 0;
            int difference = 0;
            int sum1 = 0;
            int sum2 = 0;
            for(int i=1 ; i<=27 ; i++){
                for(int j=0 ; j<=4 ; j++){
                    sum1 += profitData[month][i-1][j] ;
                    sum2 += profitData[month][i][j] ;
                }
                if ((sum2 - sum1) < 0) {difference = -(sum2 - sum1);}
                else {difference = sum2 - sum1;}
                if (difference > abs_difference){abs_difference = difference ;}
                sum1 = 0;
                sum2 = 0;
            }
            return abs_difference ;
        }

        return -99999;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        boolean control1 = false;
        boolean control2 = false;
        int c1Index = -1;
        int c2Index = -1;
        for(int i=0 ; i<commodities.length ; i++){
            if (commodities[i].equals(c1)) {
                c1Index = i;
                control1 = true;
            }
            if (commodities[i].equals(c2)) {
                c2Index = i;
                control2 = true;
            }
        }
        if (!(control1 && control2)){return "INVALID_COMMODITY";}

        int c1total = 0;
        int c2total = 0;
        for(int i=0; i<=11; i++){
            for(int j=1 ; j<=28 ; j++){
                c1total += profitData[i][j-1][c1Index];
                c2total += profitData[i][j-1][c2Index];
            }
        }

        if(c1total > c2total) {
            int dif = c1total - c2total ;
            return c1 +" is better by " + dif;}
        if(c1total < c2total) {
            int dif = c2total - c1total ;
            return c2 +" is better by " + dif;}
        return "Equal" ;
    }

    public static String bestWeekOfMonth(int month) {
        if(month >= 0 && month <= 11){
            int Week_Max = 0;
            String Best_Week = "";
            int Week1 = 0;
            for(int i=1; i<=7; i++){
                for(int j=0 ; j<=4 ; j++) {
                    Week1 += profitData[month][i-1][j];
                }
            }
            if (Week1 > Week_Max){Week_Max = Week1 ;}
            int Week2 = 0;
            for(int i=8; i<=14; i++){
                for(int j=0 ; j<=4 ; j++) {
                    Week2 += profitData[month][i-1][j];
                }
            }
            if (Week2 > Week_Max){Week_Max = Week2 ;}
            int Week3 = 0;
            for(int i=15; i<=21; i++){
                for(int j=0 ; j<=4 ; j++) {
                    Week3 += profitData[month][i-1][j];
                }
            }
            if (Week3 > Week_Max){Week_Max = Week3 ;}
            int Week4 = 0;
            for(int i=22; i<=28; i++){
                for(int j=0 ; j<=4 ; j++) {
                    Week4 += profitData[month][i-1][j];
                }
            }
            if (Week4 > Week_Max){Week_Max = Week4 ;}
            int[] arr = {Week1,Week2,Week3,Week4};
            for (int i=1; i<=4; i++){
                if(Week_Max == arr[i-1]){
                    Best_Week = "Week " + i ;
                }
            }

            return Best_Week ;
        }

        return "INVALID_MONTH";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");

        System.out.println(mostProfitableCommodityInMonth(0)); //Works but must fix it
        System.out.println(profitData[0][0][0]); //Works :)
        System.out.println(totalProfitOnDay(0,15)); //Works :)
        System.out.println(commodityProfitInRange("Oil",1, 2)); //Works :)
        System.out.println(bestDayOfMonth(0)); //Works :)
        System.out.println(bestMonthForCommodity("Gold")); //Works :)
        System.out.println(consecutiveLossDays("Gold")); //Works :)
        System.out.println(daysAboveThreshold("Gold",3000)); //Works :)
        System.out.println(biggestDailySwing(0)); //Works :)
        System.out.println(compareTwoCommodities("Gold", "Oil")); //Works :)
        System.out.println(bestWeekOfMonth(0)); //Works :)
        // Don't forget to commit and delete these
    }
}
