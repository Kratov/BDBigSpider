package Game;

import Graphics.Texture;
import Helpers.Timer;

public class Animation {
	private Texture[] frames; //Tecture of animation
	private int pointer; //Pointer of array
	
	//Frame delimeter
	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;

	public Animation(int amount, double fps, String fileName) {
		this.pointer = 0;
		this.elapsedTime = 0; 
		this.currentTime = 0;
		this.lastTime = Timer.getTime();
		this.fps = 1.0f/(double)fps;
		
		this.frames = new Texture[amount];
		for (int i = 0; i < amount; i++) {
			this.frames[i] = new Texture(".\\res\\img\\"+fileName+"\\"+i+".png");
		}
	}
	
	public void bind() { bind(0); }
	
	public void bind(int sampler) {
		this.currentTime = Timer.getTime();
		this.elapsedTime += currentTime - lastTime;
		if (elapsedTime >= fps) {
			elapsedTime = 0;
			pointer ++;
		}
		if (pointer >= frames.length) {
			pointer = 0;
		}
		this.lastTime = currentTime;
		
		frames[pointer].bind(sampler);
	}
}
