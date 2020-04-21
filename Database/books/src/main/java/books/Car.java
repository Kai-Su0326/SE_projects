package books;

import jrails.Column;
import jrails.Model;

public class Car extends Model {
    @Column
    public String color;

    @Column
    public String name;

    @Column
    public int age;

    @Column
    public boolean isSleeping;
}
