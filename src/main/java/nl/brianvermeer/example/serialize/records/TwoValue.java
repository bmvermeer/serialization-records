package nl.brianvermeer.example.serialize.records;

import java.io.Serializable;

public record TwoValue(String a, String b) implements Serializable {

    public TwoValue(String a, String b) {
        System.out.println("creating a twovalue via constructor");
        this.a = a;
        this.b = b;
    }
}
