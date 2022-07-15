public class testThread {
    public static void main(String[] args) {
        for(int i = 0; i <= 5; i++){
            Multithread mythread = new Multithread(i);
            mythread.start();
        }
    }
}
