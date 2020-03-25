import java.util.*;

public class Rook extends Piece {
    List<String> dest = new ArrayList<>();
    Rook(Color c) {
        this.color = c;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "r";
    }

    void left(Board b, String sq) {
        char fir = (char)(sq.charAt(0)-1);
        char sec = sq.charAt(1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            left(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
            return;
        }
    }

    void right(Board b, String sq) {
        char fir = (char)(sq.charAt(0)+1);
        char sec = sq.charAt(1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            right(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
            return;
        }
    }

    void up(Board b, String sq) {
        char fir = sq.charAt(0);
        char sec = (char)(sq.charAt(1)+1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            up(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
            return;
        }
    }

    void down(Board b, String sq) {
        char fir = sq.charAt(0);
        char sec = (char)(sq.charAt(1)-1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            down(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
            return;
        }
    }

    public List<String> moves(Board b, String loc) {
        left(b,loc);
        right(b, loc);
        up(b,loc);
        down(b,loc);
        return dest;
    }

}