// to access a file the string should read something like ".\\game\\display\\file.java"
import entity.character;
public class test{
    public static void main(String args[]){
        character james=new character();
        int new_position[] = {2,0};
        james.setPos(new_position);
        System.out.println(james.getPos()[0]);
        System.out.println(james.getPos()[1]);

    }
}