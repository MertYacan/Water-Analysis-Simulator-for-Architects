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
public class waterArray {
    
    int height;
    int width;
    Water[][] waterArray;
    
    waterArray(int width, int height){
        this.height = height;
        this.width = width;
        waterArray = new Water[width][height];
    }
    
    public int getHeight(int x, int y){
        return waterArray[x][y].getWaterHeight();
    }
    
    public void addWater(int x, int y){
        waterArray[x][y].addWater();
    }
    
    public void removeWater(int x, int y){
        waterArray[x][y].removeWater();
    }
    
}
