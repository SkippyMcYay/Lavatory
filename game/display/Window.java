package display;

import entity.board;
import unclassified.options;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Window extends JFrame {
    ImageIcon Impassable_Terrain=new ImageIcon(".\\game\\display\\Images\\Impassable_Terrain.PNG");
    ImageIcon Passable_Terrain=new ImageIcon(".\\game\\display\\Images\\Passable_Terrain.PNG");
    ImageIcon Actor=new ImageIcon(".\\game\\display\\Images\\Actor.PNG");
    int tile_dimension_x=new options().sprite_dimension_x;
    int tile_dimension_y=new options().sprite_dimension_y;

    int board_size_x=new options().board_size_x;
    int board_size_y=new options().board_size_y;


    public Window(board world){
        super("Tactical Game");
        //setLayout(new GridLayout(board_size_x,board_size_y));
        setLayout(null);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int i,j;
        Component Cur_Terrain=null;
        Component Cur_Actor;
        for(j=0;j<board_size_y;j++){
            for(i=0;i<board_size_x;i++){
                if(world.queryLocation(i,j)!=-1){
                    Cur_Actor=new JLabel(Actor);
                    add(Cur_Actor);
                    Cur_Actor.setSize(tile_dimension_x,tile_dimension_y);
                    Cur_Actor.setLocation(i*tile_dimension_x,j*tile_dimension_y);
                }
                if(world.getTerrain(i,j)==0){
                    Cur_Terrain=new JLabel(Passable_Terrain);
                }else if(world.getTerrain(i,j)==1){
                    Cur_Terrain=new JLabel(Impassable_Terrain);
                }else{
                    System.out.println("game's gonna break, that terrain type not covered in Window");
                }

                add(Cur_Terrain);
                Cur_Terrain.setSize(tile_dimension_x,tile_dimension_y);
                Cur_Terrain.setLocation(i*tile_dimension_x,j*tile_dimension_y);

//                if(world.queryLocation(i,j)!=-1){
//                    Cur_Actor=new JLabel(Actor);
//                    add(Cur_Actor);
//                    Cur_Actor.setSize(tile_dimension_x,tile_dimension_y);
//                    Cur_Actor.setLocation(i*tile_dimension_x,j*tile_dimension_y);
//                }

            }
        }
        //window.setSize(300, 400);
        //pack();
        setVisible(true);
    }
}