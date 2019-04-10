/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewerproject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Melchertsen
 */
public class Slideshow implements Runnable
{

    private List<Image> images;
    private ImageView imageView;
    private int currentImageIndex = 0;
    private Label label;
    private List<String> names;
    private ExecutorService executor;
    private Runnable task;

    public Slideshow(List<Image> images, ImageView imageView, Label label, List<String> names)
    {
        this.images = images;
        this.imageView = imageView;
        this.label = label;
        this.names = names;
        task = this;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Platform.runLater(() ->
                {
                    imageView.setImage(images.get(currentImageIndex));
                    label.setText("Image Name: " + names.get(currentImageIndex));
                });
                currentImageIndex = (currentImageIndex + 1) % images.size();
                TimeUnit.SECONDS.sleep(2);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        }
    }

    public synchronized void start()
    {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
    }

    public synchronized void stop()
    {
        try
        {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);

        }
        catch (InterruptedException ex)
        {
            System.out.println("the thread has stopped");
        }
        finally
        {
            if (!executor.isTerminated())
            {
                executor.shutdownNow();
            }
        }
    }
}
