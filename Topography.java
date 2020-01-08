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
public class Topography {
    
    private static int width;
    private static int height;
    private static int[][] topography;
    
    Topography(int t){
        switch(t){
            case 0: 
                break;
            case 1:
                
                break;
            case 2:
            case 3:
        }
    }
    
    //random topo constructor
    Topography(int width, int height){
        this.width = width;
        this.height = height;
        topography = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int randomtopo = (int) (1 + (Math.random() * 12));
                topography[i][j] = randomtopo;
            }
        }
    }
    
    public int get(int x, int y){
        return topography[x][y];
    }
    
    
}
