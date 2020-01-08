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
public class Water {
    
    private int x;
    private int y;
    private int waterHeight;
    private boolean gotAnyWater = false;
    
    Water(int x, int y, int waterHeight){
        this.x = x;
        this.y = y;
        this.waterHeight = waterHeight;
        if(waterHeight > 0){
            gotAnyWater = true;
        }
    }
    
    public void addWater(){
        waterHeight++;
        gotAnyWater = true;
    }
    
    public void removeWater(){
        if(--waterHeight == 0){
            gotAnyWater = false;
        }
    } 
    
    public int getWaterHeight(){
        return waterHeight;
    }
    
    public boolean gotAnyWater(){
        return gotAnyWater;
    }
}
