package unclassified;

//NOTE: All options should be static, so you don't have to create an instance of options to use them. See board.java for example.

public class options {
    public static int board_size_x=8;
    public static int board_size_y=8;
    public static int max_combatants=8;
    public static int ap_gain_per_turn=10;

    public static int window_x=800;
    public static int window_y=600;

    public static int sprite_dimension_x=60;
    public static int sprite_dimension_y=60;


    public static int battlefield_offset_x=window_x/2-sprite_dimension_x*board_size_x/2;
    public static int battlefield_offset_y=window_y/2-sprite_dimension_y*board_size_y/2;

    public static String[] buttons={"move","weak attack","strong attack","defend","delay","end turn"};

    public static int weak_attack_AP_cost = 40;
    public static int strong_attack_AP_cost = 60;
    public static int defend_AP_cost = 50;
    public static int weak_attack_value = 0;
    public static int strong_attack_value = 4;
    public static float preaction_multiplier = 1.5f;

}
