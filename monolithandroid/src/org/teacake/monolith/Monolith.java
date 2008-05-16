package org.teacake.monolith;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;

public class Monolith extends Activity
{
    
    
	public static final int GAME_CLASSIC = 0;
	public static final int GAME_MONOLITH = 1;
    GLView view;
    GameSurfaceView gsf;
    GameOverlay overlay;
    OptionsView optionsView;
    
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //view = new GLView( getApplication(),GAME_MONOLITH );
        //view.setViewType(GLView.VIEW_INTRO);
        //view.doInit();
        //view.running=true;
        overlay = new GameOverlay(this);
        overlay.setOverlayType(GameOverlay.OVERLAY_TYPE_INTRO);
        optionsView = new OptionsView(getApplication());
        android.content.AssetManager mgr = getApplication().getAssets();
        gsf = new GameSurfaceView(this,overlay);
        gsf.setViewType(GLThread.VIEW_INTRO);
        gsf.setGameType(Monolith.GAME_MONOLITH);
        
        //overlay.setVisibility(View.VISIBLE);
        
        
        
        //String[] assetlist=null;
        /*
        String message = "->";
        try
        {
        	assetlist = mgr.list("/");
        	if (assetlist!=null)
        	{
        		for(int i=0;i<assetlist.length;i++)
        		{
        			message+=assetlist[i]+"{";
        		}
        		view.message = message;
        	}
        }
        catch(Exception e)
        {
        	
        }
        */
        //this.addContentView(gsf,new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
        //this.addContentView(overlay,new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
        setContentView(gsf);
        gsf.setVisibility(View.VISIBLE);
        this.addContentView(overlay,new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
        
        
        
         
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        
        menu.add(0, 0, R.string.m_new_monolith, new Runnable() {
            public void run() {
                //mLunarView.doStart();

            	gsf.setGameType(Monolith.GAME_MONOLITH);
            	gsf.setViewType(GLThread.VIEW_GAME);
            	gsf.initGame();
            	
            	
            	
            }
        });
        
        menu.add(1, 0, R.string.m_new_classic, new Runnable() {
            public void run() {
                //mLunarView.doStart();


            	gsf.setGameType(Monolith.GAME_CLASSIC);
            	gsf.setViewType(GLThread.VIEW_GAME);
            	gsf.initGame();
                
            	
            	
            }
        });       
        
		menu.add(2, 0, "test", new Runnable()
		{
			public void run() {
				setContentView(gsf);
				
			}
			
		});
		menu.add(3, 0, R.string.s_exit_game, new Runnable()
		{
			public void run() {
				
				
				finish();
			}
			
		});
		
		
		
		

        
        
        /*
        menu.add(0, 0, R.string.menu_stop, new Runnable() {
            public void run() {
                mLunarView.setMode(LunarView.LOSE, LunarLander.this.
                        getText(R.string.message_stopped));
            }
        });


        menu.add(0, 0, R.string.menu_pause, new Runnable() {
            public void run() {
                mLunarView.doPause();
            }
        });

        menu.add(0, 0, R.string.menu_resume, new Runnable() {
            public void run() {
                mLunarView.doResume();
            }
        });

        menu.addSeparator(0, 0);

        menu.add(0, 0, R.string.menu_easy, new Runnable() {
            public void run() {
                mLunarView.setDifficulty(LunarView.EASY);
            }
        });

        menu.add(0, 0, R.string.menu_medium, new Runnable() {
            public void run() {
                mLunarView.setDifficulty(LunarView.MEDIUM);
            }
        });

        menu.add(0, 0, R.string.menu_hard, new Runnable() {
            public void run() {
                mLunarView.setDifficulty(LunarView.HARD);
            }
        });
		*/
        return true;
    }   
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        boolean handled = false;
        /*
        if(view.game!=null)
        {
        	if (view.game.getStatus()!= SimpleGameData.STATUS_PLAYING)
        	{
        		return handled;
        	}
        }
        */
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
        {
        	try
        	{
        		android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_MOVE_DOWN);
        		message.sendToTarget();
        	}
        	catch(Exception e)
        	{
        		
        	}
        	handled = true;
        }
        if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
        {
        	try
        	{
        		android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_MOVE_LEFT);
        		message.sendToTarget();
        		
        	}
        	catch(Exception e)
        	{
        		
        	}
        	handled = true;
        }
        if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
        {
        	try
        	{
        		android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_MOVE_RIGHT);
        		message.sendToTarget();
        	}
        	catch(Exception e)
        	{
        		
        	}
        	handled = true;
        }
        if(keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_DPAD_UP)
        {
        	try
        	{
        		android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_ROTATE);
        		message.sendToTarget();
        	}
        	catch(Exception e)
        	{
        		
        	}
        	handled = true;
        }
        /*
        if(keyCode == KeyEvent.KEYCODE_ENDCALL)
        {
        	this.finish();
        	handled = true;
        }
        */
        return handled;
    }

    /**
     * Standard override for key-up. We actually care about these,
     * so we can turn off the engine or stop rotating.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        boolean handled = false;
        /*
        if (mMode == RUNNING) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_SPACE) {
                setFiring(false);
                handled = true;
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT ||
                       keyCode == KeyEvent.KEYCODE_Q || 
                       keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ||
                       keyCode == KeyEvent.KEYCODE_W) {
                mRotating = 0;
                handled = true;
            }
        }
		*/
        return handled;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
    	int action = event.getAction();
    	boolean handled = false;
    	if(action==MotionEvent.ACTION_DOWN)
    	{
    		xval=(int)event.getX();
    		yval=(int)event.getY();

    		handled = true;
    	}
    	
    	if(action==MotionEvent.ACTION_UP)
    	{
    		int xnow = (int)event.getX();
    		int ynow = (int)event.getY();
    		if(xnow<20 && ynow<20)
    		{
    			zx =0 ;
    			zy =0 ;
        		try
        		{
        			android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_ROTATE_PLAYFIELD);
        			message.arg1 = zx;
        			message.arg2 = zy;
        			message.sendToTarget();
        		}
        		catch(Exception e)
        		{
        			
        		}
    		}
    		handled=true;
    	}
    	if(action==MotionEvent.ACTION_MOVE)
    	{
            zx = zx+((int)event.getX()-xval);
            zy = zy+((int)event.getY()-yval);
      	  	xval=(int)event.getX();
      	  	yval=(int)event.getY();
    		try
    		{
    			android.os.Message message = android.os.Message.obtain(gsf.getHandler(), GLThread.MSG_ROTATE_PLAYFIELD);
    			message.arg1 = zx;
    			message.arg2 = zy;
    			message.sendToTarget();
    		}
    		catch(Exception e)
    		{
    			
    		}      	  	
      	  	handled = true;
    	}

        return handled;
    } 
    private int xval;
    private int yval;
    private int zx;
    private int zy;
    
     
}