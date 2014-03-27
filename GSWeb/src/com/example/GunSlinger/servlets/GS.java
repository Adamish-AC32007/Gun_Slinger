package com.example.GunSlinger.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.utils.UUIDs;
import com.example.GunSlinger.lib.*;
import com.example.GunSlinger.models.*;
import com.example.GunSlinger.stores.*;

/**
 * Servlet implementation class GS
 */
@WebServlet({"/GS", "/GS/*"})
public class GS extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Cluster cluster;
	String name = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GS() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster(); 
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		GunSlingerModel gsm = new GunSlingerModel();
		gsm.setCluster(cluster);
		gsm.createDB();
		String name = "Hamish";
		LinkedList<GunSlingerStore> scoreList = gsm.getScores(name);			
		request.setAttribute("Scores", scoreList);
		RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");	//Send the user to the login page
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Boolean logged = false;
		
		GunSlingerModel gsm = new GunSlingerModel();
		gsm.setCluster(cluster);
		
		if(request.getParameter("Login") != null){											//Used to login
			String username = request.getParameter("Username");
			name = username;
			String password = request.getParameter("Password");
			logged = gsm.Login(username, password);
			if(logged == true){
				RequestDispatcher rd = request.getRequestDispatcher("/Main.jsp"); 
				rd.forward(request, response);
			}
			else{
				boolean loginfail = true;
				request.setAttribute("invalidlog", loginfail); 
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp"); 
				rd.forward(request, response);	
			}
		}
		//if the main page is displayed
		//show the logged in users game results
		else if(request.getParameter("Main") != null)
		{
			LinkedList<GunSlingerStore> scoreList = gsm.getScores(name);			
			request.setAttribute("Scores", scoreList);
			RequestDispatcher rd = request.getRequestDispatcher("/Main.jsp"); 
			rd.forward(request, response);
		}
	}

}
