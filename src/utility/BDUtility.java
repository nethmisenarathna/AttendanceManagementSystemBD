/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author USER
 */
public class BDUtility {
    
    public static void setImage(JFrame frame, String imagepath, int newWidth, int newHeight){
        
        try{
            BufferedImage originalImage = ImageIO.read(BDUtility.class.getResourceAsStream(imagepath));
            BufferedImage resizedImage =new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            resizedImage.createGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIcon backgroundImage =new ImageIcon(resizedImage);
            JLabel backgroundLabel =new JLabel(backgroundImage);
            backgroundLabel.setBounds(0,0,newWidth,newHeight);
            frame.getContentPane().add(backgroundLabel, BorderLayout.CENTER);
            frame.validate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private static HashMap<String, JFrame> formMap = new HashMap<>();
    
    public static void openForm(String formName, JFrame formInstance){
        JFrame existingForm = formMap.get(formName);
        if(existingForm==null || !existingForm.isVisible()){
            formMap.put(formName, formInstance);
            formInstance.setVisible(true);
        }else{
            existingForm.toFront();
        }
    }
    
    public static String getPath(String finalPath){
        String projectPath = System.getProperty("user.dir");
        return projectPath + "\\src\\" +finalPath;
    }
    
    public static String getFileExtension(String fileName){
        int lastDotIndex =fileName.lastIndexOf(".");
        if(lastDotIndex!= -1){
            return fileName.substring(lastDotIndex+ 1);
        }
        return "";
    }
    
    public static BufferedImage scaleImage(BufferedImage originalImage, BufferedImage selectedImage){
        int width = selectedImage.getWidth();
        int height = selectedImage.getHeight();
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        scaledImage.createGraphics().drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return scaledImage;
        
    }
}
