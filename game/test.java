// to access a file the string should read something like ".\\game\\folder\\file.java"
import entity.*;

public class test{
    public static void main(String args[]){
        board world=new board();

        int position[]={2,2};
        character person=new character("james",position);
        world.addCharacter(person);

        int position2[]={2,1};
        character someone=new character("Indestructible James",position2);
        world.addCharacter(someone);

        boolean turnout[][]=world.getMoveRange(position,2);

        world.displayMoveRange(turnout);


    }
}