package org.teacake.monolith.apk;

import android.view.View;
import android.content.Context;
import android.graphics.*;

import android.content.res.Resources;;
public class GameOverlay extends View {
	public GameOverlay(Context context, HighScoreTable table)
	{
		super(context);
		this.hsTable = table;
		this.overlayType = OVERLAY_TYPE_GAME_MONOLITH;
		res = context.getResources();
		curtainPaint = new Paint();
		curtainPaint.setARGB(255, 0, 0, 0);
		gameOverPaint = new Paint();
        gameOverPaint.setARGB(190, 190, 190, 0);
        gameOverPaint.setTextSize(36);
        statusTextPaint1 = new Paint();
        statusTextPaint2 = new Paint();
        statusTextPaint1.setARGB(200, 255, 0, 0);
        statusTextPaint1.setTextSize(14);
        statusTextPaint2.setTextSize(14);
        statusTextPaint2.setARGB(255, 128, 128, 128); 
        hsPaint = new Paint();
        hsPaint.setTextSize(16);
        hsPaint.setARGB(190, 190, 190, 0);
        score="0";
        level="1";
        hiscore="0";
        lines="0";
        energy="0";
        message="monolith android";
        goalpha=0;
        direction=8;
        this.curtain = 0;
        this.lastDrawTime = System.currentTimeMillis();
        
    	

		
	}
	@Override protected synchronized void onDraw(Canvas canvas)
	{
		
		int timeindex = this.currentTextColor%5000;
		
		goalpha=goalpha+direction;
		if(goalpha>255)
		{
			direction=-8;
			goalpha=goalpha+direction;
		}
		if(goalpha<32)
		{
			direction=8;
			goalpha=goalpha+direction;
		}
		if(overlayType==OVERLAY_TYPE_INTRO)
		{
			if(timeindex<2500)
			{
				goalpha = (timeindex*255)/2500;
			}
			else
			{
				goalpha = 255-((timeindex-2500)*255)/2500;
			}
			
		}
		this.gameOverPaint.setAlpha(goalpha);
		this.hsPaint.setAlpha(goalpha);
        this.gameOverXPos = getTextWidth(res.getString( R.string.s_game_over),gameOverPaint);
        this.evolvingXPos = getTextWidth(res.getString( R.string.s_evolving ),gameOverPaint);
        drawCurtain(canvas);
		switch (overlayType)
		{
		case OVERLAY_TYPE_INTRO:
			drawIntroOverlay(canvas);
			break;
		case OVERLAY_TYPE_GAME_CLASSIC:
			drawClassicGameOverlay(canvas);
			break;
		case OVERLAY_TYPE_GAME_MONOLITH:
			drawMonolithGameOverlay(canvas);
			break;
		case OVERLAY_TYPE_GAME_PUZZLE:
			drawPuzzleGameOverlay(canvas);
			break;
		}
	}
	
    public int getTextWidth(String str,Paint paint)
    {
    	float widths[] =new float[str.length()];
    	paint.getTextWidths(str,widths); 
    	int totalwidth=0;
    	for(int i=0;i<widths.length;i++)
    	{
    		totalwidth+=widths[i];
    	}
    	return totalwidth;
    }	
	private void drawIntroOverlay(Canvas canvas)
	{
		Long now = System.currentTimeMillis();
		
		String logo="MonolithAndroid";
		
		int index = (int)((now-this.lastDrawTime)%35000);
		
		this.currentTextColor = index;
		if (index>=0 && index<10000)
		{
			logo = "MonolithAndroid";
		}
		if(index>=10000 && index<15000)
		{
			logo = "by Tasos Kleisas";
		}
		if(index>=15000 && index<20000)
		{
			logo = "www.mprizols.org";
		}
		if(index>=20000 && index<25000)
		{
			logo = "press MENU to play";
		}
		if(index>=25000 && index<35000)
		{
			
			int highScoresHeight = 12*18;
			int basey=(canvas.getHeight()-highScoresHeight)/2;
			int highScoresWidth = this.getTextWidth(res.getString(R.string.s_highscores), hsPaint);
			int xoffset = (canvas.getWidth()-248)/2;
			canvas.drawText(res.getString(R.string.s_highscores), (canvas.getWidth()-highScoresWidth)/2, basey,hsPaint);
			canvas.drawText(res.getString(R.string.s_playername), xoffset+32, basey+18, hsPaint);
			canvas.drawText(res.getString(R.string.s_score), xoffset+96, basey+18, hsPaint);
			canvas.drawText(res.getString(R.string.s_level), xoffset+208, basey+18, hsPaint);
			
			
			for(int i=0;i<hsTable.getHighScoreCount();i++)
			{
				String number = ""+(i+1)+".";
				while(number.length()<3)
				{
					number = " "+number;
				}
				String score = "" +hsTable.getHighScore(i).getScore();
				while(score.length()<8)
				{
					score = "0"+score;
				}
				String name = "" +hsTable.getHighScore(i).getName();
				while(name.length()<3)
				{
					name = " "+name;
				}
				String level = "" + hsTable.getHighScore(i).getLevel();
				canvas.drawText(number, xoffset, basey+2*18+i*18, hsPaint);
				canvas.drawText(name, xoffset+32, basey+2*18+i*18, hsPaint);
				canvas.drawText(score, xoffset+96, basey+2*18+i*18, hsPaint);
				canvas.drawText(level,xoffset+208,basey+2*18+i*18,hsPaint);
				
			}
			
		}
		else
		{
			int textxpos = this.getTextWidth(logo,this.gameOverPaint);
			canvas.drawText(logo, (canvas.getWidth()-textxpos)/2, (canvas.getHeight()-gameOverPaint.getTextSize())/2, gameOverPaint);
		}
	}
    public void drawString(Canvas canvas,String str,int x,int y)
    {
    	canvas.drawText(str, x+1, y+1, statusTextPaint2);
    	canvas.drawText(str, x, y, statusTextPaint1);
    }
    public void drawCurtain(Canvas canvas)
    {
    	long now = System.currentTimeMillis();
    	int width = canvas.getWidth();
    	int height = canvas.getHeight();
    	float aperture = 1.0f;
    	if(now-this.lastDrawTime<10000)
    	{
    		canvas.drawRect(0,0, width,height, curtainPaint);
    		
    	}
    	else if(now-this.lastDrawTime>=10000 && now-this.lastDrawTime<20000)
    	{
    		aperture = ((float)(now-this.lastDrawTime-10000))/10000.0f;
    		canvas.drawRect(0,0,width,(height/2)-(height/2)*aperture,curtainPaint);
    		canvas.drawRect(0,(height/2)+(height/2)*aperture,width,height, curtainPaint);
    		//canvas.drawRect(0,height/2,width,(height/2)*aperture,curtainPaint);
    	}
    	
    }
    
