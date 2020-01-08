/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainwater.analysis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mert Yacan
 */
public class RWAnalysisAPP implements ActionListener{
    
    int width;
    int height;
    static JFrame window;
    static waterPanel waterP;
    static topographyPanel topographyP;
    static JPanel screen, button;
    static JButton go;
    static JLabel yazi, bg;
    
    RWAnalysisAPP(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public void start(){
        ImageIcon bgimg = new ImageIcon("C:\\Users\\Mert Yacan\\Desktop\\Mert\\Courses\\594\\FinalWork\\bg.jpg");
        bg = new JLabel(bgimg);
        bg.setBounds(0, 0, bgimg.getIconWidth(), bgimg.getIconHeight());
        //lets create topograhy
        window = new JFrame("Rainwater Analysis");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen = new JPanel();
        screen.setLayout(null);
        screen.setSize(900,900);
        waterP = new waterPanel(353,353,width,height);
        topographyP = new topographyPanel(353,353,width,height);
        
        screen.add(waterP);
        screen.add(topographyP);
        screen.add(bg);
        
        waterP.setLocation(45, 110);
        topographyP.setLocation(447, 110);
        go = new JButton("Create shapes!");
        go.setSize(300,50);
        go.setLocation(300, 850);
	go.addActionListener(this);
        yazi = new JLabel("yazi");
        yazi.setPreferredSize(new Dimension(200,30));
        screen.setVisible(true);
        
        
        window.add(screen);
        
        window.setSize(860, 550);
	window.setVisible(true);
    }
    
    public void paintArrayIn(Water[][] waterArray, Topography topo){
        for (int j = height - 1; j > -1; j--) {
            for (int i = 0; i < width; i++) {
                waterP.addCrazyShapesInsýde(waterArray);
                topographyP.addTopoInside(topo);
            }
        }
        
        window.repaint();
    }
    
    public void actionPerformed(ActionEvent event) {
        window.repaint();
    }
    
    public static void main(String[] args){
        RWAnalysisAPP app = new RWAnalysisAPP(10,10);
        app.start();
        RainwaterAnalysis2 analiz = new RainwaterAnalysis2(10,10);
        for (;;) {
            try {
                //sending the actual Thread of execution to sleep X milliseconds
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
            app.paintArrayIn(analiz.printWaterSituation(), analiz.getTopo());
        }
        
    }
    
}
