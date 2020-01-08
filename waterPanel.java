/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainwater.analysis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static rainwater.analysis.RWAnalysisAPP.window;

/**
 *
 * @author Mert Yacan
 */
public class waterPanel extends JPanel {

    int height;
    int width;
    Water[][] waterArray;
    int wSize;
    int hSize;
    boolean isReady = false;

    waterPanel(int wSize, int hSize, int width, int height) {
        super();
        this.setSize(wSize, hSize);
        Water[][] waterArray = new Water[width][height];
        this.height = height;
        this.width = width;
        this.wSize = wSize;
        this.hSize = hSize;
        setBackground(Color.WHITE);
        this.setLayout(null);
    }

    public void addCrazyShapesInsýde(Water[][] waterArray) {
        this.waterArray = waterArray;
        isReady = true;
    }
    
    //this method returns a color according to the water level. If water level is higher then the color gets closer to navy blue. no water == dark gray. some water = turquoise
    public Color getAColor(int x){
        if(x == 0){
            return Color.DARK_GRAY;
        }else{
            int b = x;
            if(x>=7){
                b = 7;
            }
            int t = 255 - b*30;
            Color temp = new Color(0,t,255);
            return temp;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isReady) {
            this.removeAll();
            for (int j = height-1; j >= 0; j--) {
                for (int i = 0; i < width; i++) {
                    Graphics2D ga = (Graphics2D) g;
                    ga.setPaint(getAColor(waterArray[i][j].getWaterHeight()));
                    int horizontalSize = ((wSize-((width+1)*3))/width);
                    int verticalSize = ((hSize-((height+1)*3))/height);
                    ga.fillRect(3+((horizontalSize+3)*i), 3 + ((verticalSize+3)*j), horizontalSize, verticalSize);
                    if(waterArray[i][j].getWaterHeight()>0){
                        ga.setPaint(Color.BLACK);
                        Font stringFont = new Font( "Montserrat Thin", Font.PLAIN, 18 );
                        ga.setFont(stringFont);
                        String temp = ""+waterArray[i][j].getWaterHeight();
                        ga.drawString(temp, 10+((horizontalSize+3)*i), 20 + ((verticalSize+3)*j));
                    }
                }
            }
        }
    }
    
    
    public static void main(String[] args){
        waterPanel deneme = new waterPanel(800,800,5,5);
        window = new JFrame("Rainwater Analysis");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(deneme);
        
        Water[][] dene = new Water[5][5];
        for(int j = 0; j<5; j++){
            for(int i = 0; i<5; i++){
                dene[i][j] = new Water(i,j,1);
           }
        }
        dene[3][2].addWater();
        deneme.addCrazyShapesInsýde(dene);
        deneme.setSize(800, 800);
        
        window.pack();
	window.setVisible(true);
    }
}
