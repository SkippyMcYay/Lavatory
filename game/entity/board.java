package entity;

import unclassified.options;
import static unclassified.stat.*;

import java.awt.Point;
import java.util.Vector;

public class board {

    public board(){
        setupBoard();
    }

    public void displayBoard(){
        for (int j=0;j<this.board_size_y; j++){
            for(int i=0;i<this.board_size_x;i++){
                displaySpace(i,j);
            }
            System.out.println();

        }
        System.out.println("------------------------------");
    }

    public void addCharacter(actor individual){
        if (this.combatants.size() >= max_combatants){
            System.out.println("Cannot add combatant"+individual.getName()+": combatant limit reached.");
            return;
        }
        this.combatants.add(individual);
        this.combatant_map[individual.getPos().x][individual.getPos().y] = individual;
    }

    public void removeCharacter(actor individual){
        if (!this.combatants.remove(individual)){
            System.out.println("something went wrong removing "+individual.getName()+" from the world.");
        }
    }

    public actor getCombatantAt(int x, int y){
        return combatant_map[x][y];
    }

    public Vector<actor> getCombatants(){
        return combatants;
    }

    public void setTerrain(int x, int y, int type){
            terrain_map[x][y] = type;
    }

    public int getTerrain(int x, int y){
        return this.terrain_map[x][y];
    }

    public boolean[][] getDirectRange(int x, int y, int rangeValue){
        boolean[][] range = new boolean[this.board_size_x][this.board_size_y];
        for (int i=0; i<board_size_x; i++){
            for (int j=0; j<board_size_y; j++){
                int distance = Math.abs(x-i) + Math.abs(y-j);
                range[i][j] = ((distance <= rangeValue) && (distance>0));
            }
        }
        return range;
    }

    public int[][] getMoveRange(actor person){
        int[][] ap_remaining = new int[this.board_size_x][this.board_size_y];
        for (int i=0; i<this.board_size_x; i++){
            for (int j=0; j<this.board_size_y; j++){
                ap_remaining[i][j] = -1;
            }
        }
        Point pos = person.getPos();
        ap_remaining[pos.x][pos.y] = person.getStat(CUR_AP);
        getMoveRangeHelper(ap_remaining, pos.x, pos.y, person.getStat(MOVE_AP_COST), person.getFaction());
        ap_remaining[pos.x][pos.y] = -1;
        for (int i=0; i<this.board_size_x; i++){
            for (int j=0; j<this.board_size_y; j++){
                if (combatant_map[i][j]!=null) ap_remaining[i][j] = -1;
            }
        }
        return ap_remaining;
    }

    public void displayDirectRange(boolean[][] range){
        for (int j=0;j<this.board_size_y; j++){
            for(int i=0;i<this.board_size_x;i++){
                if (range[i][j]){
                    System.out.print("! ");
                } else {
                    displaySpace(i,j);
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }

    public void displayMoveRange(int[][] range){
        for (int j=0;j<this.board_size_y; j++){
            for(int i=0;i<this.board_size_x;i++){
                if (range[i][j] != -1){
                    System.out.print("! ");
                } else {
                    displaySpace(i,j);
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }

    private void getMoveRangeHelper(int[][] ap_remaining, int x, int y, int move_cost, int faction){
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
            actor combatant = combatant_map[cur_x][cur_y];
            if (terrain_map[cur_x][cur_y]==0 && ap_remaining[cur_x][cur_y] < next_space_ap
                    && (combatant==null || combatant.getFaction()==faction)) {
                ap_remaining[cur_x][cur_y] = next_space_ap;
                getMoveRangeHelper(ap_remaining, cur_x, cur_y, move_cost, faction);
            }
        }
    }

    private void displaySpace(int x, int y){
        char c = '$';
        actor combatant = this.combatant_map[x][y];
        if (combatant != null){
            c=combatant.getName().charAt(0);
        } else {
            switch(terrain_map[x][y]){
                case 0:
                    c = '.';
                    break;
                case 1:
                    c = '#';
                    break;
            }
        }
        System.out.print(c);
        System.out.print(' ');
    }

    private void setupBoard(){
        for (int i=0;i<this.board_size_x; i++){
            for(int j=0;j<this.board_size_y;j++){
                this.combatant_map[i][j]= null;
                this.terrain_map[i][j]=0;
            }
        }
    }

    private int board_size_x=options.board_size_x;
    private int board_size_y=options.board_size_y;
    private int max_combatants=options.max_combatants;
    private Vector<actor> combatants = new Vector<actor>();
    private actor[][] combatant_map=new actor[board_size_x][board_size_y];
    private int[][] terrain_map=new int[board_size_x][board_size_y];
}
