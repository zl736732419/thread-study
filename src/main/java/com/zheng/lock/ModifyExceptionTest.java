package com.zheng.lock;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 更新操作通过iterator来进行
 * 或者将list修改为CopyOnWriteArrayList来进行
 * Created by zhenglian on 2016/10/2.
 */
public class ModifyExceptionTest {
    
    public static void main(String[] args) {
//        List<Integer> list = new ArrayList();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        Integer value = null;
        while(it.hasNext()) {
            value = it.next();
            if(value == 1) {
                list.remove(value);
//                it.remove(); //通过it删除是没有问题的，但是通过list删除会存在修改异常，可以使用CopyOnWriteArrayList代替
            } else {
                System.out.println(value);
            }
        }

    }
}
