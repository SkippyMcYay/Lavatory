package entity;

public class character {

    private int max_hp=55,cur_hp,
                max_mp,cur_mp,
                attack,attack_range,
                defense,
                ap_for_turn,max_ap,cur_ap;
    
    public int getHp(){
        return this.cur_hp;
    }
    public void setHp(int x){
        if(x>0){
            if(x<this.max_hp){
                this.cur_hp=x;
            }else{
                this.cur_hp=this.max_hp;
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
    public int getMp(){
        return this.cur_mp;
    }
    public void setMp(int x){
        if(x>0){
            if(x<this.max_mp){
                this.cur_mp=x;
            }else{
                this.cur_mp=this.max_mp;
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
        this.cur_ap=x;
    }
}