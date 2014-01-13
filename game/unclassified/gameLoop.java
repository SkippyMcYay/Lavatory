package unclassified;

import entity.*;
import static unclassified.stat.*;

public class gameLoop {
    public gameLoop(board brd){
        this.Board = brd;
        for (actor dude:this.Board.getCombatants()){
            dude.setStat(CUR_AP, 0);
            dude.setWaitTime(-1);
            dude.setStat(CUR_HP, dude.getStat(MAX_HP));
            dude.setStat(CUR_MP, dude.getStat(MAX_MP));
        };
    }

    public void execute(){
        while (true){ //TODO: Game end condition
            for (int waitTime = 0; waitTime>=-1; waitTime--){
                while(true){
                    actor readiestActor = null;
                    int bestApDiff = -1, apDiff;
                    for (actor dude:this.Board.getCombatants()){
                        apDiff = dude.getStat(CUR_AP)-dude.getStat(AP_FOR_TURN);
                        if (dude.getWaitTime() == waitTime && apDiff > bestApDiff){
                            bestApDiff = apDiff;
                            readiestActor = dude;
                        }
                    }
                    if (readiestActor == null){
                        break;
                    } else {
                        turnLoop(readiestActor, false);
                    }
                }
            }
            for (actor dude:this.Board.getCombatants()){
                if (dude.getStat(CUR_HP) > 0){
                    dude.addStat(CUR_AP, apGainPerTurn);
                    int waitTime = dude.getWaitTime();
                    if (waitTime > -1) dude.setWaitTime(waitTime - 1);
                }
            }
        }
    }

    public void turnLoop(actor dude, boolean preactionTurn){

    }

    private board Board;
    private int apGainPerTurn = options.ap_gain_per_turn;
}
