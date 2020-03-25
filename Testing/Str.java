class Str{
    private String input;
    Str(String s){
        this.input = s;
    }

    Str isNotNull() {
        if(input == null) { throw new RuntimeException(); }
        return this;
    }

    Str isNull() {
        if(input != null) { throw new RuntimeException(); }
        return this;
    }

    Str isEqualTo(Object o2) {
        if(o2 != null && !o2.equals(input)) { throw new RuntimeException(); }
        if(o2 == null && input != null) {throw new RuntimeException();}
        return this;
    }

    Str isNotEqualTo(Object o2) {
        if(o2 != null && o2.equals(input)) { throw new RuntimeException(); }
        if(o2 == null && input == null) {throw new RuntimeException();}
        return this;
    }

    Str startsWith(String s2) {
        int len = s2.length();
        if(!s2.equals(input.substring(0,len))) {
            throw new RuntimeException();
        }
        return this;
    }

    Str isEmpty() {
        if(!input.isEmpty()) {
            throw new RuntimeException();
        }
        return this;
    }

    Str contains(String s2) {
        if(!input.contains(s2)) {
            throw new RuntimeException();
        }
        return this;
    }

}
