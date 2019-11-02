/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Images;

import Enums.PieceType;
import Enums.ThemeColor;
import Enums.ThemeType;
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

    public static HashMap<PieceType, HashMap<ThemeColor, ImageIcon>> Icons = new HashMap<>();

    /**
     * Initialize all the image mappings for the chess Pieces.
     */
    public void loadImages(ThemeType Theme) {
        //Declare strings for the file paths to each Piece Icon
        //Set as default icon file path
        String whitePawnPath = "src/Images/White_pawn.png"; 
        String whiteRookPath = "src/Images/White_castle.png";
        String whiteKnightPath = "src/Images/White_horse.png";
        String whiteBishopPath = "src/Images/White_bishop.png";
        String whiteQueenPath = "src/Images/White_queen.png";
        String whiteKingPath = "src/Images/White_king.png";
        String blackPawnPath = "src/Images/Black_pawn.png";
        String blackRookPath = "src/Images/Black_castle.png";
        String blackKnightPath = "src/Images/Black_horse.png";
        String blackBishopPath = "src/Images/Black_bishop.png";
        String blackQueenPath = "src/Images/Black_queen.png";
        String blackKingPath = "src/Images/Black_king.png";
        
        
        switch(Theme){
            case Normal:    //Do nothing and keep default file paths for images
                break;
            case Doki:      //Reassign file paths to Doki Theme images
                whitePawnPath = "src/Images/White_pawn.png";
                whiteRookPath = "src/Images/DokiWhiteRook.png";
                whiteKnightPath = "src/Images/DokiWhiteKnight.png";
                whiteBishopPath = "src/Images/DokiWhiteBishop.png";
                whiteQueenPath = "src/Images/DokiWhiteQueen.png";
                whiteKingPath = "src/Images/DokiWhiteKing.png";
                blackPawnPath = "src/Images/Black_pawn.png";
                blackRookPath = "src/Images/DokiDarkRook.png";
                blackKnightPath = "src/Images/DokiDarkKnight.png";
                blackBishopPath = "src/Images/DokiDarkBishop.png";
                blackQueenPath = "src/Images/DokiDarkQueen.png";
                blackKingPath = "src/Images/DokiDarkKing.png";
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
