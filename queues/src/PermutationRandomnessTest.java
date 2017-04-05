import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PermutationRandomnessTest {
    
    static String test(){
        
        RandomizedQueue<String> rq = new RandomizedQueue<>();
            rq.enqueue("A");
            rq.enqueue("B");
            rq.enqueue("C");
            return rq.dequeue()+rq.dequeue()+rq.dequeue();
    }

    public static void main(String[] args) {
        int n = 10;
        while (n-->0){System.out.println(test());}
   
    }
    }

