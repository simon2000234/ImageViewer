package imageviewerproject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable
{

    private int currentImageIndex = 0;
    private Runnable task;
    private ExecutorService executor;
    private Scheduler sch;

    @FXML
    Parent root;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnNext;

    @FXML
    private ImageView imageView;
    @FXML
    private Label ImageName;

    private void handleBtnLoadAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        List<Image> images = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                fileNames.add(f.getName());
                images.add(new Image(f.toURI().toString()));
            });
        }
        Slideshow ss = new Slideshow(images, imageView, ImageName, fileNames);
        sch.addSlideshow(ss);

    }

//    private void handleBtnPreviousAction(ActionEvent event)
//    {
//        if (!images.isEmpty())
//        {
//            currentImageIndex
//                    = (currentImageIndex - 1 + images.size()) % images.size();
//            displayImage();
//        }
//    }
//
//    private void handleBtnNextAction(ActionEvent event)
//    {
//        if (!images.isEmpty())
//        {
//            currentImageIndex = (currentImageIndex + 1) % images.size();
//            displayImage();
//        }
//    }

    private void displayImage(List<Image> images, List<String> fileNames)
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
            ImageName.setText("Image Name: " + fileNames.get(currentImageIndex));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btnLoad.setOnAction((ActionEvent event) ->
        {
            handleBtnLoadAction(event);
        });

//        btnPrevious.setOnAction((ActionEvent event) ->
//        {
//            handleBtnPreviousAction(event);
//        });
//
//        btnNext.setOnAction((ActionEvent event) ->
//        {
//            handleBtnNextAction(event);
//        });
        sch = new Scheduler();
        task = sch;

    }

    @FXML
    private void handleStartSlideShow(ActionEvent event)
    {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
    }

    @FXML
    private void handleStopSlideshow(ActionEvent event)
    {
        
        sch.stopSlideshow();
//        try
//        {
//            executor.shutdown();
//            executor.awaitTermination(3, TimeUnit.SECONDS);
//        }
//        catch (InterruptedException ex)
//        {
//            System.out.println("The Thread has stoped");
//        }
//        finally
//        {
//            if (!executor.isTerminated())
//            {
//                executor.shutdownNow();
//            }
//        }
    }

}
