/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

/**
 * Represents the user's intent for clicking a Tile.
 * @author nehalpatel
 */
public enum ClickType {

    /**
     * User has selected a Piece to move.
     */
    Selection,

    /**
     * User wants to move a Piece to a new Tile.
     */
    Move,

    /**
     * User no longer has a Piece selected.
     */
    Deselection,

    /**
     * Nothing at this point. If you run into this, something bizarre happened.
     */
    Other,

}
