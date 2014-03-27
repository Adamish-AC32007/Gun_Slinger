package com.example.GunSlinger.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
  CREATE TABLE Tweets (
  user varchar,
  interaction_time timeuuid,
   tweet varchar,
   PRIMARY KEY (user,interaction_time)
  ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.example.GunSlinger.lib.*;
import com.example.GunSlinger.stores.*;


public class GunSlingerModel {
	Cluster cluster;
	public GunSlingerModel(){

	}

	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}

	//creates database if it doesn't exist
	public void createDB(){

		Session session = cluster.connect();
		session.execute("create keyspace if not exists playerData WITH replication = {'class':'SimpleStrategy','replication_factor':1};");

		Session session1 = cluster.connect("playerData"); 	
		session1.execute("CREATE TABLE if not exists data (username varchar,accuracy float,totalshots int, meleekills int, totalkills int, highscore int, playtime UUID, PRIMARY KEY (username,playtime)) WITH CLUSTERING ORDER BY (playtime DESC);");
		session1.execute("CREATE TABLE if not exists friends (username varchar,friends set<varchar>, PRIMARY KEY (username))");
		session1.execute("CREATE TABLE if not exists login (username varchar,password varchar, PRIMARY KEY (username, password))");
	}

	public LinkedList<GunSlingerStore> getScores(String name){
		LinkedList<GunSlingerStore> gsList= new LinkedList<GunSlingerStore>();
		Session session = cluster.connect("playerData");

		PreparedStatement statement = session.prepare("SELECT * from data where username=?");	//create the cql statement to be executed
		BoundStatement boundStatement = new BoundStatement(statement); 			
		ResultSet rs = session.execute(boundStatement.bind(name));
		if (rs.isExhausted()) { 												//if the result set is empty
			System.out.println("No scores returned");
		} else { 																//else (meaning there is more data)
			for (Row row : rs) { 												//for each row
				GunSlingerStore gs = new GunSlingerStore(); 					
				gs.setUsername(row.getString("username")); 						
				gs.setAccuracy(row.getFloat("accuracy")); 						
				gs.setTotalshots(row.getInt("totalshots"));		
				gs.setMeleekills(row.getInt("meleekills"));
				gs.setTotalkills(row.getInt("totalkills"));
				gs.setHighscore(row.getInt("highscore"));
				gs.setPlaytime(row.getUUID("playtime"));
				gsList.add(gs); 												
			}
		}
		return gsList;
	}

	public boolean Login(String name, String password) {
		Session session = cluster.connect("playerData");
		PreparedStatement statement = session.prepare("SELECT * from login where username = ? and password = ?");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(name, password));
		boolean loggedin = false;
		if (rs.isExhausted()) {
			loggedin = false;
		} else {
			loggedin = true;
		}
		
		
		return loggedin;
	}

}
