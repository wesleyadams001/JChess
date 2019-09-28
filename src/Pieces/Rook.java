/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.Color;
import Enums.MoveType;
import Images.Images;
import Player.Player;
import static Images.Images.castleIcons;
import java.util.Vector;

/**
 *
 * @author jonathanjoiner
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner);
        image = castleIcons.get(owner.getColor());
    }

    @Override
    public Vector<Pair> specialMoves(Board board, Piece p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();

        // throw new UnsupportedOperationException("Has taken first move. can't castle");

        return moves;
    }

}