	private void drawClassicGameOverlay(Canvas canvas)
	{
        this.drawString(canvas, res.getString(R.string.s_score), 10, 14);
        this.drawString(canvas,""+score, 10, 34);
        this.drawString(canvas,res.getString(R.string.s_level), 10, 54);
        this.drawString(canvas,""+level, 10, 74);
        this.drawString(canvas,res.getString(R.string.s_lines), 10, 94);
        this.drawString(canvas,""+lines,10,114);
        if(message.equals("Game Over"))
        {
        	canvas.drawText(res.getString(R.string.s_game_over), (getWidth()-this.gameOverXPos)/2, (getHeight()-gameOverPaint.getTextSize())/2, gameOverPaint);
        }

    }
	private void drawMonolithGameOverlay(Canvas canvas)
	{
        this.drawString(canvas, res.getString(R.string.s_score), 10, 14);
        this.drawString(canvas,""+score, 10, 34);
        this.drawString(canvas,res.getString(R.string.s_level), 10, 54);
        this.drawString(canvas,""+level, 10, 74);
        this.drawString(canvas,res.getString(R.string.s_lines), 10, 94);
        this.drawString(canvas,""+lines,10,114);
    	this.drawString(canvas,res.getString(R.string.s_energy),10,134);
    	this.drawString(canvas,this.energy,10,154);		
        if(message.equals("Game Over"))
        {
        	canvas.drawText(res.getString(R.string.s_game_over), (getWidth()-this.gameOverXPos)/2, (getHeight()-gameOverPaint.getTextSize())/2, gameOverPaint);
        }
        if(message.equals("Evolving..."))
        {
        	canvas.drawText(res.getString(R.string.s_evolving), (getWidth()-this.evolvingXPos)/2, (getHeight()-gameOverPaint.getTextSize())/2, gameOverPaint);
        }
	}
	
	private void drawPuzzleGameOverlay(Canvas canvas)
	{
		
	}
	private String score;
	public void setScore(String score)
	{
		this.score = score;
	}
	private String hiscore;
	public void setHiscore(String hiscore)
	{
		this.hiscore = hiscore;
	}
	private String level;
	public void setLevel(String level)
	{
		this.level = level;
	}
	private String energy;
	public void setEnergy(String energy)
	{
		this.energy = energy;
	}
	private String lines;
	public void setLines(String lines)
	{
		this.lines = lines;
	}
	private String message;
	public void setMessage(String message)
	{
		this.message = message;
	}
	private int overlayType;
	public void setOverlayType(int overlayType)
	{
		this.overlayType = overlayType;
	}
	public void setCurtain(int theCurtain)
	{
		this.curtain = theCurtain;
	}
	public HighScoreTable getHighScoreTable()
	{
		return this.hsTable;
	}
	private Paint curtainPaint;
	private Paint gameOverPaint;
	private Paint statusTextPaint1;
	private Paint statusTextPaint2;
	private Paint hsPaint;
	private int gameOverXPos;
	private int evolvingXPos;
	private Resources res;
	private int goalpha;
	private int direction;
	private int curtain;
	private long lastDrawTime;
	private int currentTextColor;
	private HighScoreTable hsTable;
	public static final int OVERLAY_TYPE_INTRO = 0;
	public static final int OVERLAY_TYPE_GAME_CLASSIC=1;
	public static final int OVERLAY_TYPE_GAME_MONOLITH=2;
	public static final int OVERLAY_TYPE_GAME_PUZZLE=3;
}
