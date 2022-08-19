package com.ritesh.pubsub;

import java.util.ArrayList;
import java.util.List;

public class PubSub {
    int nextInitial = 0;
    private int initialCapacity = 10;
    private List<Integer> list = new ArrayList<>(initialCapacity);

    public static void main(String[] args) {
        PubSub threadSignaling = new PubSub();

        Thread publisher1 = new Thread(() -> threadSignaling.publisher());
        publisher1.start();

        Thread subscriber1 = new Thread(() -> threadSignaling.subscriber());
        subscriber1.start();

    }

    public void subscriber() {

        for (int i = 0; i < initialCapacity; i++) {
            synchronized (this) {
                try {
                    while (list.size() == 0) {
                        this.wait();
                    }
                    System.out.println(Thread.currentThread().getName()
                            + " " + nextInitial + ". Message subscribed..."
                            + list.get(0));
                    list.remove(0);
                    this.notifyAll();
                } catch (InterruptedException e) {
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void publisher() {

        for (int i = 0; i < initialCapacity; i++) {
            synchronized (this) {
                try {
                    while (list.size() == 3) {
                        this.wait();
                    }
                    list.add(nextInitial);
                    System.out.println(Thread.currentThread().getName()
                            + " " + nextInitial + ". Message published..."
                            + nextInitial);
                    nextInitial++;
                    this.notifyAll();
                } catch (InterruptedException e) {
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
