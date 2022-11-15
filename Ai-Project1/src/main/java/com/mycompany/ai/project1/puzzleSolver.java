package com.mycompany.ai.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *
 * @author Haitham
 */
public class puzzleSolver {
    
    public boolean boardsEquality(String [][]board1,String [][]board2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(board1[i][j].equals(board2[i][j]))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    ArrayList<Board> testedNodes;
    public Board aStar(Board start, Board End,boolean Heuristic){
        testedNodes=new ArrayList<>();
        PriorityQueue<Board> closedList = new PriorityQueue<>();
        PriorityQueue<Board> openList = new PriorityQueue<>();
        testedNodes.add(start);
        start.cost=0;
        if(Heuristic){
            start.f = start.cost + start.manhattanDistance(End);
        }
        else{
            start.f = start.cost + start.wrongPlaceHeu(End);
        }
        openList.add(start);

        while(!openList.isEmpty()){
            Board n = openList.peek();
            if(boardsEquality(n.Current,End.Current)){
                return n;
            }
            for(Board board : n.GenerateChildren(n.xindex, n.yindex)){
            boolean flag1=false,flag2=false;
                Board m = board;
                int f;
                m.cost=m.parent.cost+1;
                if(Heuristic){
                    m.heu=m.manhattanDistance(End);
                   f = m.cost + m.heu;
                }
                else{
                   m.heu= m.wrongPlaceHeu(End);
                   f = m.cost + m.heu;
                }
                Iterator value = openList.iterator();
                Iterator value1 = closedList.iterator();
                while(value.hasNext()){
                    Board bd=(Board) value.next();
                    if(boardsEquality(bd.Current,m.Current)){
                        flag1=true;
                        break;
                    }
                }
                while(value1.hasNext()){
                    Board bd=(Board) value1.next();
                    if(boardsEquality(bd.Current,m.Current)){
                        flag2=true;
                        break;
                    }
                }
                if(!flag1 && !flag2){
                    m.parent = n;
                    m.cost=m.parent.cost+1;
                    if(Heuristic){
                        m.f = m.cost + m.manhattanDistance(End);
                    }
                    else{
                        m.f = m.cost + m.wrongPlaceHeu(End);
                    }
                    openList.add(m);
                } else {
                    if(f < m.f){
                        m.parent = n;
                        m.cost = m.parent.cost+1;
                        m.f=f;

                        if(flag2){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
            testedNodes.add(n);
        }
        return null;
    }
    
    public Board Greedy(Board start, Board End,boolean Heuristic){
        
        testedNodes=new ArrayList<>();
        ArrayList<Board> closedList = new ArrayList<>();
        PriorityQueue<Board> openList = new PriorityQueue<>();
        testedNodes.add(start);
        if(Heuristic){
            start.heu = start.manhattanDistance(End);
            start.f = start.heu;
        }
        else{
            start.heu = start.wrongPlaceHeu(End);
            start.f = start.heu;
        }
        openList.add(start);

        while(!openList.isEmpty()){
            Board n = openList.peek();
            if(boardsEquality(n.Current,End.Current)){
                return n;
            }
            for(Board board : n.GenerateChildren(n.xindex, n.yindex)){
                boolean flag1=false,flag2=false;
                Board m = board;
                Iterator value = openList.iterator();
                Iterator value1 = closedList.iterator();
                while(value.hasNext()){
                    Board bd=(Board) value.next();
                    if(boardsEquality(bd.Current,m.Current)){
                        flag1=true;
                        break;
                    }
                }
                while(value1.hasNext()){
                    Board bd=(Board) value1.next();
                    if(boardsEquality(bd.Current,m.Current)){
                        flag2=true;
                        break;
                    }
                }
                if(!flag1 && !flag2){
                    m.parent = n;
                    if(Heuristic){
                        m.heu=m.manhattanDistance(End);
                        m.f = m.heu;
                    }
                    else{
                        m.heu = m.wrongPlaceHeu(End);
                        m.f = m.heu;
                    }
                    openList.add(m);
                } 
            }

            openList.remove(n);
            closedList.add(n);
            testedNodes.add(n);
        }
        
        
        return null;
    }
    
    
    
    public ArrayList<Board> solution(Board board) {
        ArrayList<Board> arr=new ArrayList<>();
            while (board != null) {
                arr.add(board);
                board = board.parent;
            }
            Collections.reverse(arr);
            return arr;
    }
}
