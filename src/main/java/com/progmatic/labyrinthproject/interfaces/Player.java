package com.progmatic.labyrinthproject.interfaces;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;

/**
 *
 * @author pappgergely
 */
public interface Player {
    
    /**
     * Returns the player's next move.
     * This method should not modify the parameter l.
     * @param l
     * @return the player's next move. The returned direction should point to an EMTPY cell within l.
     * If the player has arrived on the END cell, it should return null.
     */
    Direction nextMove(Labyrinth l) throws CellException;

}
