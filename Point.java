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
public class Point {
    public int x;
    public int y;
    public double heightdifference;
       
    Point(int x, int y, double heightdifference){
        this.x = x;
        this.y = y;
        this.heightdifference = heightdifference;
    }
}
