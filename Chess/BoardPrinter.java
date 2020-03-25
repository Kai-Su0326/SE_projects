public class BoardPrinter implements BoardExternalIterator {
    public void visit(String loc, Piece p) {
	if (p != null) {
	    System.out.println(loc + "=" + p.toString());
	}
    }
}