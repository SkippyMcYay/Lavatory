import entity.character;
import display.james_frame_test;

public class test{
    public static void main(String args[]){
        james_frame_test james=new james_frame_test();
        String image=".\\game\\display\\Images\\hampsterpunch.bmp";
        String title="Their world ends now!";
        james.CreateWindow(image,title);
    }
}