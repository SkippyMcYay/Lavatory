// to access a file the string should read something like ".\\game\\folder\\file.java"
import entity.*;
import java.awt.Point;
import display.Window;

public class test{
    public static void main(String args[]){

        board world=new board();

        for (int i=0; i<5; i++){
            world.setTerrain(i, 4, 1);
        }


        Point position = new Point(2,2);
        actor person=new actor("james",position);
        person.setFaction("Blueberry Destroyers");
        world.addCharacter(person);

        Point position2 = new Point(2,1);
        actor someone=new actor("Indestructible James",position2);
        someone.setFaction("Inundefeatedable");
        world.addCharacter(someone);

        Point position3 = new Point(4,2);
        actor dude=new actor("McDuggleton",position3);
        dude.setFaction("Blueberry Destroyers");
        world.addCharacter(dude);
        int turnout[][]=world.getMoveRange(world.getCharacter(0));

        Window window=new Window(world);
        world.displayBoard();
        world.displayMoveRange(turnout);


    }
}