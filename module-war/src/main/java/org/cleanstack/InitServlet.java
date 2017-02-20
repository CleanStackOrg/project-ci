package org.cleanstack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -4865940183647424756L;

    @Override
    public void init() {
	String cheminWebApp = getServletContext().getRealPath("/");
	System.setProperty("cheminWebApp", cheminWebApp);

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
	System.out.println("salut!");
    }
}