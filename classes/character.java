
public class character {

    private int max_hp,cur_hp=0,0;
    private int max_mp,cur_mp=0,0;
    private int attack,attack_range=0,1;
    private int defense=0;
    private int ap_for_turn,max_ap,cur_ap=60,100,0;
    
    public int getHp(){
        return this.cur_hp;
    }
    public int setHp(int x){
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