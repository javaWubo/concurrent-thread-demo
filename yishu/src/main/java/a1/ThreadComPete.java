package a1;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ThreadComPete {
    static Object lock = new Object();

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(new R(i),"Thread-wb-"+i).start();
        }
    }

    public static class  R implements Runnable{
         int i = 0;
        public R(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (lock){

                System.out.println(Thread.currentThread().getName()+":begin");
                Map<String,Object> map = new HashMap<String,Object>();
                for (int j = 0; j <1000 ; j++) {
                    Object o = new Object();
                    map.put(j+"",o);
                }
                try {
//                    System.out.println(Thread.currentThread().getName()+":end map is :"+JSONObject.toJSONString(map) );
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
