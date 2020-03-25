import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chess {
	private static boolean legal_layout (String s) {
		String regEx = "[a-h]{1}[1-8]{1}\\=[w,b]{1}[k,q,n,b,r,p]{1}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	private static boolean legal_move (String s) {
		String regEx = "[a-h][1-8]{1}\\-[a-h]{1}[1-8]{1}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

    public static void main(String[] args) throws Exception {
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	Piece.registerPiece(new KingFactory());
	Piece.registerPiece(new QueenFactory());
	Piece.registerPiece(new KnightFactory());
	Piece.registerPiece(new BishopFactory());
	Piece.registerPiece(new RookFactory());
	Piece.registerPiece(new PawnFactory());
	Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files
	// here
		Board board = Board.theBoard();
		try (Scanner file = new Scanner(new FileReader(args[0]))){
			while (file.hasNext()) {
				String s = file.nextLine();
				while(file.hasNext() && s.charAt(0) == '#') { s = file.nextLine(); }
				if(!legal_layout(s)) { throw new Exception(); }
				Piece piece = Piece.createPiece(s.substring(3));
				board.addPiece(piece, s.substring(0,2));
			}
		}

		try (Scanner file = new Scanner(new FileReader(args[1]))){
			while (file.hasNext()) {
				String s = file.nextLine();
				while(file.hasNext() && s.charAt(0) == '#') { s = file.nextLine(); }
				if(!legal_move(s)) { throw new Exception(); }
				String[] sq = s.split("-");
				String from = sq[0];
				String to = sq[1];
				board.movePiece(from,to);
			}
		}
//		board.addPiece();
		// Leave the following code at the end of the simulation:
	System.out.println("Final board:");
	Board.theBoard().iterate(new BoardPrinter());
    }
}