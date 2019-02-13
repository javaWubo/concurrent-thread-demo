package a1;

/**
 * 等待超时模式
 * 应用场景:
 *          调用一个方法 当超过某个时刻 返回默认值
 */
public class WaitTimeOut {

    private static String result;

    public static void main(String[] args) throws InterruptedException {
        String r = getR(5000L,new WaitTimeOut());
        System.out.println(r);
    }

    private static String getR(long mills, WaitTimeOut waitTimeOut) throws InterruptedException {
        long future = System.currentTimeMillis() + mills;

        long waitTime = mills;
        String result = null;
        synchronized (waitTimeOut) {
            result = getResult();
            while ((result == null) && waitTime > 0) {
                waitTimeOut.wait(waitTime);
                result = getResult();
//            try {
////                wait(waitTime);
//
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(waitTime);
                waitTime = future - System.currentTimeMillis();
            }

        }

//        result = "默认值";

        return result;
    }


    public static String getResult() throws InterruptedException {

        Thread.sleep(10l);
        return "RESULT";
    }
}
