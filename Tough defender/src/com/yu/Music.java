package com.yu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yu.R;


import android.content.Context;
import android.media.MediaPlayer;
public class Music 
{
	int[] a = {1,0,0,0};
	boolean putMusicFinish;
	public List<MediaPlayer>  mpList;
    Context context;
    int voiceFlag;

	public Music(Context ct)
	{
		putMusicFinish = false;
		context = ct;
		musicIntialize();
		voiceFlag = 1;

	}
   public void setVoiceFlag(int v)
   {
	   voiceFlag = v;
   }

	public void playMusic(int index)
	{
		mpList.get(index).start();
	}

	public void setLoop(int a1,boolean b)
	{
		mpList.get(a1).setLooping(b);
	}


	public void musicIntialize()
	{


		mpList = new ArrayList<MediaPlayer>();

		mpList.add(MediaPlayer.create(context,R.raw.background1));
		mpList.add(MediaPlayer.create(context,R.raw.getweapon));
		mpList.add(MediaPlayer.create(context,R.raw.over));
		mpList.add(MediaPlayer.create(context,R.raw.gameover));
		
		mpList.get(0).setLooping(true);
		mpList.get(1).setLooping(false);
		mpList.get(2).setLooping(false);
		mpList.get(3).setLooping(false);
		
		mpList.get(1).setVolume(0.2f, -1000f);
		mpList.get(2).setVolume(0.2f, -1000f);

		for(int i = 0;i<4;i++)
		{
			//prepare
			try 
			{
				mpList.get(i).prepare();   //prepare();
				
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		putMusicFinish = true;


	}




	public int MusicManagement()
	{
		    if(voiceFlag != 1)
		    {
		    	outa();
		    	return 0;
		    }
			for(int i = 1;i<mpList.size();i++)
			{
				if(a[i]==1)playMusic(i);
			}

		    outa();
		return 0;
	}

	public void setMusicWillBePlay(int a1)
	{
		a[a1] = 1;
		if(this.voiceFlag==1)
		mpList.get(a1).start();
		System.out.println("setMusicWillBePlay");
	}

	public void removeMusicWillBePlay(int a1)
	{
		if(mpList.get(a1).isPlaying())
		mpList.get(a1).pause();
		
		System.out.println("removeMusicWillBePlay");
		a[a1] = 0;
	}
	
	public void outa()
	{
		for(int i =0 ;i<4;i++)
		{
		System.out.println("--------"+a[0]+" -- ");
		}
		System.out.println("**************"+voiceFlag);
	}

}
