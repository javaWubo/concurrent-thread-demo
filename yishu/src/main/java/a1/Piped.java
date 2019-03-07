package a1;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道流 线程之间通过内存 进行数据传输
 */
public class Piped {

    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);

        Thread pipeReadThread = new Thread(new Print(in),"wb-in");
        pipeReadThread.start();
        int receive = 0;
        try{
            while ((receive = System.in.read())!=-1){
                out.write(receive);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }


    }
    static class Print implements Runnable{
        private PipedReader in;

        public Print(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int receive = 0;
            try{
                while ((receive = in.read())!=-1){
                    System.out.println((char) receive+"=====");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
