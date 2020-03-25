import java.util.*;

public class Board {

    private Piece[][] pieces = new Piece[8][8];
    private final Set<Integer> set;
    private List<BoardListener> BLlist = new LinkedList<>();
    private final static Board board = new Board();
    private Board() {
        Integer[] arr = {0,1,2,3,4,5,6,7};
        set = new HashSet<>(Arrays.asList(arr));
    }
    private int fir(String ind) {
        char row = ind.charAt(1);
        return Math.abs(Character.getNumericValue(row) - 8);
    }

    private int sec(String ind) {
        char col = ind.charAt(0);
        return col - 97;
    }

    boolean in_board(String loc){
        return set.contains(fir(loc)) && set.contains(sec(loc)) && Character.getNumericValue(loc.charAt(1)) < 9;
    }


    static Board theBoard() {
        return board;
    }

    // Returns piece at given loc or null if no such piece
    // exists
    Piece getPiece(String loc) {
        if(in_board(loc)) {
            return pieces[fir(loc)][sec(loc)];
        }
        System.out.println(loc);
        throw new IllegalArgumentException();
    }

    void addPiece(Piece p, String loc) {
        if(in_board(loc) && pieces[fir(loc)][sec(loc)] == null){
            pieces[fir(loc)][sec(loc)] = p;
            return;
        }
        throw new RuntimeException();
    }

    public void movePiece(String from, String to) {
	    if(in_board(from) && in_board(to)) {
	        if(getPiece(from) != null && getPiece(from).moves(this,from).contains(to)) {
	            if (getPiece(to) == null) {
                    for(BoardListener x : BLlist) {
                        x.onMove(from,to,getPiece(from));
                    }
                } else {
                    for(BoardListener x : BLlist) {
                        x.onMove(from,to,getPiece(from));
                        x.onCapture(getPiece(from),getPiece(to));
                    }
                }
                pieces[fir(to)][sec(to)] = pieces[fir(from)][sec(from)];
                pieces[fir(from)][sec(from)] = null;
                return;
            }
        }
	    throw new RuntimeException();
    }

    public void clear() {
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                pieces[i][j] = null;
            }
        }
    }

    public void registerListener(BoardListener bl) { BLlist.add(bl); }

    public void removeListener(BoardListener bl) { BLlist.remove(bl); }

    public void removeAllListeners() { BLlist.clear(); }

    private String ind_str(int i,int j) {
        char sec = (char)(i + 49);
        char fir = (char)(j + 97);
        return Character.toString(fir) + sec;
    }

    public void iterate(BoardExternalIterator bi) {
	    for(int i = 0; i < 8; i++){
	        for(int j = 0; j < 8; j++){
	            String loc = ind_str(i,j);
	            bi.visit(loc,getPiece(loc));
            }
        }
    }

}