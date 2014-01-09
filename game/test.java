// to access a file the string should read something like ".\\game\\folder\\file.java"
import entity.*;
import java.awt.Point;
import display.Window;

public class test{
    public static void main(String args[]){

        board world=new board();
        Window window=new Window();

        for (int i=0; i<5; i++){
            world.setTerrain(i, 4, 1);
        }


        Point position = new Point(2,2);
        actor person=new actor("james", "thief", position);
        person.setFaction(0);
        world.addCharacter(person);

        Point position2 = new Point(4,4);
        actor someone=new actor("Indestructible James", "archer", position2);
        someone.setFaction(1);
        world.addCharacter(someone);

        Point position3 = new Point(4,2);
        actor dude=new actor("McDuggleton", "warrior", position3);
        dude.setFaction(0);
        world.addCharacter(dude);
        int turnout[][]=world.getMoveRange(world.getCombatant(0));

        world.displayMoveRange(turnout);

        person.displayStats();
        someone.displayStats();
        dude.displayStats();

        window.update(world);



    }
}