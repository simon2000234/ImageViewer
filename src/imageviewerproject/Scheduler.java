/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewerproject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melchertsen
 */
public class Scheduler implements Runnable
{

    private Slideshow currentSlideshow;
    private BlockingQueue<Slideshow> queue = new LinkedBlockingDeque<>();
    private ExecutorService executor;

    @Override
    public void run()
    {

        try
        {
            while (true)
            {
                if (!queue.isEmpty())
                {
                    runNextSlideShow();
                }
                TimeUnit.SECONDS.sleep(6);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Slideshow stopped");
        }
    }

    public synchronized void runNextSlideShow() throws InterruptedException
    {
        if (currentSlideshow != null)
        {
            currentSlideshow.stop();
            queue.put(currentSlideshow);
        }
        currentSlideshow = queue.take();
        currentSlideshow.start();
    }

    public void addSlideshow(Slideshow ss)
    {
        try
        {
            queue.put(ss);
        }
        catch (InterruptedException ex)
        {
            System.out.println("Something went wrong at addSlideshow");
        }
    }

}
