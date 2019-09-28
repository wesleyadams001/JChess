/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Images;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Wesley
 * Contains the mappings for chess pieces
 */
public class Images {

	public ImageIcon whitePawnImage;
	public ImageIcon whiteCastleImage;
	public ImageIcon whiteHorseImage;
	public ImageIcon whiteBishopImage;
	public ImageIcon whiteQueenImage;
	public ImageIcon whiteKingImage;
	public ImageIcon whiteWazirImage;
	public ImageIcon whiteBerolinaImage;
	public ImageIcon blackPawnImage;
	public ImageIcon blackCastleImage;
	public ImageIcon blackHorseImage;
	public ImageIcon blackBishopImage;
	public ImageIcon blackQueenImage;
	public ImageIcon blackKingImage;
	public ImageIcon blackWazirImage;
	public ImageIcon blackBerolinaImage;
	
	/**
	 * Initialize all the images image mappings for the
         * chess pieces
	 */
	public Images() {
		
		//white pawn
		try {
			whitePawnImage = new ImageIcon(ImageIO.read(new File("src/Images/White_pawn.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white castle
		try {
			whiteCastleImage = new ImageIcon(ImageIO.read(new File("src/Images/White_castle.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white horse
		try {
			whiteHorseImage = new ImageIcon(ImageIO.read(new File("src/Images/White_horse.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white bishop
		try {
			whiteBishopImage = new ImageIcon(ImageIO.read(new File("src/Images/White_bishop.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white queen
		try {
			whiteQueenImage = new ImageIcon(ImageIO.read(new File("src/Images/White_queen.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white king
		try {
			whiteKingImage = new ImageIcon(ImageIO.read(new File("src/Images/White_king.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white wazir
		try {
			whiteWazirImage = new ImageIcon(ImageIO.read(new File("src/Images/White_wazir.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//white berolina
		try {
			whiteBerolinaImage = new ImageIcon(ImageIO.read(new File("src/Images/White_berolina.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black pawn
		try {
			blackPawnImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_pawn.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black castle
		try {
			blackCastleImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_castle.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black horse
		try {
			blackHorseImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_horse.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black bishop
		try {
			blackBishopImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_bishop.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black queen
		try {
			blackQueenImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_queen.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black king
		try {
			blackKingImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_king.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black wazir
		try {
			blackWazirImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_wazir.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//black berolina
		try {
			blackBerolinaImage = new ImageIcon(ImageIO.read(new File("src/Images/Black_berolina.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
