package unclassified;

import entity.*;
import java.awt.Point;
import display.Window;
import static unclassified.stat.*;
import java.util.Stack;

public class gameLoop {
    enum gameState{NORMAL, SELECT_SPACE, PROMPT}
    enum action{MOVE, WEAK_ATTACK, STRONG_ATTACK}

    private class recordedAttack{
        recordedAttack(actor guy, Point target, action act, int rng){
            this.attacker = guy;
            this.targetSpace = target;
            this.actn = act;
            this.range = rng;
        }
        actor attacker;
        Point targetSpace;
        action actn;
        int range;
    }

    public gameLoop(board brd){
        this.state = gameState.NORMAL;
        this.Board = brd;
        this.attackStack = new Stack<recordedAttack>();
        for (actor dude:this.Board.getCombatants()){
            dude.setStat(CUR_AP, 0);
            dude.setWaitTime(-1);
            dude.setStat(CUR_HP, dude.getStat(MAX_HP));
            dude.setStat(CUR_MP, dude.getStat(MAX_MP));
        }
    }

    public void findNextActor(){
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
        if (this.attackStack.isEmpty()){
            System.out.println(this.curActor.getName()+" is ready to rock.");
        } else {
            System.out.println(this.curActor.getName()+" swiftly preacts like the wind!");
        }
        curActor.setIsDefending(false);
        this.state = gameState.NORMAL;
    }

    public void turnEnd(){
        this.resolveAttackStack();
        this.findNextActor();
    }

    public void buttonParser(String button){
        int ap = curActor.getStat(CUR_AP);
        boolean hasEnoughAp = true;
        float preactMod = this.attackStack.isEmpty() ? 1 : options.preaction_multiplier;
        if (this.state == gameState.NORMAL){
            if (button == "move" && (hasEnoughAp = ap >= curActor.getStat(MOVE_AP_COST) * preactMod)){
                int oldMoveApCost = this.curActor.getStat(MOVE_AP_COST);
                this.curActor.setStat(MOVE_AP_COST, (int)(oldMoveApCost * preactMod));
                this.Board.getMoveRange(curActor);
                this.curActor.setStat(MOVE_AP_COST, oldMoveApCost);
                this.window.setHighlightRange(this.Board.getRangeMap());
                this.state = gameState.SELECT_SPACE;
                this.actn = action.MOVE;
            } else if (button == "weak attack" && (hasEnoughAp = ap >= options.weak_attack_AP_cost * preactMod)){
                this.window.setHighlightRange(this.Board.getDirectRange(curActor,curActor.getStat(ATTACK_RANGE)));
                this.state = gameState.SELECT_SPACE;
                this.actn = action.WEAK_ATTACK;
            } else if (button == "strong attack" && (hasEnoughAp = ap > options.strong_attack_AP_cost * preactMod)){
                this.window.setHighlightRange(this.Board.getDirectRange(curActor,curActor.getStat(ATTACK_RANGE)));
                this.state = gameState.SELECT_SPACE;
                this.actn = action.STRONG_ATTACK;
            } else if (button == "defend" && (hasEnoughAp = ap > options.defend_AP_cost * preactMod)){
                curActor.setIsDefending(true);
                curActor.addStat(CUR_AP, (int)(-options.defend_AP_cost * preactMod));
                System.out.println(curActor.getName() + " takes a sexy defensive stance.");
                //needs to delay
                this.turnEnd();
            } else if (button == "delay"){
                System.out.println("You wish you could delay right now.");
            } else if (button == "end turn"){
                this.turnEnd();
            }
            if (!hasEnoughAp) System.out.println("Not enough AP!");
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
                this.state = gameState.NORMAL;
            }
        }
    }

    public void performAction(Point p){
        float preactMod = this.attackStack.isEmpty() ? 1 : options.preaction_multiplier;
        switch (this.actn){
            case MOVE:
                int newAp = this.Board.getRangeMap()[p.x][p.y];
                this.Board.moveCombatant(this.curActor, p.x, p.y);
                this.curActor.setStat(CUR_AP, newAp);
                this.state = gameState.NORMAL;
                break;
            case WEAK_ATTACK:
                curActor.addStat(CUR_AP, (int)(-options.weak_attack_AP_cost * preactMod));
                break;
            case STRONG_ATTACK:
                curActor.addStat(CUR_AP, (int)(-options.strong_attack_AP_cost * preactMod));
                break;
        }
        if (this.actn==action.WEAK_ATTACK || this.actn==action.STRONG_ATTACK){
            actor target = this.Board.getCombatantAt(p.x,p.y);
            if (target!=null){
                System.out.println(curActor.getName()+" attempts to attack "+target.getName()+"!");
                if (target.canPreact()){
                    if (true){ //preaction prompt! goes here
                        recordedAttack atk = new recordedAttack(curActor, p, this.actn, curActor.getStat(ATTACK_RANGE));
                        this.attackStack.push(atk);
                        this.curActor = target;
                        turnBegin();
                    } else {
                        resolveAttack(p);
                    }
                }
            }
        }
    }

    public void resolveAttackStack(){
        while (!this.attackStack.isEmpty()){
            recordedAttack atk = attackStack.pop();
            if (atk.attacker.getStat(CUR_HP)>0){
                if (atk.attacker.getDistanceTo(atk.targetSpace)<=atk.range){
                    this.curActor = atk.attacker;
                    this.actn = atk.actn;
                    resolveAttack(atk.targetSpace);
                } else {
                    System.out.println(atk.attacker.getName()+" is too far to land his/her attack!");
                }
            }
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
        this.state=gameState.NORMAL;
    }

    public void setWindow(Window w){
        this.window = w;
    }

    private Stack<recordedAttack> attackStack;
    private action actn;
    private Window window;
    private gameState state;
    private actor curActor;
    private board Board;
    private int apGainPerTurn = options.ap_gain_per_turn;
}
