package com.ritesh.pubsub;

public class Consumer
{
    Runnable runnable = () ->
    {
        synchronized (this) {
            try
            {
                while(Constant.MAX_CAPACITY > 0)
                {
                    while(Constant.data.size() == 0)
                    {
                        this.wait();
                    }
                    String log = "Consumer Thread | " +Thread.currentThread().getName()
                            +" | Message Consumed | "+Constant.data.get(0);
                    System.out.println(log);
                    Constant.data.remove(0);
                    this.notifyAll();
                    Thread.sleep(1000);
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
