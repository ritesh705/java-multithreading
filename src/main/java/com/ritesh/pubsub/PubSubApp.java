package com.ritesh.pubsub;

import java.util.ArrayList;

public class PubSubApp
{
    public int MAX_CAPACITY = 10;
    public ArrayList<String> data = new ArrayList<>(MAX_CAPACITY);
    int count = 0;

    public static void main(String[] args) throws InterruptedException
    {
        PubSubApp pubsubApp = new PubSubApp();
        new Thread(()-> pubsubApp.publishData()).start();
        new Thread(() -> pubsubApp.consumeData()).start();
    }

    void publishData()
    {
        synchronized (this)
        {
            try
            {
                for(int i=0; i<MAX_CAPACITY; i++)
                {
                    while(data.size() == 3)
                    {
                        this.wait();
                    }
                    data.add("Data "+count);
                    count++;
                    System.out.println("Publisher Thread: " + Thread.currentThread().getName());
                    System.out.println("Message Published | "+"Data "+count);
                    this.notifyAll();
                    Thread.sleep(100);
                }
            }
            catch (InterruptedException ex){
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    void consumeData()
    {
        synchronized (this) {
            try
            {
                for(int i=0; i<MAX_CAPACITY; i++)
                {
                    while(data.size() == 0)
                    {
                        this.wait();
                    }
                    System.out.println("Consumer Thread: " + Thread.currentThread().getName());
                    System.out.println("Message Consumed: "+data.get(0));
                    data.remove(0);
                    this.notifyAll();
                    Thread.sleep(100);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
