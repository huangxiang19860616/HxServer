package Hx.HxServer.thread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by huangxiang on 2017/3/6 0006.
 */
public class LockTest {
    @Test
    public void lockTest() throws Exception {
        final Outputter output = new Outputter();

        new Thread()
        {
            @Override
            public void run() {
                output.output("zhangsan");
            }
        }.start();

        new Thread()
        {
            @Override
            public void run() {
                output.output("wangwu");
            }
        }.start();

        while (true);
    }

    private class Outputter {
        private Lock lock = new ReentrantLock();

        public void output(String name) {
            lock.lock();
            try {
                for(int i = 0; i < name.length(); i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println("");
            }
            finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void ReadWriteLockTest() throws Exception {
        final Data data = new Data();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        data.set(new Random().nextInt(30));
                    }
                }
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        data.get();
                    }
                }
            }).start();
        }

        while (true);
    }

    private class Data {
        private int data;// 共享数据
        private ReadWriteLock rwl = new ReentrantReadWriteLock();

        public void set(int data) {
            rwl.writeLock().lock();// 取到写锁
            try {
                System.out.println(Thread.currentThread().getName() + "准备写入数据");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.data = data;
                System.out.println(Thread.currentThread().getName() + "写入" + this.data);
            } finally {
                rwl.writeLock().unlock();// 释放写锁
            }
        }

        public void get() {
            rwl.readLock().lock();// 取到读锁
            try {
                System.out.println(Thread.currentThread().getName() + "准备读取数据");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "读取" + this.data);
            } finally {
                rwl.readLock().unlock();// 释放读锁
            }
        }
    }
}
