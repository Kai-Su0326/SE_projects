import java.util.*;

public class Queen extends Piece {
    List<String> dest = new ArrayList<>();
    Queen(Color c) {
        this.color = c;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "q";
    }

    private void left(Board b, String sq) {
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

    private void right(Board b, String sq) {
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

    private void up(Board b, String sq) {
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

    private void down(Board b, String sq) {
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

    private void left_up(Board b, String sq) {
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
            return;
        }
    }

    private void right_up(Board b, String sq) {
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
            return;
        }
    }

    private void left_down(Board b, String sq) {
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
            return;
        }
    }

    private void right_down(Board b, String sq) {
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
            return;
        }
    }


    public List<String> moves(Board b, String loc) {
        left(b,loc);
        left_up(b,loc);
        up(b,loc);
        right_up(b,loc);
        right(b,loc);
        right_down(b,loc);
        down(b,loc);
        left_down(b,loc);
        return dest;
    }

}