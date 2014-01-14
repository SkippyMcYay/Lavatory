package display;

import entity.board;
import unclassified.options;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Window extends JFrame {
    private ImageIcon Impassable_Terrain=new ImageIcon(".\\game\\display\\Images\\Impassable_Terrain.PNG");
    private ImageIcon Passable_Terrain=new ImageIcon(".\\game\\display\\Images\\Passable_Terrain.PNG");
    private ImageIcon Actor=new ImageIcon(".\\game\\display\\Images\\Archer.PNG");
    private ImageIcon Tile_Highlight=new ImageIcon(".\\game\\display\\Images\\Tile_Template.png");
    private ImageIcon Button_Highlight=new ImageIcon(".\\game\\display\\Images\\Button_Highlight.png");

    private int tile_dimension_x=options.sprite_dimension_x;
    private int tile_dimension_y=options.sprite_dimension_y;

    private int board_size_x=options.board_size_x;
    private int board_size_y=options.board_size_y;

    private static int window_x=options.window_x;
    private static int window_y=options.window_y;

    private int battlefield_offset_x=options.battlefield_offset_x;
    private int battlefield_offset_y=options.battlefield_offset_y;

    private String[] buttons=options.buttons;

    private Component tile_highlight=new JLabel(Tile_Highlight);
    private Component button_highlight=new JLabel(Button_Highlight);

    public Window(){
        super("Tactical Game");
        //setLayout(new GridLayout(board_size_x,board_size_y));
        setLayout(null);
        setSize(window_x, window_y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonHandler();
    }
    public void update(board world){
        addActor(Tile_Highlight,-10,-10);
        int coord_x,coord_y;
        for(coord_y=0;coord_y<board_size_y;coord_y++){
            for(coord_x=0;coord_x<board_size_x;coord_x++){
                actorHandler(world,coord_x,coord_y);
                tileHandler(world,coord_x,coord_y);
                highlightHandler();
                buttonHandler();
            }
        }
        setVisible(true);
    }
    private class tileListener implements MouseListener{
        public void mouseEntered(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            tile_highlight.setLocation(coords.x,coords.y);

        }
        public void mouseExited(MouseEvent event){
            tile_highlight.setLocation(-1000,-1000);
        }
        public void mouseClicked(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            coords.x=(coords.x-battlefield_offset_x)/tile_dimension_x;
            coords.y=(coords.y-battlefield_offset_y)/tile_dimension_y;
            System.out.println(coords);

        }
        public void mouseReleased(MouseEvent event){}
        public void mousePressed(MouseEvent event){}
    }
    private class buttonListener implements MouseListener{
        public void mouseEntered(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            button_highlight.setLocation(coords.x-1,coords.y-1);

        }
        public void mouseExited(MouseEvent event){
            button_highlight.setLocation(-1000,-1000);
        }
        public void mouseClicked(MouseEvent event){

            System.out.println(event.getComponent().getName());

        }
        public void mouseReleased(MouseEvent event){}
        public void mousePressed(MouseEvent event){}
    }
    private void addActor(ImageIcon image,int coord_x,int coord_y){
        int x=coord_x*tile_dimension_x+battlefield_offset_x;
        int y=coord_y*tile_dimension_y+battlefield_offset_y;
        JLabel Cur_Actor=new JLabel(image);

        add(Cur_Actor,0);
        Cur_Actor.setSize(tile_dimension_x,tile_dimension_y);
        Cur_Actor.setLocation(x,y);
    }
    private void addTile(ImageIcon image,int coord_x,int coord_y){
        int x=coord_x*tile_dimension_x+battlefield_offset_x;
        int y=coord_y*tile_dimension_y+battlefield_offset_y;
        tileListener listener=new tileListener();
        Component Cur_Terrain=new JLabel(image);

        Cur_Terrain.addMouseListener(listener);
        add(Cur_Terrain,-1);
        Cur_Terrain.setSize(tile_dimension_x,tile_dimension_y);
        Cur_Terrain.setLocation(x,y);
    }
    private void tileHandler(board world,int coord_x,int coord_y){
        if(world.getTerrain(coord_x,coord_y)==0){
            addTile(Passable_Terrain,coord_x,coord_y);
        }else if(world.getTerrain(coord_x,coord_y)==1){
            addTile(Impassable_Terrain,coord_x,coord_y);
        }else{
            System.out.println("that terrain type not covered in Window.tileHandler");
        }
    }
    private void actorHandler(board world,int coord_x,int coord_y){
        if(world.getCombatantAt(coord_x, coord_y)!=null){
            //actor parser code goes here
            addActor(Actor,coord_x,coord_y);
        }
    }
    private void highlightHandler(){
        add(tile_highlight,0);
        tile_highlight.setSize(tile_dimension_x,tile_dimension_y);
        tile_highlight.setLocation(-1000,-1000);
    }
    private void buttonHandler(){
        int magical_button_spacer_number=(window_y/(board_size_x/4));

        int spacer=(window_y-magical_button_spacer_number)/buttons.length;
        for(int i=0;i<buttons.length;i++){
            String name=buttons[i];
            Component foo=new JLabel(Button_Highlight);
            foo.setName(name);
            foo.setSize(foo.getPreferredSize());
            foo.setLocation(battlefield_offset_x/2-foo.getWidth()/2, spacer * i + (magical_button_spacer_number / 2));
            foo.addMouseListener(new buttonListener());



            Component text=new JLabel(name);
            text.setSize(text.getPreferredSize());
            text.setLocation(foo.getWidth()/2-text.getWidth()/2+foo.getLocation().x,
                            foo.getHeight()/2-text.getHeight()/2+foo.getLocation().y);
            add(text);
            add(foo);

        }
    }
}