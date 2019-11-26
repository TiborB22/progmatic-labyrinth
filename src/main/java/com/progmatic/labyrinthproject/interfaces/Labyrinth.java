package com.progmatic.labyrinthproject.interfaces;

import com.progmatic.labyrinthproject.Coordinate;
import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import java.util.List;

/**
 *
 * @author pappgergely
 */
public interface Labyrinth {
    
    /**
     * Returns the labyrinth's width.
     * @return the number of columns in the labyrinth.
     */
    int getWidth();
    
    /**
     * Returns the labyrinth's height.
     * @return the number of rows in the labyrinth.
     */
    int getHeight();
    
    /**
     * Reads the given file and loads the file's data.
     * Should also set the player's position to the START cell. 
     * @param fileName the exact path and name of the file to load.
     */
    void loadLabyrinthFile(String fileName);
    
    /**
     * Returns the Cell's content at c.row, c.col
     * The top-left cell has (0, 0) coordinates.
     * @param c
     * @return the CellType which occupies the given coordinate
     * @throws CellException if c.row or c.col points to a non-existent index.
     */
    CellType getCellType(Coordinate c) throws CellException;
    
    /**
     * Sets the labyrinth to the given width and height. 
     * Resets all the cells to EMPTY state.
     * @param width The new width.
     * @param height The new height.
     */
    void setSize(int width, int height);
    
    /**
     * Sets a given cell coordinates to a cell type.
     * After calling a setCellType(c, ct), getCellType(c) should return ct.
     * If type equals START, should also set the player's position to c.
     * @param c the coordinate which must be overwritten.
     * @param type the type to write to the cell.
     * @throws CellException if c.row or c.col points to a non-existent index.
     */
    void setCellType(Coordinate c, CellType type) throws CellException;
    
    /**
     * @return the Coordinate object with the player's current position.
     */
    Coordinate getPlayerPosition();
    
    /**
     * Determines if the player has arrived on the END cell.
     * A player has finished if and only if they are on the END cell.
     * @return true if player has finished, false otherwise.
     */
    boolean hasPlayerFinished() throws CellException;
    
    /**
     * Returns a list of the directions the player could go.
     * @return a list of the possible directions the player could move.
     * The returned list will be always non-empty if a normal labyrinth is loaded.
     */
    List<Direction> possibleMoves() throws CellException;

    /**
     * Moves and updates the player's position in the given direction.
     * @param direction in which the player wants to move.
     * @throws InvalidMoveException if the player tries to move to an invalid cell (eg WALL).
     */
    public void movePlayer(Direction direction) throws InvalidMoveException, CellException;
    
}
