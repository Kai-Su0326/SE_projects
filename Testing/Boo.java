class Boo {
    private boolean input;
    Boo(boolean b){
        input = b;
    }

    Boo isEqualTo(boolean b2) {
        if(b2 != input) { throw new RuntimeException(); }
        return this;
    }

    Boo isTrue() {
        if(!input) { throw new RuntimeException(); }
        return this;
    }

    Boo isFalse() {
        if(input) { throw new RuntimeException(); }
        return this;
    }
}
