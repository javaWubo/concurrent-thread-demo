package a7.ys7_1;


import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static AtomicReference<User> atomicReference = new AtomicReference<>();
    public static void main(String[] args) {
        User user = new User("wubo",13);
        atomicReference.set(user);
        User updateUser = new User("zhangsan",14);
       boolean r =  atomicReference.compareAndSet(user,updateUser);
        System.out.println("return r = {"+r+"} user = name = "+atomicReference.get().getName()+" age = "+atomicReference.get().getAge());
        System.out.println("return r = {"+r+"} user = "+ JSONObject.toJSONString(atomicReference));
    }


    static class User{
        private String name;
        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("User{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age=").append(age);
            sb.append('}');
            return sb.toString();
        }
    }
}
