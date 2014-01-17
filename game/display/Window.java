package display;

import entity.board;
import unclassified.options;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

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

    private gameLoop GameLoop;
    private board world;

    private Component[][] highlightedSpaces=new Component[board_size_x][board_size_y];

    private JPanel Tile_Layer=new JPanel();
    private JPanel Actor_Layer=new JPanel();
    private JPanel Range_Layer=new JPanel();
    private JPanel Highlight_Layer=new JPanel();
    private JPanel Button_Layer=new JPanel();


    public Window(gameLoop g){
        super("Tactical Game");
        //setLayout(new GridLayout(board_size_x,board_size_y));
        this.GameLoop = g;
        setLayout(null);
        setSize(window_x, window_y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonHandler();
        Tile_Layer.setLayout(null);
        Actor_Layer.setLayout(null);
        Range_Layer.setLayout(null);
        Highlight_Layer.setLayout(null);
        Button_Layer.setLayout(null);
        Tile_Layer.setSize(tile_dimension_x * board_size_x, tile_dimension_y * board_size_y);
        Actor_Layer.setSize(tile_dimension_x * board_size_x, tile_dimension_y * board_size_y);
        Range_Layer.setSize(tile_dimension_x * board_size_x, tile_dimension_y * board_size_y);
        Highlight_Layer.setSize(tile_dimension_x * board_size_x, tile_dimension_y * board_size_y);
        Button_Layer.setSize(battlefield_offset_x, window_y);
        Tile_Layer.setBackground(new Color(0,0,0,0));
        Actor_Layer.setBackground(new Color(0,0,0,0));
        Range_Layer.setBackground(new Color(0,0,0,0));
        Highlight_Layer.setBackground(new Color(0,0,0,0));
        Button_Layer.setBackground(new Color(0,0,0,0));
        Tile_Layer.setLocation(battlefield_offset_x, battlefield_offset_y);
        Actor_Layer.setLocation(battlefield_offset_x, battlefield_offset_y);
        Range_Layer.setLocation(battlefield_offset_x, battlefield_offset_y);
        Highlight_Layer.setLocation(battlefield_offset_x,battlefield_offset_y);
        add(Button_Layer);
        highlightHandler();
        tile_highlight.setSize(tile_dimension_x,tile_dimension_y);
        Highlight_Layer.add(tile_highlight);
        add(Highlight_Layer);
        add(Range_Layer);
        add(Actor_Layer);
        add(Tile_Layer);

    }
    public void update(board world){
        this.world=world;
        clearAllLayers();
        buttonHandler();
        //highlightHandler();
        for(int coord_y=0;coord_y<board_size_y;coord_y++){
            for(int coord_x=0;coord_x<board_size_x;coord_x++){
                rangeHighlightHandler(coord_x,coord_y);
                actorHandler(coord_x,coord_y);
                tileHandler(coord_x,coord_y);


            }
        }
//        add(Button_Layer);
//        add(Highlight_Layer);
//        add(Range_Layer);
//        add(Actor_Layer);
//        add(Tile_Layer);
        setVisible(true);
    }
    public void setHighlightRange(int[][] spaces){
        clearHighlightRange();
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
            update(world);
        }
        public void mouseClicked(MouseEvent event){}
        public void mouseReleased(MouseEvent event){
            Point coords=event.getComponent().getLocation();
            coords.x=coords.x/tile_dimension_x;
            coords.y=coords.y/tile_dimension_y;
            clearHighlightRange();
            GameLoop.tileParser(coords);
            update(world);
        }
        public void mousePressed(MouseEvent event){}
    }
    private class buttonListener implements MouseListener{
        public void mouseEntered(MouseEvent event){}
        public void mouseExited(MouseEvent event){        }
        public void mouseClicked(MouseEvent event){}
        public void mouseReleased(MouseEvent event){
            GameLoop.buttonParser(event.getComponent().getName());
            update(world);

        }
        public void mousePressed(MouseEvent event){}
    }
    private void addCompo(Component component,int coord_x,int coord_y,JPanel layer){
        int x=coord_x*tile_dimension_x;
        int y=coord_y*tile_dimension_y;

        layer.add(component,0);
        component.setSize(tile_dimension_x,tile_dimension_y);
        component.setLocation(x,y);
    }
    private void tileHandler(int coord_x,int coord_y){
        Component component=new JLabel();
        if(world.getTerrain(coord_x,coord_y)==0){
            component=new JLabel(Passable_Terrain);
        }else if(world.getTerrain(coord_x,coord_y)==1){
            component=new JLabel(Impassable_Terrain);
        }else{
            System.out.println("that terrain type not covered in Window.tileHandler");
        }
        component.addMouseListener(new tileListener());
        addCompo(component,coord_x,coord_y,Tile_Layer);

    }
    private void actorHandler(int coord_x,int coord_y){
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
            addCompo(Actor,coord_x,coord_y,Actor_Layer);
        }
    }
    private void clearAllLayers(){

//        remove(Tile_Layer);
//        remove(Actor_Layer);
//        remove(Range_Layer);
//        remove(Button_Layer);
        Tile_Layer.removeAll();
        Actor_Layer.removeAll();
        Range_Layer.removeAll();
        Button_Layer.removeAll();
        Tile_Layer.repaint();
        Actor_Layer.repaint();
        Range_Layer.repaint();
        Button_Layer.repaint();
        Highlight_Layer.repaint();
        repaint();
    }
    private void highlightHandler(){
        //tile_highlight.setSize(tile_dimension_x,tile_dimension_y);
        tile_highlight.setLocation(-1000,-1000);
    }
    private void rangeHighlightHandler(int coord_x,int coord_y){
        if(highlightedSpaces[coord_x][coord_y]!=null){
            addCompo(highlightedSpaces[coord_x][coord_y],coord_x,coord_y,Range_Layer);
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
            Button_Layer.add(text);
            Button_Layer.add(button);

        }
    }

}