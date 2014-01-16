package unclassified;

import entity.*;
import java.awt.Point;
import display.Window;
import static unclassified.stat.*;

public class gameLoop {
    enum gameState{NORMAL, SELECT_SPACE, PROMPT}
    enum action{MOVE, WEAK_ATTACK, STRONG_ATTACK}

    public gameLoop(board brd){
        this.state = gameState.NORMAL;
        this.Board = brd;
        for (actor dude:this.Board.getCombatants()){
            dude.setStat(CUR_AP, 0);
            dude.setWaitTime(-1);
            dude.setStat(CUR_HP, dude.getStat(MAX_HP));
            dude.setStat(CUR_MP, dude.getStat(MAX_MP));
        }
    }

    public void execute(){
        actor readiestActor = null;
        while (true){ //TODO: Game end condition
            for (int waitTime = 0; waitTime>=-1; waitTime--){
                int bestApDiff = -1, apDiff;
                for (actor dude:this.Board.getCombatants()){
                    apDiff = dude.getStat(CUR_AP)-dude.getStat(AP_FOR_TURN);
                    if (dude.getWaitTime() == waitTime && apDiff > bestApDiff){
                        bestApDiff = apDiff;
                        readiestActor = dude;
                    }
                }
                if (readiestActor != null) {
                    this.curActor = readiestActor;
                    System.out.println(this.curActor.getName()+" is ready to rock.");
                    this.turnBegin();
                    return;
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

    public void turnBegin(){
        curActor.setIsDefending(false);
    }

    public void buttonParser(String button){
        if (this.state == gameState.NORMAL){
            if (button == "move"){
                this.window.setHighlightRange(this.Board.getMoveRange(curActor));
                this.state = gameState.SELECT_SPACE;
                this.actn = action.MOVE;
                this.window.update(this.Board);
            } else if (button == "weak attack"){
                this.window.setHighlightRange(this.Board.getDirectRange(curActor,curActor.getStat(ATTACK_RANGE)));
                this.state = gameState.SELECT_SPACE;
                this.actn = action.WEAK_ATTACK;
            } else if (button == "strong attack"){
                this.window.setHighlightRange(this.Board.getDirectRange(curActor,curActor.getStat(ATTACK_RANGE)));
                this.state = gameState.SELECT_SPACE;
                this.actn = action.STRONG_ATTACK;
            } else if (button == "defend"){
                curActor.setIsDefending(true);
                curActor.addStat(CUR_AP, -options.defend_AP_cost);
                System.out.println(curActor.getName() + " takes a sexy defensive stance.");
                this.execute();
            } else if (button == "delay"){
                System.out.println("You wish you could delay right now.");
            } else if (button == "end turn"){
                this.execute();
            }
        }
    }

    public void tileParser(Point p){
        if (this.state == gameState.NORMAL){
            actor guy = this.Board.getCombatantAt(p.x,p.y);
            if (guy != null) guy.displayStats();
        } else if (this.state == gameState.SELECT_SPACE){
            if (this.Board.getRangeMap()[p.x][p.y]!=-1){
                this.performAction(p);
            } else {
                this.window.clearHighlightRange();
                this.state = gameState.NORMAL;
            }
        }
    }

    public void performAction(Point p){
        switch (this.actn){
            case MOVE:
                int newAp = this.Board.getRangeMap()[p.x][p.y];
                curActor.setPos(p);
                curActor.setStat(CUR_AP, newAp);
                this.state = gameState.NORMAL;
                this.window.update(this.Board);
                break;
            case WEAK_ATTACK:
                curActor.addStat(CUR_AP, -options.weak_attack_AP_cost);
                //preaction code goes here!
                resolveAttack(p);
                break;
            case STRONG_ATTACK:
                curActor.addStat(CUR_AP, -options.strong_attack_AP_cost);
                //preaction code!!!!
                resolveAttack(p);
                break;
        }
    }

    public void resolveAttack(Point p){
        int attackModifier = 0;
        if (actn == action.WEAK_ATTACK){
            attackModifier = options.weak_attack_value;
        } else if (actn == action.STRONG_ATTACK){
            attackModifier = options.strong_attack_value;
        }
        actor target = this.Board.getCombatantAt(p.x, p.y);
        if (target == null){
            System.out.println(curActor.getName()+" attacked nothing.");
        } else {
            int damage = curActor.getStat(ATTACK)+attackModifier-target.getStat(DEFENSE);
            if (target.getIsDefending()) damage/=2;
            target.addStat(CUR_HP, -damage);
            System.out.printf(curActor.getName() + " dealt %d damage to " + target.getName() + ".%n", damage);
            if (target.getStat(CUR_HP)==0){
                System.out.println(target.getName()+" has died! His or her heart and eardrums have ruptured.");
            }
        }
    }

    public void setWindow(Window w){
        this.window = w;
    }

    private action actn;
    private Window window;
    private gameState state;
    private actor curActor;
    private board Board;
    private int apGainPerTurn = options.ap_gain_per_turn;
}
