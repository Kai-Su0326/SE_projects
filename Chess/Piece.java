import javax.swing.*;
import java.util.*;

abstract public class Piece {
    protected Color color;
    private final static Map<Character,PieceFactory> map = new HashMap<>();

    static void registerPiece(PieceFactory pf) {
        map.put(pf.symbol(),pf);
    }

    static Piece createPiece(String name) {
            if (name.charAt(0) == 'b') {
                return map.get(name.charAt(1)).create(Color.BLACK);
            } else if (name.charAt(0) == 'w') {
                return map.get(name.charAt(1)).create(Color.WHITE);
            }
        throw new IllegalArgumentException();
    }

    String toStr(char c, int n){
        return Character.toString(c) + n;
    }


    Boolean vacant (Board b, String loc) {
        return b.getPiece(loc) == null;
    }

    Boolean capture(Board b, String loc) {
        return (b.getPiece(loc) != null && b.getPiece(loc).color() != this.color);
    }

    Boolean block(Board b, String loc) {
        return (b.getPiece(loc) != null && b.getPiece(loc).color() == this.color);
    }

    Boolean vacant_or_capture(Board b, char x, int y) {
        return b.getPiece(toStr(x,y)) == null || (b.getPiece(toStr(x,y)) != null
                && b.getPiece(toStr(x,y)).color() != this.color);
    }

    public Color color() {
	// You should write code here and just inherit it in
	// subclasses. For this to work, you should know
	// that subclasses can access superclass fields.
	    return color;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}