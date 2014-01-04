package entity;

import unclassified.options;
import java.awt.Point;

public class board {

    public board(){
        setupBoard();
    }

    public void displayBoard(){
        for (int j=0;j<this.board_size_y; j++){
            for(int i=0;i<this.board_size_x;i++){
                if (this.combatant_map[i][j]!=-1){
                    char c = (char)(this.combatant_map[i][j] + 'a');
                    System.out.print(c);
                } else {
                    System.out.print(terrain_map[i][j]);
                }
            }
            System.out.println();

        }
        System.out.println("------------------------------");
    }

    public void addCharacter(actor individual){
        for(int i=0;i<this.combatants.length;i++){
            if(this.combatants[i]==null){
                this.combatants[i]=individual;
                this.combatant_map[individual.getPos().x][individual.getPos().y]=i;
                break;
            }
        }
        System.out.println("something went wrong adding "+individual.name+" to the world.");
    }

    public void removeCharacter(actor individual){
        for(int i=0;i<this.combatants.length;i++){
            if(this.combatants[i]==individual){
                this.combatants[i]=null;
                break;
            }

        }
        System.out.println("something went wrong removing "+individual.name+" from the world.");
    }

    //Tentative. Need this so an external function can call getMoveRange
    public actor getCharacter(int index){
        return combatants[index];
    }

    public void setTerrain(int x, int y, int type){
        terrain_map[x][y] = type;
    }

    public int[][] getMoveRange(actor person){
        int[][] ap_remaining = new int[this.board_size_x][this.board_size_y];
        for (int i=0; i<this.board_size_x; i++){
            for (int j=0; j<this.board_size_y; j++){
                ap_remaining[i][j] = -1;
            }
        }
        Point pos = person.getPos();
        ap_remaining[pos.x][pos.y] = person.getCurAp();
        getMoveRangeHelper(ap_remaining, pos.x, pos.y, person.getMoveApCost(), person.getFaction());
        ap_remaining[pos.x][pos.y] = -1;
        for (int i=0; i<this.board_size_x; i++){
            for (int j=0; j<this.board_size_y; j++){
                if (combatant_map[i][j]!=-1) ap_remaining[i][j] = -1;
            }
        }
        return ap_remaining;
    }

    public void displayMoveRange(int[][] range){
        for (int j=0;j<this.board_size_y; j++){
            for(int i=0;i<this.board_size_x;i++){
                char c = range[i][j] != -1 ? '1' : '0';
                System.out.print(c);
            }
            System.out.println();

        }
        System.out.println("------------------------------");
    }

    private void getMoveRangeHelper(int[][] ap_remaining, int x, int y, int move_cost, String faction){
        int next_space_ap = ap_remaining[x][y] - move_cost;
        if (next_space_ap < 0){
            return;
        }
        for (int i=0; i<4; i++) {
            int cur_x = x, cur_y = y;
            if (i==0 && --cur_x < 0) break;
            else if (i==1 && ++cur_x >= board_size_x) break;
            else if (i==2 && --cur_y < 0) break;
            else if (i==3 && ++cur_y >= board_size_y) break;
            int combatant_index = combatant_map[cur_x][cur_y];
            if (terrain_map[cur_x][cur_y]==0 && ap_remaining[cur_x][cur_y] < next_space_ap
                    && (combatant_index==-1 || combatants[combatant_index].getFaction()==faction)) {
                ap_remaining[cur_x][cur_y] = next_space_ap;
                getMoveRangeHelper(ap_remaining, cur_x, cur_y, move_cost, faction);
            }
        }
    }

    private void setupBoard(){
        for (int i=0;i<this.board_size_x; i++){
            for(int j=0;j<this.board_size_y;j++){
                this.combatant_map[i][j]= -1;
                this.terrain_map[i][j]=0;
            }
        }
    }

    private int board_size_x=new options().board_size_x;
    private int board_size_y=new options().board_size_y;
    //private int number_of_combatants=((new options().number_of_combatants<=10) ? new options().number_of_combatants:10);
    private int number_of_combatants=new options().number_of_combatants;
    private actor[] combatants = new actor[number_of_combatants];
    private int[][] combatant_map=new int[board_size_x][board_size_y];
    private int[][] terrain_map=new int[board_size_x][board_size_y];
}
