package display;
/*
This file will be used to make a frame,and paste an image to it
 */
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class james_frame_test {
    public static void CreateWindow(String image,String title){
        JFrame window=new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(image)))));
        }catch(IOException e){
            System.out.println(e);
        }

        //window.setSize(300, 400);
        window.pack();
        window.setVisible(true);
    }
}