package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.util.List;
import java.util.Random;

public class Player implements com.progmatic.labyrinthproject.interfaces.Player {
    private int strategy;

    // 1: fall follower
    // 2: random

    public Player(int strategy){
        this.strategy = strategy;
    }
    @Override
    public Direction nextMove(Labyrinth l) throws CellException {
        switch (strategy){
            case 1:
                return null;
            case 2:
                List<Direction> posssibleMoves = l.possibleMoves();
                Random rnd = new Random();
                int random = rnd.nextInt(posssibleMoves.size());
                System.out.println( posssibleMoves.get(random) );
                return posssibleMoves.get(random);
            case 3:
                //szorgamli
                //TODO
                break;
        }
        return null;
    }
}
