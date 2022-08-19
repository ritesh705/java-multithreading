package com.ritesh.pubsub;

public class Consumer
{
    Runnable runnable = () ->
    {
        synchronized (this) {
            try
            {
                while(Constant.data.size() > 0)
                {
                    while(Constant.data.size() == 0)
                    {
                        this.wait();
                    }
                    System.out.println("Consumer Thread: " + Thread.currentThread().getName());
                    System.out.println("Message Consumed: "+Constant.data.get(0));
                    Thread.sleep(500);
                    Constant.data.remove(0);
                    this.notifyAll();
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    };

    public void consumeData()
    {
        new Thread(runnable).start();
    }
}
