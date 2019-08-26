package imageloader;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class MyStaticImageLoader {
	static BufferedImage[] sprites= new BufferedImage[20*20];
	
	public MyStaticImageLoader() {
		super();
		
	}
	
	public static void prepareImage(){
		//save a sprite version
		
		BufferedImage image;// = new BufferedImage(1, 1, 1);
		try {
			 image= ImageIO.read(new File("./images/SpriteSheetBig.png"));
			 Color color;
		for(int h=0; h<image.getHeight();h++){
			for(int w=0; w<image.getWidth();w++){
				color=new Color(image.getRGB(w, h));
				if(color.getRed()>200&&color.getBlue()>200&&color.getGreen()<25){
					image.setRGB(w, h, 0);
				}
			}
		}
		for(int a=0; a<20; a++){
			for(int b=0; b<20; b++){
				sprites[a*20+b]=image.getSubimage(b*60, a*51, 60, 51);
				try {
					ImageIO.write(sprites[a*20+b], "png", new File("./images/Sprite_"+(a*20+b)+".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public static BufferedImage getImage(int i){
		// this has to be made way more efficient: check!
		BufferedImage image = new BufferedImage(1, 1, 1);
		try {
			 image= ImageIO.read(new File("./images/Sprite_"+i+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public static BufferedImage getBackgroundImage(){
		BufferedImage image = new BufferedImage(1, 1, 1);
		try {
			 image= ImageIO.read(new File("./images/LandscapePicture"+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
