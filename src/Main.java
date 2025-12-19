// Main.java — Students version
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

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
                    catch (Exception e){
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
        if(month >= 0 && month <= 11){
            Scanner reader = null;
            System.out.println(months[month]);
            String fileName = "Data_Files/" + months[month] +".txt";
            try {
                File F = new File(fileName);
                reader = new Scanner(Paths.get(F.getAbsolutePath()));
                int Max = 0;
                String Commodity = "";
                while (reader.hasNextLine()) {
                    String[] Line = reader.nextLine().split(",");
                    try{
                        int control = Integer.parseInt(Line[2]);
                    }
                    catch (NumberFormatException e){
                        continue ;
                    }
                    for(int i=0 ; i<141 ; i++) {
                        if(Integer.parseInt(Line[2]) > Max){
                            Max = Integer.parseInt(Line[2]);
                            Commodity = Line[1];
                        }
                    }
                }
                return Commodity + " " + Max ;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        return  "INVALID_MONTH";
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month >= 0 && month <= 11 && day >= 1 && day <= 28){
            try{
                int sum = 0;
                for(int i=0 ; i<=4 ; i++){
                    sum += profitData[month][day-1][i];
                }
                return sum ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -99999;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        if(fromDay <= toDay && fromDay >= 1 && fromDay <= 28 && toDay >= 1 && toDay <= 28){
            boolean control = false;
            int commodityIndex = -1;
            for(int i=0 ; i<=commodities.length ; i++){
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
            System.out.print(day + ".day : ");
            return Max;
        }
        return -1;
    }

    public static String bestMonthForCommodity(String comm) {

        boolean control = false;
        int commIndex = -1;
        for(int i=0 ; i<=commodities.length ; i++){
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

        return months[bestmonth] + ": " + max ;
    }

    public static int consecutiveLossDays(String comm) {
        boolean control = false;
        int commIndex = -1;
        for(int i=0 ; i<=commodities.length ; i++){
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
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");

        //System.out.println(mostProfitableCommodityInMonth(1)); //Works but must fix it
        //System.out.println(profitData[0][1][0]); //Works :)
        //System.out.println(totalProfitOnDay(0,1)); //Works :)
        //System.out.println(commodityProfitInRange("Oil",1, 2)); //Works :)
        //System.out.println(bestDayOfMonth(1)); //Works but must fix it
        //System.out.println(bestMonthForCommodity("Gold")); //Works :)
        //System.out.println(consecutiveLossDays("Gold")); //Works :)
        //
        // Don't forget to commit and delete these
    }
}
