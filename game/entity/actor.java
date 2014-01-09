package entity;

import java.awt.Point;
import java.io.IOException;
import data.fileLoader;

public class actor {

    private String role;
    private int faction;
    private int max_hp;
    private int cur_hp;
    private int max_mp;
    private int cur_mp;
    private int attack;
    private int attack_range;
    private int defense;
    private int ap_for_turn;
    private int max_ap;
    private int cur_ap;
    private int move_ap_cost;
    private int waitTime;
    private Point position;

    private static int num_loaded_stats = 8;
    private static String data_folder = ".\\game\\data\\roles\\";
    public String name;

//  constructor
    public actor(String name, String role, Point position){
        this.setRole(role);

        this.setCurHp(this.getMaxHp());
        this.setCurMp(this.getMaxMp());
        this.setCurAp(0);
        this.setPos(position);
        this.setFaction(0);
        this.setWaitTime(-1);
        this.name=name;
    }



    public void displayStats(){
        System.out.println("name  :"+this.name);
        System.out.println("role  :"+this.getRole());
        System.out.print("max hp:");System.out.println(this.getMaxHp());
        System.out.print("cur hp:");System.out.println(this.getCurHp());
        System.out.print("max mp:");System.out.println(this.getMaxMp());
        System.out.print("cur mp:");System.out.println(this.getCurMp());
        System.out.print("attack:");System.out.println(this.getAttack());
        System.out.print("at rng:");System.out.println(this.getAttackRange());
        System.out.print("dfense:");System.out.println(this.getDefense());
        System.out.print("ap4trn:");System.out.println(this.getApForTurn());
        System.out.print("max ap:");System.out.println(this.getMaxAp());
        System.out.print("cur ap:");System.out.println(this.getCurAp());
        System.out.print("mvcost:");System.out.println(this.getMoveApCost());
        System.out.print("facton:");System.out.println(this.getFaction());
        System.out.print("positn:");System.out.print(this.getPos().x);System.out.print(",");System.out.println(this.getPos().y);
        System.out.println();
    }

    public String getRole(){
        return this.role;
    }
    public void setRole(String role){
        String rolePath = data_folder + role;
        fileLoader loader = new fileLoader(rolePath, num_loaded_stats);
        try{
            String[] stringStats = loader.openFile();
            int[] stats = new int[num_loaded_stats];
            for (int i=0; i<num_loaded_stats; i++){
                stats[i] = Integer.parseInt(stringStats[i]);
            }
            this.setMaxHp(stats[0]);
            this.setMaxMp(stats[1]);
            this.setAttack(stats[2]);
            this.setDefense(stats[3]);
            this.setAttackRange(stats[4]);
            this.setApForTurn(stats[5]);
            this.setMaxAp(stats[6]);
            this.setMoveApCost(stats[7]);
            this.role = role;
        } catch(IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
    public int getCurHp(){
        return this.cur_hp;
    }
    public void setCurHp(int x){
        if(x>0){
            if(x<=this.max_hp){
                this.cur_hp=x;
            }
        }else{
            this.cur_hp=0;
        }
    }
    public int getMaxHp(){
        return this.max_hp;
    }
    public void setMaxHp(int x){
        if(x>0){
            this.max_hp=x;
        }else{
            this.max_hp=0;
        }
    }
    public int getCurMp(){
        return this.cur_mp;
    }
    public void setCurMp(int x){
        if(x>0){
            if(x<=this.max_mp){
                this.cur_mp=x;
            }
        }else{
            this.cur_mp=0;
        }
    }
    public int getMaxMp(){
        return this.max_mp;
    }
    public void setMaxMp(int x){
        if(x>0){
            this.max_mp=x;
        }else{
            this.max_mp=0;
        }
    }
    public int getAttack(){
        return this.attack;
    }
    public void setAttack(int x){
        this.attack=x;
    }
    public int getDefense(){
        return this.defense;
    }
    public void setDefense(int x){
        this.defense=x;
    }
    public int getAttackRange(){
        return this.attack_range;
    }
    public void setAttackRange(int x){
        this.attack_range=x;
    }
    public int getApForTurn(){
        return this.ap_for_turn;
    }
    public void setApForTurn(int x){
        this.ap_for_turn=x;
    }
    public int getMaxAp(){
        return this.max_ap;
    }
    public void setMaxAp(int x){
        this.max_ap=x;
    }
    public int getCurAp(){
        return this.cur_ap;
    }
    public void setCurAp(int x){
        if (x>0){
            if(x<=this.max_ap){
                this.cur_ap=x;
            }
        }else{
            this.cur_ap=0;
        }

    }
    public int getMoveApCost(){
        return this.move_ap_cost;
    }
    public void setMoveApCost(int x){
        this.move_ap_cost=x;
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
} //getters and setters
