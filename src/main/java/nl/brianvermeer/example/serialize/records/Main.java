package nl.brianvermeer.example.serialize.records;

import java.io.*;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        var value =  new TwoValue("One", "Two");
        var value = new Gadget(new Command("ls -l"));
        var filename = "twovalue.ser";

        var filter1 = ObjectInputFilter.allowFilter(cl -> cl.getPackageName().contentEquals("nl.brianvermeer.example.serialize.records"), ObjectInputFilter.Status.REJECTED);
        ObjectInputFilter.Config.setSerialFilter(filter1);
        ObjectInputFilter.Config.setSerialFilterFactory((f1, f2) -> ObjectInputFilter.merge(f2,f1));

        //same filter but with println statements
//        ObjectInputFilter.Config.setSerialFilterFactory((f1, f2) -> {
//            System.out.println("f1 =" + f1);
//            System.out.println("f2 =" + f2);
//            var merged = ObjectInputFilter.merge(f2,f1);
//            System.out.println("merged = " + merged);
//            return merged;
//        });

        serialize(value, filename);
//        deserialize(filename);
        safeDeserialize(filename);
    }

    public static void serialize(Object value, String filename) throws IOException {
        System.out.println("---serialize");
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(value);
        out.close();
        fileOut.close();
    }

    public static void deserialize(String filename) throws IOException, ClassNotFoundException {
        System.out.println("---deserialize");
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        TwoValue tv = (TwoValue) in.readObject();
        System.out.println(tv);
    }


    public static void safeDeserialize(String filename) throws IOException, ClassNotFoundException {
        System.out.println("---safe deserialize");
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        //add context specific filter
        ObjectInputFilter intFilter = ObjectInputFilter.allowFilter(cl -> cl.equals(Integer.class), ObjectInputFilter.Status.UNDECIDED);
        in.setObjectInputFilter(intFilter);

        TwoValue tv = (TwoValue) in.readObject();
        System.out.println(tv);
    }
}
