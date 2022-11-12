package com.mycompany.ai.project1;

import java.util.ArrayList;

/**
 *
 * @author Haitham
 */
public class Board implements Comparable<Board>{
    String [][]Current;
    String [][]Goal;
    ArrayList<Board> Children=new ArrayList<>();
    Board parent=null;
    int cost=0,heu,f;
    int xindex=-1,yindex=-1;
    public Board(String [][] board){
        this.Current = board;
        for(int k=0;k<3;k++){
            for(int m=0;m<3;m++){
                if(board[k][m].equals(" ")){
                    xindex=k;
                    yindex=m;
                }
            }
        }
    }
    
    public ArrayList<Board> GenerateChildren(int i,int j){
        if(i<2){
            Board child=new Board(swapMove(this.Current,i+1,j));
            child.parent=this;
//            child.cost=parent.cost+1;
            Children.add(child);
        }
        if(i>0){
            Board child1=new Board(swapMove(this.Current,i-1,j));
            child1.parent=this;
//            child1.cost=parent.cost+1;
            Children.add(child1);
        }
        if(j<2){
            Board child2=new Board(swapMove(this.Current,i,j+1));
            child2.parent=this;
//            child2.cost=parent.cost+1;
            Children.add(child2);
        }
        if(j>0){
            Board child3=new Board(swapMove(this.Current,i,j-1));
            child3.parent=this;
//            child3.cost=parent.cost+1;
            Children.add(child3);
        }
        return Children;
    }
    public String[][] swapMove(String[][] board,int i1,int j1){
        String[][]swapped=new String[3][3];
        String tmp;
        int i=-1;
        int j=-1;
        for(int k=0;k<3;k++){
            for(int m=0;m<3;m++){
                swapped[k][m]=board[k][m];
                if(board[k][m].equals(" ")){
                    i=k;
                    j=m;
                }
            }
        }
        if(i!=-1 && j!=-1){
            tmp=swapped[i][j];
            swapped[i][j]=swapped[i1][j1];
            swapped[i1][j1]=tmp;
        }
        
        return swapped;
    }
    public int wrongPlaceHeu(Board board){
        int count=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(this.Current[i][j].equals(" ")){}
                else{
                    if(!(this.Current[i][j].equals(board.Current[i][j]))) count++;
                }
            }
        }
        return count;
    }
    public int manhattanDistance(Board board){
        int sum=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!(this.Current[i][j].equals(" "))){
                    for(int k=0;k<3;k++){
                        for(int m=0;m<3;m++){
                            if(this.Current[i][j].equals(board.Current[k][m])){
                                sum+=((Math.abs(i-k))+(Math.abs(j-m)));
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }
    public String toString() {
        String str;
         str = "\n";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                str += this.Current[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }
    @Override
      public int compareTo(Board board) {
            return Integer.compare(this.f, board.f);
      }
}
