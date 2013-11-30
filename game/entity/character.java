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
}