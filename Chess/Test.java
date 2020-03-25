import java.util.List;

public class Test {
    public Test(){ }
    public static void test1() {
        Board b = Board.theBoard();
        Piece.registerPiece(new PawnFactory());
        Piece.registerPiece(new KingFactory());
        Piece.registerPiece(new QueenFactory());
        Piece p = Piece.createPiece("bp");
        System.out.println(p.moves(b, "d7"));


        //assert b.getPiece("a3") == p;
    }

    public static void testPawnMoves()
    {
        Board b = Board.theBoard();
        b.clear();
        Piece.registerPiece(new PawnFactory());
        Piece d2 = Piece.createPiece("wp");
        b.addPiece(d2, "d2");
        assert b.getPiece("d2") == d2;
        List<String> d2moves = d2.moves(b, "d2");
        // (d2 -> d3, d4)
        assert d2moves.size() == 2;
        assert d2moves.contains("d3");
        assert d2moves.contains("d4");

        Piece d3 = Piece.createPiece("wp");
        b.addPiece(d3, "d3");
        assert b.getPiece("d3") == d3;
        d2moves = d2.moves(b, "d2");
        List<String> d3moves = d3.moves(b, "d3");
        // d2 has no possible moves, (d3 -> d4)
        assert d2moves.size() == 0;
        assert d3moves.size() == 1;
        assert d3moves.contains("d4");

        Piece d4 = Piece.createPiece("bp");
        b.addPiece(d4, "d4");
        assert b.getPiece("d4") == d4;
        d2moves = d2.moves(b, "d2");
        d3moves = d3.moves(b, "d3");
        List<String> d4moves = d4.moves(b, "d4");
        // d2, d3, and d4 have no possible moves
        assert d2moves.size() == 0;
        assert d3moves.size() == 0;
        assert d4moves.size() == 0;

        Piece e3 = Piece.createPiece("bp");
        b.addPiece(e3, "e3");
        assert b.getPiece("e3") == e3;
        d2moves = d2.moves(b, "d2");
        List<String> e3moves= e3.moves(b, "e3");
        // (d2 -> e3), (e3 -> d2, e2)
        assert d2moves.size() == 1;
        assert d2moves.contains("e3");
        assert e3moves.size() == 2;
        assert e3moves.contains("d2");
        assert e3moves.contains("e2");
    }

    public static void test_queen() {
        Board b = Board.theBoard();

        Piece.registerPiece(new QueenFactory());
        Piece.registerPiece(new PawnFactory());

        Piece p1 = Piece.createPiece("bp");
        Piece p2 = Piece.createPiece("bp");
        Piece p3 = Piece.createPiece("bp");
        Piece p4 = Piece.createPiece("wp");
        Piece p5 = Piece.createPiece("wp");
        Piece p6 = Piece.createPiece("wp");
        Piece p7 = Piece.createPiece("bq");

        b.addPiece(p1, "a5");
        b.addPiece(p2, "a3");
        b.addPiece(p3, "c4");
        b.addPiece(p4, "e5");
        b.addPiece(p5, "d3");
        b.addPiece(p6, "c2");
        b.addPiece(p7, "c3");

        List<String> res = p7.moves(b, "c3");
        assert res.size() == 10;
        assert res.contains("b4");
        assert res.contains("b3");
        assert res.contains("b2");
        assert res.contains("a1");
        assert res.contains("c2");
        assert res.contains("d4");
        assert res.contains("d3");
        assert res.contains("d2");
        assert res.contains("e5");
        assert res.contains("e1");

    }

    public static void main(String[] args) throws Exception {

        test_queen();

    }
}
