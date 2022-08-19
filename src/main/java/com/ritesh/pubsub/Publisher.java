package com.ritesh.pubsub;

public class Publisher
{
    int count = 0;

    Runnable runnable = () -> {
        synchronized (this)
        {
            try
            {
                while(Constant.data.size() <= Constant.MAX_CAPACITY)
                {
                    while(Constant.data.size() == 3)
                    {
                        this.wait();
                    }
                    Constant.data.add("Data "+count);
                    count++;
                    System.out.println("Publisher Thread: " + Thread.currentThread().getName());
                    System.out.println("Message Published | "+"Data "+count);
                    this.notifyAll();
                }
            }
            catch (InterruptedException ex){
                System.out.println(ex.getLocalizedMessage());
            }
        }
    };

    public void publishData()
    {
        new Thread(runnable).start();
    }
}
