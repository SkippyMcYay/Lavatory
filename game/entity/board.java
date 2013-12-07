package entity;

import unclassified.options;

public class board {

    private int board_size=new options().board_size;
    //private int number_of_combatants=((new options().number_of_combatants<=10) ? new options().number_of_combatants:10);
    private int number_of_combatants=new options().number_of_combatants;
    private character[] combatants = new character[number_of_combatants];
    private int[][] battlefield=new int[board_size][board_size];
    public board(){
        setupBoard();
    }

    private void setupBoard(){
        for (int i=0;i<this.battlefield.length; i++){
            for(int j=0;j<this.battlefield[1].length;j++){
                this.battlefield[i][j]= -1;
            }
        }
    }
    public void displayBoard(){
        for (int j=0;j<this.battlefield.length; j++){
            for(int i=0;i<this.battlefield[j].length;i++){
                System.out.print(this.battlefield[i][j]+"\t");
            }
            System.out.println();

        }
        System.out.println("------------------------------");
    }

    public void addCharacter(character individual){
        for(int i=0;i<this.combatants.length;i++){
            if(this.combatants[i]==null){
                this.combatants[i]=individual;
                this.battlefield[individual.getPos()[0]][individual.getPos()[1]]=i;
                break;
            }
        }
        System.out.println("something went wrong adding "+individual.name+" to the world.");
    }
    public void removeCharacter(character individual){
        for(int i=0;i<this.combatants.length;i++){
            if(this.combatants[i]==individual){
                this.combatants[i]=null;
                break;
            }

        }
        System.out.println("something went wrong removing "+individual.name+" from the world.");
    }

    public boolean[][] getMoveRange(int position[],int max_distance){
        //this is gonna get ugly, also will be horrendously flawed
        boolean[][] free_tiles_in_range=new boolean[this.board_size][this.board_size];
        int movable_spaces_counter=0;
        int posx=position[0]; int posy=position[1];
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
                int abs_dist_x = ((i-posx>0)? i-posx: -(i-posx));
                int abs_dist_y = ((j-posy>0)? j-posy: -(j-posy));
                if(this.battlefield[i][j]==-1 && abs_dist_x+abs_dist_y<=max_distance){
                    free_tiles_in_range[i][j]=true;
                }

            }
        }
        return free_tiles_in_range;
    }
    public void displayMoveRange(boolean[][] range){
        for (int j=0;j<this.battlefield.length; j++){
            for(int i=0;i<this.battlefield[j].length;i++){
                System.out.print(range[i][j]+"\t");
            }
            System.out.println();

        }
        System.out.println("------------------------------");
    }

}
