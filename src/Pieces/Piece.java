/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Pair;
import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public abstract class Piece {
    public Integer player;
    public Pair currentPosition;
    public Boolean selected;
    public Boolean taken;
    public Boolean hasTakenFirstMove;
    public abstract Vector<Pair> specialMoves();
    public abstract Vector<Pair> getPossibleMoves();
}
