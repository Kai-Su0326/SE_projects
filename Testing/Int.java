class Int {

    private int input;
    Int(int i){
        input = i;
    }

    Int isEqualTo(int i2) {
        if(input != i2) { throw new RuntimeException(); }
        return this;
    }

    Int isLessThan(int i2) {
        if(input >= i2) { throw new RuntimeException(); }
        return this;
    }

    Int isGreaterThan(int i2) {
        if(input <= i2) { throw new RuntimeException(); }
        return this;
    }

}
