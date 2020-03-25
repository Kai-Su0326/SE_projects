import java.util.*;

public class Pawn extends Piece {
    private List<String> dest = new ArrayList<>();

    Pawn(Color c) {
        this.color = c;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "p";
    }

    private void black (Board b, String loc) {
        char x = (char)(loc.charAt(0) + 1);
        char n = (char)(loc.charAt(1) - 1);
        String left_tar = Character.toString(x) + n;
        boolean edible_left = (b.in_board(left_tar) && b.getPiece(left_tar) != null && b.getPiece(left_tar).color() != this.color);
        x = (char)(loc.charAt(0) - 1);
        String right_tar = Character.toString(x) + n;
        boolean edible_right = (b.in_board(right_tar) && b.getPiece(right_tar) != null && b.getPiece(right_tar).color() != this.color);
        if (edible_left) { dest.add(left_tar); }
        if (edible_right) { dest.add(right_tar); }
        boolean safe_row = (loc.charAt(1) == '7');
        x = loc.charAt(0);
        String front = Character.toString(x) + n;
        boolean forward = (b.in_board(front) && b.getPiece(front) == null);
        if(forward) { dest.add(front); }
        n = (char)(loc.charAt(1) - 2);
        String front_2 = Character.toString(x) + n;
        boolean forward_2 = (safe_row && b.getPiece(front_2) == null);
        if (forward && forward_2) { dest.add(front_2); }
    }

    private void white (Board b, String loc) {
        char x = (char)(loc.charAt(0) - 1);
        char n = (char)(loc.charAt(1) + 1);
        String left_tar = Character.toString(x) + n;
        boolean edible_left = (b.in_board(left_tar) && b.getPiece(left_tar) != null && b.getPiece(left_tar).color() != this.color);
        x = (char)(loc.charAt(0) + 1);
        String right_tar = Character.toString(x) + n;
        boolean edible_right = (b.in_board(right_tar) && b.getPiece(right_tar) != null && b.getPiece(right_tar).color() != this.color);
        if (edible_left) { dest.add(left_tar); }
        if (edible_right) { dest.add(right_tar); }
        boolean safe_row = (loc.charAt(1) == '2');
        x = loc.charAt(0);
        String front = Character.toString(x) + n;
        boolean forward = (b.in_board(front) && b.getPiece(front) == null);
        if(forward) { dest.add(front); }
        n = (char)(loc.charAt(1) + 2);
        String front_2 = Character.toString(x) + n;
        boolean forward_2 = (safe_row && b.getPiece(front_2) == null);
        if (forward && forward_2) { dest.add(front_2); }
    }

    public List<String> moves(Board b, String loc) {
        if(this.color() == Color.BLACK) { black(b,loc); }
        else { white(b,loc); }
        return dest;
    }

}