package com.example.GunSlinger.stores;

import java.util.UUID;

public class GunSlingerStore {

	String username;
	float accuracy;
	int totalshots;
	int meleekills;
	int totalkills;
	int highscore;
	UUID playtime;

	public String getUsername(){
		return username;
	}
	public float getAccuracy(){
		return accuracy;
	}
	public int getTotalshots(){
		return totalshots;
	}
	public int getMeleekills(){
		return meleekills;
	}
	public int getTotalkills(){
		return totalkills;
	}
	public int getHighscore(){
		return highscore;
	}
	public UUID getPlaytime(){
		return playtime;
	}

	public void setUsername(String username){
		this.username=username;
	}
	public void setAccuracy(float accuracy){
		this.accuracy=accuracy;
	}
	public void setTotalshots(int totalshots){
		this.totalshots=totalshots;
	}
	public void setMeleekills(int meleekills){
		this.meleekills=meleekills;
	}
	public void setTotalkills(int totalkills){
		this.totalkills=totalkills;
	}
	public void setHighscore(int highscore){
		this.highscore=highscore;
	}
	public void setPlaytime(UUID playtime){
		this.playtime=playtime;
	}

}