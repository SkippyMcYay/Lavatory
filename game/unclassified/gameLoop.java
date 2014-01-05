package unclassified;

import entity.*;
import unclassified.options;

public class gameLoop {
    public gameLoop(board brd){
        this.Board = brd;
        for (actor dude:this.Board.getCombatants()){
            dude.setCurAp(0);
            dude.setWaitTime(-1);
            dude.setCurHp(dude.getMaxHp());
            dude.setCurMp(dude.getMaxMp());
        };
    }

    public void execute(){
        while (true){ //TODO: Game end condition
            for (int waitTime = 0; waitTime>=-1; waitTime--){
                while(true){
                    actor readiestActor = null;
                    int bestApDiff = -1, apDiff;
                    for (actor dude:this.Board.getCombatants()){
                        apDiff = dude.getCurAp()-dude.getApForTurn();
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
                if (dude.getCurHp() > 0){
                    int ap = dude.getCurAp() + apGainPerTurn;
                    if (ap>dude.getMaxAp()) ap = dude.getMaxAp();
                    dude.setCurAp(ap);
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
