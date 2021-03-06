package entity;

import java.awt.Point;
import java.io.IOException;
import data.fileLoader;
import unclassified.options;
import unclassified.stat;
import static unclassified.stat.*;

public class actor {

    private String role;
    private int faction;
    private int waitTime;
    private Point position;
    private boolean isDefending;

    private int[] stats = new int[stat.total_stats];

    private static int num_loaded_stats = 8;
    private static String data_folder = ".\\game\\data\\roles\\";
    private String name;

//  constructor
    public actor(String name, String role, Point position){
        this.setRole(role);

        this.setStat(CUR_HP, this.getStat(MAX_HP));
        this.setStat(CUR_MP, this.getStat(MAX_MP));
        this.setStat(CUR_AP, 0);
        this.setPos(position);
        this.setFaction(0);
        this.setWaitTime(-1);
        this.name=name;
    }


    public boolean canPreact(){
        int lowestCost = 999;
        int apCost[] = new int[4];
        apCost[0] = options.weak_attack_AP_cost;
        apCost[1] = options.strong_attack_AP_cost;
        apCost[2] = this.getStat(MOVE_AP_COST);
        apCost[3] = options.defend_AP_cost;
        for (int i=0; i<4; i++){
            if (apCost[i]<lowestCost) lowestCost = apCost[i];
        }
        return (this.getStat(CUR_AP) >= lowestCost * options.preaction_multiplier);
    }

    public int getDistanceTo(Point p){
        Point guy = this.getPos();
        return Math.abs(guy.x-p.x) + Math.abs(guy.y-p.y);
    }

    public void displayStats(){
        String defending = this.getIsDefending() ? "Defending" : "";
        System.out.println(this.name+"  HP:"+this.getStat(CUR_HP)+"/"+this.getStat(MAX_HP)+
        "  AP:"+this.getStat(CUR_AP)+"/"+this.getStat(AP_FOR_TURN)+"/"+this.getStat(MAX_AP)+
        "  "+defending);
    }

    public void displayAllStats(){
        System.out.println("name  :"+this.name);
        System.out.println("role  :"+this.getRole());

        for (stat s: stat.values()){
            System.out.print(s);
            System.out.printf(": %d%n",this.getStat(s));
        }
        System.out.print("facton:");System.out.println(this.getFaction());
        System.out.print("positn:");System.out.print(this.getPos().x);System.out.print(",");System.out.println(this.getPos().y);
        System.out.print("defend:");System.out.println(this.getIsDefending());
        System.out.println();
    }

    public String getName(){
        return this.name;
    }
    public void setName(String n){
        this.name = n;
    }
    public int getStat(stat s){
        return stats[s.getId()];
    }
    public void setStat(stat s, int value){
        switch (s){
            case CUR_HP:
                if (value>this.getStat(MAX_HP)) value = this.getStat(MAX_HP);
                break;
            case CUR_MP:
                if (value>this.getStat(MAX_MP)) value = this.getStat(MAX_MP);
                break;
            case CUR_AP:
                if (value>this.getStat(MAX_AP)) value = this.getStat(MAX_AP);
                break;
        }
        if (value<0) value = 0;
        stats[s.getId()] = value;
    }
    public void addStat(stat s, int value){
        this.setStat(s, value + this.getStat(s));
    }
    public String getRole(){
        return this.role;
    }
    public void setRole(String role){
        String rolePath = data_folder + role;
        fileLoader loader = new fileLoader(rolePath, num_loaded_stats);
        try{
            String[] stringStats = loader.openFile();
            int[] loadedStats = new int[num_loaded_stats];
            for (int i=0; i<num_loaded_stats; i++){
                loadedStats[i] = Integer.parseInt(stringStats[i]);
            }
            this.setStat(MAX_HP, loadedStats[0]);
            this.setStat(MAX_MP, loadedStats[1]);
            this.setStat(ATTACK, loadedStats[2]);
            this.setStat(DEFENSE, loadedStats[3]);
            this.setStat(ATTACK_RANGE, loadedStats[4]);
            this.setStat(AP_FOR_TURN, loadedStats[5]);
            this.setStat(MAX_AP, loadedStats[6]);
            this.setStat(MOVE_AP_COST, loadedStats[7]);
            this.role = role;
        } catch(IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
    public int getFaction(){
        return this.faction;
    }
    public void setFaction(int fact){
        this.faction = fact;
    }
    public int getWaitTime(){
        return this.waitTime;
    }
    public void setWaitTime(int time){
        this.waitTime = time;
    }
    public Point getPos(){
        return this.position;
    }
    public void setPos(Point new_position){
        //possible array linking error in the future
        this.position=new_position;
    }
    public boolean getIsDefending() {
        return this.isDefending;
    }
    public void setIsDefending(boolean d){
        this.isDefending = d;
    }
} //getters and setters
