/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainwater.analysis;

/**
 *
 * @author Mert Yacan
 */
public class RainwaterAnalysis {

    public static int width = 10; //adjustable map size
    public static int height = 10;
    public static int[][] topography = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                         { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, 
                                         { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                                         { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
                                         { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                                         { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
                                         { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                                         { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                                         { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                                         { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10} 
                                        }; //sloped to one direction for testing...
    public static double[][] currentWater = new double[width][height]; //current water situation
    public static double[][] nextDistributedWater = new double[width][height]; //to store the water information for the next round
    public static int[][] absorbation = new int[width][height]; //not used yet
    public static int rainSpeed = 100; //variable to adjust water to be added
    
    //automaticly creating topography but not used yet
    public static void determineTheTopograpgy(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int randomtopo = (int) (1 + (Math.random() * 5));
                topography[i][j] = randomtopo;
            }
        }
    }

    //printing the water situation each round
    public static void printWaterSituation() {
        System.out.println("----------");
        for (int j = height - 1; j > -1; j--) {
            for (int i = 0; i < width; i++) {
                System.out.print((int) currentWater[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    //adding water to random points
    public static void rainStart() {
        int randomnumber = (int) (Math.random() * 10)+1;
        for (int i = 0; i < rainSpeed * randomnumber; i++) {
            int randomtempx = (int) ((Math.random() * width));
            int randomtempy = (int) ((Math.random() * height));
            currentWater[randomtempx][randomtempy]++;
        }
    }

    //sending point informations to distributewater method but if water to be distributed on a point is very low value then pass it by
    //sending currentwater value to nextDistributedWater to keep the same value next round. There is no need to distribute very low value of water. We can ignore it.
    public static void distributeWaterStart() {
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                if ((currentWater[i][j]) <= 0.50) {
                    nextDistributedWater[i][j] = currentWater[i][j];
                } else {
                    distributeWater(i, j);
                }
            }
        }
    }

    //getting x y coordinates and according to their location finding its neighbour points. then sending neighbour points' direction according to the main point, to compareWithAround method
    public static void distributeWater(int x, int y) {
        if (x == 0) {
            if (y == 0) {
                String temp[] = {"TOP", "RIGHT", "TOPRIGHT"};
                compareWithAround(x, y, temp);
            } else if (y == height - 1) {
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT"};
                compareWithAround(x, y, temp);
            } else {
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "TOP", "TOPRIGHT"};
                compareWithAround(x, y, temp);
            }
        }else if (x == width - 1) {
            if (y == 0) {
                String temp[] = {"TOP", "LEFT", "TOPLEFT"};
                compareWithAround(x, y, temp);
            } else if (y == height - 1) {
                String temp[] = {"BOTTOM", "LEFT", "BOTTOMLEFT"};
                compareWithAround(x, y, temp);
            } else {
                String temp[] = {"BOTTOM", "LEFT", "BOTTOMLEFT", "TOP", "TOPLEFT"};
                compareWithAround(x, y, temp);
            }
        } else {
            if (y == 0) {
                String temp[] = {"TOP", "RIGHT", "LEFT", "TOPLEFT", "TOPRIGHT"};
                compareWithAround(x, y, temp);
            } else if (y == height - 1) {
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "LEFT", "BOTTOMLEFT"};
                compareWithAround(x, y, temp);
            } else {
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "TOP", "TOPRIGHT", "LEFT", "TOPLEFT", "BOTTOMLEFT"};
                compareWithAround(x, y, temp);
            }
        }
    }

    //getting location info of comparing points and finding their coordinates then sending it to compareWithOne to get Height Difference info then doing some distribution.
    public static void compareWithAround(int x, int y, String[] directions) {
        Point[] noktalar = new Point[directions.length];
        double totalHeightDif = 1;
        for (int i = 0; i < directions.length; i++) {
            int comparedx = 0;
            int comparedy = 0;
            if (directions[i].equals("TOP")) {
                comparedx = x;
                comparedy = y + 1;
            }
            if (directions[i].equals("LEFT")) {
                comparedx = x - 1;
                comparedy = y;
            }
            if (directions[i].equals("RIGHT")) {
                comparedx = x + 1;
                comparedy = y;
            }
            if (directions[i].equals("BOTTOM")) {
                comparedx = x;
                comparedy = y - 1;
            }
            if (directions[i].equals("TOPLEFT")) {
                comparedx = x - 1;
                comparedy = y + 1;
            }
            if (directions[i].equals("TOPRIGHT")) {
                comparedx = x + 1;
                comparedy = y + 1;
            }
            if (directions[i].equals("BOTTOMLEFT")) {
                comparedx = x - 1;
                comparedy = y - 1;
            }
            if (directions[i].equals("BOTTOMRIGHT")) {
                comparedx = x + 1;
                comparedy = y - 1;
            }
            //System.out.println("sending to compare " + x + " " + y + " to " + comparedx + " " + comparedy);
            double tempDif = compareWithOne(x, y, comparedx, comparedy);
            //System.out.println("tempdif is " + tempDif);
            totalHeightDif += tempDif;
            noktalar[i] = new Point(comparedx, comparedy, tempDif);
        }
        double unitWaterToDistrubute = currentWater[x][y] / totalHeightDif;
        for (int i = 0; i < noktalar.length; i++) {
            nextDistributedWater[noktalar[i].x][noktalar[i].y] += (unitWaterToDistrubute)*(noktalar[i].heightdifference);
            //System.out.println("distributed water = " + nextDistributedWater[noktalar[i].x][noktalar[i].y]);
        }
        nextDistributedWater[x][y] += unitWaterToDistrubute;
    }

    //finding total height difference.
    //the total height difference is square rooted to make ditribution a little more stabilized. Otherwise all the water on a point can be distributed in a way that the point loses all
    //its water and send it to a point next to it. Then the water level on that point will get too high that it will send the water back. And it keeps doing the same thing.
    //even if i do it this way the program still sends the water back and forth and thats a problem.
    public static double compareWithOne(int x1, int y1, int x2, int y2) {
        //System.out.println("I will compare " + x1 + " and " + y1 + " to " + x2 + " and " + y2);
        double totalheightmain = topography[x1][y1]*5 + currentWater[x1][y1];
        double totalheightcomparing = topography[x2][y2]*5 + currentWater[x2][y2];
        if (totalheightmain >= totalheightcomparing) {
            //System.out.println("totmain" + totalheightmain + " totcomparing" + totalheightcomparing);
            return Math.sqrt(totalheightmain - totalheightcomparing) + 1;
        } else {
            //System.out.println("totmain" + totalheightmain + "totcomparing" + totalheightcomparing);
            return 0;
        }
    }

    public static void main(String[] args) {
        //determineTheTopograpgy();
        rainStart();
        for (;;) {
            try {
                //sending the actual Thread of execution to sleep X milliseconds
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
            printWaterSituation();
            distributeWaterStart();
            currentWater = nextDistributedWater; //storedWaterData is now the currentWater data since the round ends.
            nextDistributedWater = new double[width][height]; //storedWaterData is emptied and ready to use again.
        }
    }

}
