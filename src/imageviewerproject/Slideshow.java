/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewerproject;

import java.util.List;
import java.util.concurrent.TimeUnit;
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

    public Slideshow(List<Image> images, ImageView imageView)
    {
        this.images = images;
        this.imageView = imageView;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                currentImageIndex = (currentImageIndex + 1) % images.size();
                imageView.setImage(images.get(currentImageIndex));
                TimeUnit.SECONDS.sleep(2);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        }
    }
}


