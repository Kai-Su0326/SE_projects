import java.util.*;

public class King extends Piece {
    King(Color c) {
        this.color = c;
    }
    // implement appropriate methods

    public String toString() {
        char co = color() == Color.WHITE ? 'w':'b';
        return co + "k";
    }

    public List<String> moves(Board b, String loc) {
        List<String> dest = new ArrayList<>();
        for(int y = 1; y < 9;y++){
            for(char x = 'a'; x < 'i'; x++){
                if (Math.abs(x - loc.charAt(0)) <= 1 && Math.abs(y - Character.getNumericValue(loc.charAt(1))) <= 1) {
                    if(vacant_or_capture(b,x,y))
                        dest.add(toStr(x,y));
                }
            }
        }
        dest.remove(loc);
        return dest;
    }

}