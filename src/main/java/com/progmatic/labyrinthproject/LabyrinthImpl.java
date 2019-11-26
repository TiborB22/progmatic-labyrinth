package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
//import org.graalvm.compiler.phases.graph.ReentrantBlockIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private CellType[][] table;
    private int width;
    private int height;
    private Coordinate playerPosition;

    public LabyrinthImpl() {
        this.width = -1;
        this.height = -1;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());

            this.table = new CellType[width][height];


            this.width = width;
            this.height = height;
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            this.table[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            this.table[hh][ww] = CellType.END;
                            break;
                        case 'S':
                            this.table[hh][ww] = CellType.START;
                            this.playerPosition = new Coordinate(ww,hh);
                            break;
                        default:
                            this.table[hh][ww] = CellType.EMPTY;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        //Tudom tudom, lehetett volna lekérni a fildekből.
        int col = table[0].length-1;
        int row = table.length-1;

        //ha minuszba megyünk:
        if ( c.getCol() < 0 || c.getRow() < 0 ) {
            throw new CellException(c.getRow(),c.getCol(), "Odafigyeljél, minuszba mentünk." );
        }

        //ha nem létező cellát kér le az user:
        if ( c.getCol() > col || c.getRow() > row ) {
            throw new CellException(c.getRow(),c.getCol(), "Odafigyeljél, nincs ekkora a labirintus." );
        }
        return table[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.table = new CellType[width][height];
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        this.table[c.getRow()][c.getCol()] = type;

        //btw start position is player position

        if (type.equals(CellType.START)){
            this.playerPosition = c;
        }

    }

    @Override
    public Coordinate getPlayerPosition() {

        /*
        for (int hh = 0; hh < height; hh++) {
            for (int ww = 0; ww < width; ww++) {
                if (this.table[hh][ww].equals(CellType.START)){
                    return new Coordinate(ww,hh);
                }
            }
        }
        */
        return this.playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() throws CellException {
        Coordinate playerPosition = getPlayerPosition();
        if (getCellType(playerPosition).equals(CellType.END)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() throws CellException {

        //ki vannak hagyva az egyéb eseteik, pl pályán kívűlre érkeznénk, de ehhez már nem elég a türelmem ÉS/VAGY kompetenciám.

        ArrayList<Coordinate> nearbyCells = new ArrayList<>();
        ArrayList<Direction> possibeMoves = new ArrayList<>();

        Coordinate startPosition = getPlayerPosition();
        int startCol = startPosition.getCol();
        int startRow = startPosition.getRow();


        // nearby cells:

        Coordinate north = new Coordinate(startCol,startRow-1); //eszak
        Coordinate south = new Coordinate(startCol,startRow+1); //del
        Coordinate east = new Coordinate(startCol+1,startRow);
        Coordinate west = new Coordinate(startCol-1,startRow);
        nearbyCells.add(north);
        nearbyCells.add(south);
        nearbyCells.add(east);
        nearbyCells.add(west);


        for (Coordinate near : nearbyCells) {

            if (getCellType(near).equals(CellType.EMPTY)){
                Direction d = null;

                if (near.getCol() == startCol && near.getRow()==startRow-1) { d = Direction.NORTH; }
                if (near.getCol() == startCol && near.getRow()==startRow+1) { d = Direction.SOUTH; }
                if (near.getCol() == startCol+1 && near.getRow()==startRow) { d = Direction.EAST; }
                if (near.getCol() == startCol-1 && near.getRow()==startRow) { d = Direction.WEST; }
                possibeMoves.add(d);

                System.out.println();

            }
        }

        return possibeMoves;

    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException, CellException {
        Coordinate coordinateForCheck = null;
        if (direction.equals(Direction.NORTH)){
            if (getPlayerPosition().getRow() == 0) {
                //WALLL
                throw new InvalidMoveException(); }

            coordinateForCheck = new Coordinate(getPlayerPosition().getCol(),getPlayerPosition().getRow()-1);
        }
        if (direction.equals(Direction.SOUTH)){
            if (getPlayerPosition().getRow() == table[0].length-1) {
                //WALLL
                throw new InvalidMoveException(); }
            coordinateForCheck = new Coordinate(getPlayerPosition().getCol(),getPlayerPosition().getRow()+1);
        }
        if (direction.equals(Direction.EAST)){
            if (getPlayerPosition().getCol() == table.length-1) {
                //WALLL
                throw new InvalidMoveException(); }
            coordinateForCheck = new Coordinate(getPlayerPosition().getCol()+1,getPlayerPosition().getRow());
        }
        if (direction.equals(Direction.WEST)){
            if (getPlayerPosition().getCol() == 0) {
                //WALLL
                throw new InvalidMoveException(); }
            coordinateForCheck = new Coordinate(getPlayerPosition().getCol()-1,getPlayerPosition().getRow());
        }

        if (!getCellType(coordinateForCheck).equals(CellType.EMPTY)){
            throw new InvalidMoveException();
        }
        playerPosition = coordinateForCheck;
    }

}
