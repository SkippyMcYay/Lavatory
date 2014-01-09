package display;

import entity.board;
import unclassified.options;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Window extends JFrame {
    private ImageIcon Impassable_Terrain=new ImageIcon(".\\game\\display\\Images\\Impassable_Terrain.PNG");
    private ImageIcon Passable_Terrain=new ImageIcon(".\\game\\display\\Images\\Passable_Terrain.PNG");
    private ImageIcon Actor=new ImageIcon(".\\game\\display\\Images\\Actor.PNG");

    private int tile_dimension_x=options.sprite_dimension_x;
    private int tile_dimension_y=options.sprite_dimension_y;

    private int board_size_x=options.board_size_x;
    private int board_size_y=options.board_size_y;

    private int window_x=options.window_x;
    private int window_y=options.window_y;

    private int battlefield_offset_x=options.battlefield_offset_x;
    private int battlefield_offset_y=options.battlefield_offset_y;


    public Window(){
        super("Tactical Game");
        //setLayout(new GridLayout(board_size_x,board_size_y));
        setLayout(null);
        setSize(window_x,window_y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void update(board world){
        int i,j;
        Component Cur_Terrain=null;
        Component Cur_Actor;
        for(j=0;j<board_size_y;j++){
            for(i=0;i<board_size_x;i++){
                int x=i*tile_dimension_x+battlefield_offset_x;
                int y=j*tile_dimension_y+battlefield_offset_y;
                if(world.queryLocation(i,j)!=-1){
                    Cur_Actor=new JLabel(Actor);
                    add(Cur_Actor);
                    Cur_Actor.setSize(tile_dimension_x,tile_dimension_y);
                    Cur_Actor.setLocation(x,y);
                }
                if(world.getTerrain(i,j)==0){
                    Cur_Terrain=new JLabel(Passable_Terrain);
                }else if(world.getTerrain(i,j)==1){
                    Cur_Terrain=new JLabel(Impassable_Terrain);
                }else{
                    System.out.println("game's gonna break, that terrain type not covered in Window.update");
                }

                add(Cur_Terrain);
                Cur_Terrain.setSize(tile_dimension_x,tile_dimension_y);
                Cur_Terrain.setLocation(x,y);
            }
        }
        setVisible(true);
    }
}