import java.util.*;

public class Knight extends Piece {
    Knight(Color c) {
        this.color = c;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "n";
    }

    public List<String> moves(Board b, String loc) {
        List<String> dest = new ArrayList<>();
        for(int y = 1; y < 9;y++){
            for(char x = 'a'; x < 'i';x++){
                int y_dif = Math.abs(y - Character.getNumericValue(loc.charAt(1)));
                int x_dif = Math.abs(x - loc.charAt(0));
                if((x_dif == 1 && y_dif == 2)||(x_dif == 2 && y_dif == 1)) {
                    if(vacant_or_capture(b,x,y))
                        dest.add(toStr(x,y));
                }
            }
        }
        dest.remove(loc);
        return dest;
    }

}