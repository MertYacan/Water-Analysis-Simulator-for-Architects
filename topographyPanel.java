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
public class topographyPanel extends JPanel {

    int height;
    int width;
    Topography topo;
    int wSize;
    int hSize;
    boolean isReady = false;

    topographyPanel(int wSize, int hSize, int width, int height) {
        super();
        this.setSize(wSize, hSize);
        topo = new Topography(width,height);
        this.height = height;
        this.width = width;
        this.wSize = wSize;
        this.hSize = hSize;
        setBackground(Color.WHITE);
        this.setLayout(null);
    }

    public void addTopoInside(Topography topo) {
        this.topo = topo;
        isReady = true;
    }
    
    //this method returns a color according to the water level. If water level is higher then the color gets closer to navy blue. no water == dark gray. some water = turquoise
    public Color getAColor(int x){
        if(x == 0){
            return Color.DARK_GRAY;
        }else{
            int b = x;
            if(x>=9){
                b = 7;
            }
            int t = 180 - b*10;
            Color temp = new Color(t,t-40,t-40);
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
                    ga.setPaint(getAColor(topo.get(i, j)));
                    int horizontalSize = ((wSize-((width+1)*3))/width);
                    int verticalSize = ((hSize-((height+1)*3))/height);
                    ga.fillRect(3+((horizontalSize+3)*i), 3 + ((verticalSize+3)*j), horizontalSize, verticalSize);
                    ga.setPaint(Color.BLACK);
                    Font stringFont = new Font( "Montserrat Thin", Font.PLAIN, 18 );
                    ga.setFont(stringFont);
                    String temp = ""+topo.get(i, j);
                    ga.drawString(temp, 10+((horizontalSize+3)*i), 20 + ((verticalSize+3)*j));
                }
            }
        }
    }
    
    
    public static void main(String[] args){
    }
}
