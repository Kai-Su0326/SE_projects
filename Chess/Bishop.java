import java.util.*;

public class Bishop extends Piece {
    List<String> dest = new ArrayList<>();
    Bishop(Color color) {
        this.color = color;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "b";
    }

    void left_up(Board b, String sq) {
        char fir = (char)(sq.charAt(0)-1);
        char sec = (char)(sq.charAt(1)+1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            left_up(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
        }
    }

    void right_up(Board b, String sq) {
        char fir = (char)(sq.charAt(0)+1);
        char sec = (char)(sq.charAt(1)+1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            right_up(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
        }
    }

    void left_down(Board b, String sq) {
        char fir = (char)(sq.charAt(0)-1);
        char sec = (char)(sq.charAt(1)-1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            left_down(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
        }
    }

    void right_down(Board b, String sq) {
        char fir = (char)(sq.charAt(0)+1);
        char sec = (char)(sq.charAt(1)-1);
        String next = Character.toString(fir) + sec;
        if (!b.in_board(next)) { return; }
        if (vacant(b,next)) {
            dest.add(next);
            right_down(b,next);
        } else if (block(b,next)) {
            return;
        } else if (capture(b,next)) {
            dest.add(next);
        }
    }



    public List<String> moves(Board b, String loc) {
        left_up(b,loc);
        right_up(b,loc);
        left_down(b,loc);
        right_down(b,loc);
        return dest;
    }

}