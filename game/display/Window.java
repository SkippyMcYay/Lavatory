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
import unclassified.gameLoop;

public class Window extends JFrame {
    private ImageIcon Impassable_Terrain=new ImageIcon(".\\game\\display\\Images\\Impassable_Terrain.PNG");
    private ImageIcon Passable_Terrain=new ImageIcon(".\\game\\display\\Images\\Passable_Terrain.PNG");
    private ImageIcon Thief=new ImageIcon(".\\game\\display\\Images\\Thief.PNG");
    private ImageIcon Archer=new ImageIcon(".\\game\\display\\Images\\Archer.PNG");
    private ImageIcon Warrior=new ImageIcon(".\\game\\display\\Images\\Warrior.PNG");

    private ImageIcon Tile_Highlight=new ImageIcon(".\\game\\display\\Images\\Tile_Template.png");
    private ImageIcon Movement_Highlight=new ImageIcon(".\\game\\display\\Images\\Move_Space.png");
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

    private gameLoop GameLoop;
    private board world;

    private int[][] highlightRange=new int[board_size_x][board_size_y];
    private Component[][] highlightedSpaces=new Component[board_size_x][board_size_y];

    public Window(gameLoop g){
        super("Tactical Game");
        //setLayout(new GridLayout(board_size_x,board_size_y));
        this.GameLoop = g;
        setLayout(null);
        setSize(window_x, window_y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonHandler();

    }
    public void update(board world){
        this.world=world;
        for(int coord_y=0;coord_y<board_size_y;coord_y++){
            for(int coord_x=0;coord_x<board_size_x;coord_x++){
                tileHandler(world,coord_x,coord_y);
                actorHandler(world,coord_x,coord_y);
                rangeHighlightHandler(coord_x,coord_y);
                highlightHandler();
                buttonHandler();
            }
        }
        setVisible(true);
    }
    public void setHighlightRange(int[][] spaces){
        clearHighlightRange();
        highlightRange=spaces;
        for(int coord_y=0;coord_y<board_size_y;coord_y++){
            for(int coord_x=0;coord_x<board_size_x;coord_x++){
                if (spaces[coord_x][coord_y]!=-1){
                    Component newSpace=new JLabel(Movement_Highlight);
                    highlightedSpaces[coord_x][coord_y]=newSpace;
                }
            }
        }
    }
    public void clearHighlightRange(){
        for(int coord_y=0;coord_y<board_size_y;coord_y++){
            for(int coord_x=0;coord_x<board_size_x;coord_x++){
                if (highlightedSpaces[coord_x][coord_y]!=null){
                    remove(highlightedSpaces[coord_x][coord_y]);
                }
            }
        }
        this.highlightedSpaces=new JLabel[board_size_x][board_size_y];
    }

    private class tileListener implements MouseListener{
        public void mouseEntered(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            tile_highlight.setLocation(coords.x,coords.y);

        }
        public void mouseExited(MouseEvent event){
            tile_highlight.setLocation(-1000,-1000);
        }
        public void mouseClicked(MouseEvent event){}
        public void mouseReleased(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            coords.x=(coords.x-battlefield_offset_x)/tile_dimension_x;
            coords.y=(coords.y-battlefield_offset_y)/tile_dimension_y;
            clearHighlightRange();
            GameLoop.tileParser(coords);
            update(world);
        }
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
        public void mouseClicked(MouseEvent event){}
        public void mouseReleased(MouseEvent event){
            GameLoop.buttonParser(event.getComponent().getName());
            update(world);
        }
        public void mousePressed(MouseEvent event){}
    }
    private void addComponent(Component component,int coord_x,int coord_y){
        int x=coord_x*tile_dimension_x+battlefield_offset_x;
        int y=coord_y*tile_dimension_y+battlefield_offset_y;

        add(component,0);
        component.setSize(tile_dimension_x,tile_dimension_y);
        component.setLocation(x,y);
    }
    private void tileHandler(board world,int coord_x,int coord_y){
        Component component=new JLabel();
        if(world.getTerrain(coord_x,coord_y)==0){
            component=new JLabel(Passable_Terrain);
        }else if(world.getTerrain(coord_x,coord_y)==1){
            component=new JLabel(Impassable_Terrain);
        }else{
            System.out.println("that terrain type not covered in Window.tileHandler");
        }
        component.addMouseListener(new tileListener());
        addComponent(component,coord_x,coord_y);

    }
    private void actorHandler(board world,int coord_x,int coord_y){
        if(world.getCombatantAt(coord_x, coord_y)!=null){
            Component Actor=new JLabel();
            if(world.getCombatantAt(coord_x,coord_y).getRole()=="thief"){
                Actor=new JLabel(Thief);
            }else if(world.getCombatantAt(coord_x,coord_y).getRole()=="warrior"){
                Actor=new JLabel(Warrior);
            }else if(world.getCombatantAt(coord_x,coord_y).getRole()=="archer"){
                Actor=new JLabel(Archer);
            }else{
                System.out.println("that is not a role: Window.actorHandler");
            }
            addComponent(Actor,coord_x,coord_y);
        }
    }
    private void highlightHandler(){
        add(tile_highlight,0);
        tile_highlight.setSize(tile_dimension_x,tile_dimension_y);
        tile_highlight.setLocation(-1000,-1000);
    }
    private void rangeHighlightHandler(int coord_x,int coord_y){
        if(highlightedSpaces[coord_x][coord_y]!=null){
            addComponent(highlightedSpaces[coord_x][coord_y],coord_x,coord_y);
        }
    }
    private void buttonHandler(){
        int space_between_buttons=(window_y/(board_size_x/4));

        int spacer=(window_y-space_between_buttons)/buttons.length;
        for(int i=0;i<buttons.length;i++){
            String name=buttons[i];
            Component button=new JLabel(Button_Highlight);
            button.setName(name);
            button.setSize(button.getPreferredSize());
            button.setLocation(battlefield_offset_x/2-button.getWidth()/2, spacer * i + (space_between_buttons / 2));
            button.addMouseListener(new buttonListener());



            Component text=new JLabel(name);
            text.setSize(text.getPreferredSize());
            text.setLocation(button.getWidth()/2-text.getWidth()/2+button.getLocation().x,
                    button.getHeight()/2-text.getHeight()/2+button.getLocation().y);
            add(text);
            add(button);

        }
    }

}