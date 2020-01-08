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
public class RainwaterAnalysis2 {
    private int width = 10; //adjustable map size
    private int height = 10;
    private int[][] absorbation = new int[width][height]; //not used yet
    private int rainSpeed = 100; //variable to adjust water to be added
    public Water[][] waterArray;
    Topography topo;
    
    RainwaterAnalysis2(int width, int height){
        this.width = width;
        this.height = height;
        waterArray = new Water[width][height];
        topo = new Topography(width,height);
        rainIt();
    }
    
    //add some random water to everywhere
    public void startIt(){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                waterArray[i][j] = new Water(i,j,0);
            }
        }
    }
    
    //add some random water to everywhere
    public void rainIt(){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                waterArray[i][j] = new Water(i,j,1);
            }
        }
        for(int i = 0; i<rainSpeed; i++){
            int randomx = (int) (Math.random()*(width));
            int randomy = (int) (Math.random()*(height));
            waterArray[randomx][randomy].addWater();
        }
    }
    
    //remove water from x,y add some water to the x2,y2
    public void transferWater(int x, int y, int x2, int y2){
        waterArray[x][y].removeWater();
        waterArray[x2][y2].addWater();
    }
    
    //prints the water situation to screen
    public Water[][] printWaterSituation() {
        distributeWaterStart();
        return waterArray;
        //System.out.println("----------");
        //for (int j = height - 1; j > -1; j--) {
        //    for (int i = 0; i < width; i++) {
        //        System.out.print(waterArray[i][j].getWaterHeight() + " ");
        //    }
        //   System.out.print("\n");
        //}
    }
    
    public Topography getTopo(){
        return topo;
    }
    
    //for every water point which has some water, it call the distributeWater method and
    //it transfers one unit of water to the direction it gets as message back.
    //if the message is nowhere it just doesnt do anything
    public void distributeWaterStart(){
        for (int j = height - 1; j > -1; j--) {
            for (int i = 0; i < width; i++) {
                if(waterArray[i][j].gotAnyWater()){
                    String anyLowerPlace = distributeWater(i,j);
                    switch(anyLowerPlace){
                        case "NOWHERE":
                            break;
                        case "TOP":
                            transferWater(i,j,i,j+1);
                            break;
                        case "BOTTOM":
                            transferWater(i,j,i,j-1);
                            break;
                        case "LEFT":
                            transferWater(i,j,i-1,j);
                            break;
                        case "RIGHT":
                            transferWater(i,j,i+1,j);
                            break;
                        case "TOPLEFT":
                            transferWater(i,j,i-1,j+1);
                            break;
                        case "TOPRIGHT":
                            transferWater(i,j,i+1,j+1);
                            break;
                        case "BOTTOMLEFT":
                            transferWater(i,j,i-1,j-1);
                            break;
                        case "BOTTOMRIGHT":
                            transferWater(i,j,i+1,j-1);
                            break;
                    }
                }
            }
        }
    }
    
    //A method returns one of the positive values in an array.
    //As value in the method gets bigger, there is bigger chance that it will be picked.
    //This method decides where the water will flow.
    //if no positive values then it sends arrayIn.length which represents NOWHERE in the distributeWater method.
    public int pickOneOfPositiveOne(int[] arrayIn){
        double tempValue = 0.0;
        int tempKey = 0;
        for(int i = 0; i<arrayIn.length; i++){
            if(arrayIn[i]<=0){
                continue;
            }
            double addSomeRandomness = Math.abs(arrayIn[i]*(Math.random()*10));
            if(addSomeRandomness>tempValue){
                tempValue = addSomeRandomness;
                tempKey = i;
            }
        }
        if(tempValue<=0){
            return arrayIn.length;
        }
        return tempKey;
    }
    
    //A method gets the total height of the point, which is topo height + water height
    public int getHeight(int x, int y){
        return topo.get(x, y)+waterArray[x][y].getWaterHeight();
    }
    
    //So many code lines but it just picks a place to send the water if there is a place around with lower height than the x,y
    //If there is no place with lower height then this method sends back "NOWHERE" message
    public String distributeWater(int x, int y) {
        if (x == 0) {
            if (y == 0) {
                int point = getHeight(x,y);
                int top = point - getHeight(x,y+1);
                int right = point - getHeight(x+1,y);
                int topright = point - getHeight(x+1,y+1);
                int[] array = {top,right,topright};
                String temp[] = {"TOP", "RIGHT", "TOPRIGHT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else if (y == height - 1) {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int right = point - getHeight(x+1,y);
                int botright = point - getHeight(x+1,y-1);
                int[] array = {bot,right,botright};
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int right = point - getHeight(x+1,y);
                int top = point - getHeight(x,y+1);
                int topright = point - getHeight(x+1,y+1);
                int botright = point - getHeight(x+1,y-1);
                int[] array = {bot,right,botright,top,topright};
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "TOP", "TOPRIGHT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            }
        }else if (x == width - 1) {
            if (y == 0) {
                int point = getHeight(x,y);
                int top = point - getHeight(x,y+1);
                int left = point - getHeight(x-1,y);
                int topleft = point - getHeight(x-1,y+1);
                int[] array = {top,left,topleft};
                String temp[] = {"TOP", "LEFT", "TOPLEFT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else if (y == height - 1) {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int left = point - getHeight(x-1,y);
                int botleft = point - getHeight(x-1,y-1);
                int[] array = {bot,left,botleft};
                String temp[] = {"BOTTOM", "LEFT", "BOTTOMLEFT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int left = point - getHeight(x-1,y);
                int top = point - getHeight(x,y+1);
                int topleft = point - getHeight(x-1,y+1);
                int botleft = point - getHeight(x-1,y-1);
                int[] array = {bot,left,botleft,top,topleft};
                String temp[] = {"BOTTOM", "LEFT", "BOTTOMLEFT", "TOP", "TOPLEFT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            }
        } else {
            if (y == 0) {
                int point = getHeight(x,y);
                int left = point - getHeight(x-1,y);
                int top = point - getHeight(x,y+1);
                int right = point - getHeight(x+1,y);
                int topleft = point - getHeight(x-1,y+1);
                int topright = point - getHeight(x+1,y+1);
                int[] array = {top,right,left,topleft,topright};
                String temp[] = {"TOP", "RIGHT", "LEFT", "TOPLEFT", "TOPRIGHT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else if (y == height - 1) {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int left = point - getHeight(x-1,y);
                int right = point - getHeight(x+1,y);
                int botleft = point - getHeight(x-1,y-1);
                int botright = point - getHeight(x+1,y-1);
                int[] array = {bot,right,botright,left,botleft};
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "LEFT", "BOTTOMLEFT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            } else {
                int point = getHeight(x,y);
                int bot = point - getHeight(x,y-1);
                int left = point - getHeight(x-1,y);
                int top = point - getHeight(x,y+1);
                int right = point - getHeight(x+1,y);
                int topleft = point - getHeight(x-1,y+1);
                int botleft = point - getHeight(x-1,y-1);
                int topright = point - getHeight(x+1,y+1);
                int botright = point - getHeight(x+1,y-1);
                int[] array = {bot,right,botright,top,topright,left,topleft,botleft};
                String temp[] = {"BOTTOM", "RIGHT", "BOTTOMRIGHT", "TOP", "TOPRIGHT", "LEFT", "TOPLEFT", "BOTTOMLEFT","NOWHERE"};
                return temp[pickOneOfPositiveOne(array)];
            }
        }
    }
    
    
    
    public static void main(String[] args) {
    }
    
}
