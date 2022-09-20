package com.ritesh.pubsub;

public class Publisher
{
    int count = 0;

    Runnable runnable = () -> {
        synchronized (this)
        {
            try
            {
                while(Constant.data.size() < Constant.MAX_CAPACITY)
                {
                    System.out.println("Data Size: "+Constant.data.size());
                    while(Constant.data.size() == 3)
                    {
                        this.wait();
                    }
                    String message = "Data "+count;
                    Constant.data.add(message);
                    count++;
                    String log = "Publisher Thread | " + Thread.currentThread().getName()
                            + " | Message Published | "+message;
                    System.out.println(log);
                    this.notifyAll();
                    Thread.sleep(1000);
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
