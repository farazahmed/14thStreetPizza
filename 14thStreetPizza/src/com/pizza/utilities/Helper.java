package com.pizza.utilities;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author Faraz
 * 
 *         This class will handle all the functions that will be used by most of
 *         the classes
 * 
 */
public class Helper {

	// Animation
	Animation animFadein;
	MediaPlayer objMediaPlayer;
	// singleton object
	public static Helper instance = null;

	/**
	 * @return Helper object.
	 */
	public static Helper getInstance() {

		if (instance == null) {

			return new Helper();
		}
		return instance;

	}

	public Helper() {

	}

	public void loadAnimation(Context context, int idAnimation, View view) {
		
		animFadein = AnimationUtils.loadAnimation(context, idAnimation);
		view.startAnimation(animFadein);

	}

	public void loadAnimationWithSound(Context context, int idAnimation, View view) {
		
		playSound(context);
		animFadein = AnimationUtils.loadAnimation(context, idAnimation);
		view.startAnimation(animFadein);

	}
	
	public void playSound(Context context) {

		objMediaPlayer = MediaPlayer.create(context, com.pizza.activities.R.raw.buttonone);
		objMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

             @Override
             public void onCompletion(MediaPlayer mp) {
                 mp.release();
             }

         });   
		objMediaPlayer.start();

	}
	

	
	
	
}
