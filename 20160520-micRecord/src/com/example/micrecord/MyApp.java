package com.example.micrecord;

import android.app.Application;

public class MyApp extends Application{
	private double soundVol;
	private float[] mag;
	private float[] gra;
	private float[] acc;
	private float[] light;
	private float[] dis;
	private long sysTime;
	
	public double getSoundVol(){
		return this.soundVol;
	}
	public void setSoundVol(double v){
		this.soundVol=v;
	}
	
	public float[] getMag(){
		return this.mag;
	}
	public void setMag(float[] v){
		this.mag=v;
	}
	public float[] getGra(){
		return this.gra;
	}
	public void setGra(float[] v){
		this.gra=v;
	}
	public float[] getLight(){
		return this.light;
	}
	public void setLight(float[] v){
		this.light=v;
	}

	public float[] getDis(){
		return this.dis;
	}
	public void setDis(float[] v){
		this.dis=v;
	}
	
	public float[] getAcc(){
		return this.acc;
	}
	public void setAcc(float[] v){
		this.acc=v;
	}
	
	public long getTime(){
		return this.sysTime;
	}
	public void setTime(long v){
		this.sysTime=v;
	}
}
