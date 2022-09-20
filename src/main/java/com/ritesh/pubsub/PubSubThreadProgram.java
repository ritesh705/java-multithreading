package com.ritesh.pubsub;

/*Program for producing and consuming message using Threads*/
public class PubSubThreadProgram
{
    int count = 0;

    public static void main(String[] args) throws InterruptedException {
        PubSubThreadProgram obj = new PubSubThreadProgram();
        new Thread(()-> obj.publishData()).start();
        new Thread(() -> obj.consumeData()).start();
    }

    void publishData()
    {
        synchronized (this)
        {
            try
            {
                for(int i=0; i<Constant.MAX_CAPACITY; i++)
                {
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
    }

    void consumeData()
    {
        synchronized (this) {
            try
            {
                for(int i=0; i<Constant.MAX_CAPACITY; i++)
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
    }

}
