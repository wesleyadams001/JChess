/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Images;

import Jchess.Enums.PieceType;
import Jchess.Enums.ThemeColor;
import Jchess.Enums.ThemeType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Wesley
 * Contains the mappings for chess pieces
 */
public class Images {

    /**
     * Prevent instantiation.
     */
    private Images() { }

    public static HashMap<PieceType, HashMap<ThemeColor, ImageIcon>> Icons = new HashMap<>();

    /**
     * Read Piece images into memory.
     * @param Theme The Piece theme to load.
     */
    public static void loadImages(ThemeType Theme) {
        //Declare strings for the file paths to each Piece Icon
        //Set as default icon file path
        String whitePawnPath = "src/Jchess/Images/White_pawn.png"; 
        String whiteRookPath = "src/Jchess/Images/White_castle.png";
        String whiteKnightPath = "src/Jchess/Images/White_horse.png";
        String whiteBishopPath = "src/Jchess/Images/White_bishop.png";
        String whiteQueenPath = "src/Jchess/Images/White_queen.png";
        String whiteKingPath = "src/Jchess/Images/White_king.png";
        String blackPawnPath = "src/Jchess/Images/Black_pawn.png";
        String blackRookPath = "src/Jchess/Images/Black_castle.png";
        String blackKnightPath = "src/Jchess/Images/Black_horse.png";
        String blackBishopPath = "src/Jchess/Images/Black_bishop.png";
        String blackQueenPath = "src/Jchess/Images/Black_queen.png";
        String blackKingPath = "src/Jchess/Images/Black_king.png";
        
        
        switch(Theme){
            case Normal:    //Do nothing and keep default file paths for images
                break;
            case Doki:      //Reassign file paths to Doki Theme images
                whitePawnPath = "src/Jchess/Images/KirbyWhitePawn.png";
                whiteRookPath = "src/Jchess/Images/DokiWhiteRook.png";
                whiteKnightPath = "src/Jchess/Images/DokiWhiteKnight.png";
                whiteBishopPath = "src/Jchess/Images/DokiWhiteBishop.png";
                whiteQueenPath = "src/Jchess/Images/DokiWhiteQueen.png";
                whiteKingPath = "src/Jchess/Images/DokiWhiteKing.png";
                blackPawnPath = "src/Jchess/Images/KirbyDarkPawn.png";
                blackRookPath = "src/Jchess/Images/DokiDarkRook.png";
                blackKnightPath = "src/Jchess/Images/DokiDarkKnight.png";
                blackBishopPath = "src/Jchess/Images/DokiDarkBishop.png";
                blackQueenPath = "src/Jchess/Images/DokiDarkQueen.png";
                blackKingPath = "src/Jchess/Images/DokiDarkKing.png";
                break;
            default:
                break;      //If something goes wrong keep default file paths
        }
        
        //Initialize the hashmap used for image icons
        try {
            HashMap<ThemeColor, ImageIcon> pawnIcons = new HashMap<>();
            pawnIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whitePawnPath))));
            pawnIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackPawnPath))));
            Icons.put(PieceType.Pawn, pawnIcons);

            HashMap<ThemeColor, ImageIcon> rookIcons = new HashMap<>();
            rookIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whiteRookPath))));
            rookIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackRookPath))));
            Icons.put(PieceType.Rook, rookIcons);

            HashMap<ThemeColor, ImageIcon> knightIcons = new HashMap<>();
            knightIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whiteKnightPath))));
            knightIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackKnightPath))));
            Icons.put(PieceType.Knight, knightIcons);

            HashMap<ThemeColor, ImageIcon> bishopIcons = new HashMap<>();
            bishopIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whiteBishopPath))));
            bishopIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackBishopPath))));
            Icons.put(PieceType.Bishop, bishopIcons);

            HashMap<ThemeColor, ImageIcon> queenIcons = new HashMap<>();
            queenIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whiteQueenPath))));
            queenIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackQueenPath))));
            Icons.put(PieceType.Queen, queenIcons);

            HashMap<ThemeColor, ImageIcon> kingIcons = new HashMap<>();
            kingIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File(whiteKingPath))));
            kingIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File(blackKingPath))));
            Icons.put(PieceType.King, kingIcons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
