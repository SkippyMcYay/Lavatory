// to access a file the string should read something like ".\\game\\folder\\file.java"
import entity.*;

public class test{
    public static void main(String args[]){
        character person=new character("james");
        board world=new board();

        world.displayBoard();
        world.addCharacter(person);
        world.displayBoard();

        character someone=new character("Indestructible James");

        world.removeCharacter(someone);
    }
}