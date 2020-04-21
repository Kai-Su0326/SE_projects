package books;

import jrails.Model;

public class Test {
    public static void main(String[] args) {
        Book b = new Book();
        b.title = null;
        b.author = "Kai";
        b.num_copies = 132;
        b.save();

        Book t = new Book();
        t.title = "hhh";
        t.author = null;
        t.num_copies = 22;
        t.save();

        Car bb = new Car();
        bb.color = "WHITE";
        bb.name = "cat";
        bb.age = 1;
        bb.isSleeping = true;
        bb.save();

        Car cc = new Car();
        cc.color = "color";
        cc.name = "name";
        cc.age = 2;
        cc.isSleeping = false;
        cc.save();


        //Book b_found = Model.find(Book.class, b.id());
        //Book t_found = Model.find(Book.class, t.id());
//        Book t_found = Model.find(Book.class, t.id());
        //System.out.println(b_found.title);
        //System.out.println(t_found.author == null);
        Book c_found = Model.find(Book.class, t.id());
        Book tt = Model.find(Book.class,t.id());
        System.out.println(c_found.author);
        System.out.println(tt.author);
//        System.out.println("t_found.id=" + t_found.id());

        //System.out.println(Model.all(Book.class).get(0).author);

    }
}
