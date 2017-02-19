package org.cleanstack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitServlet extends HttpServlet {

  public void init() {
    String cheminWebApp = getServletContext().getRealPath("/");
    System.setProperty("cheminWebApp", cheminWebApp);

  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("salut!");
  }
}