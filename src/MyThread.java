public class MyThread implements Runnable
{
    String str;
    public MyThread(String str)
    {
        this.str = str;
    }

    @Override
    public void run()
    {
        System.out.println(str);
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        MyThread object1 = new MyThread("First Thread.");
        Thread t1 = new Thread(object1);
        MyThread object2 = new MyThread("Second Thread.");
        Thread t2 = new Thread(object2);
        t1.start();
        t2.start();
    }
}