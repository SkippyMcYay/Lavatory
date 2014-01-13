package unclassified;

public enum stat {
    MAX_HP(0),
    CUR_HP(1),
    MAX_MP(2),
    CUR_MP(3),
    MAX_AP(4),
    CUR_AP(5),
    AP_FOR_TURN(6),
    MOVE_AP_COST(7),
    ATTACK(8),
    ATTACK_RANGE(9),
    DEFENSE(10);

    private int id;
    public static int total_stats = 11;

    stat(int statId){
        this.id = statId;
    }

    public int getId(){
        return this.id;
    }

}