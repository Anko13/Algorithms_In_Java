import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PermutationFile {
    

public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> rq = new RandomizedQueue<>();
    
    try {
        System.setIn(new FileInputStream("D:\\Java\\Algoritms in java\\Workspace\\Queue\\test.txt"));
    } catch (FileNotFoundException e) {
             e.printStackTrace();
    }
while (!StdIn.isEmpty()) rq.enqueue(StdIn.readString()); 
while (k-- > 0) StdOut.println(rq.dequeue());
}

}
