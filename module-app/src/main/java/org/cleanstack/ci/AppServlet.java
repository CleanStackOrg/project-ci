package org.cleanstack.ci;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppServlet extends HttpServlet {

  private static final long serialVersionUID = 545496491843603026L;

  @Override
  public void init() throws ServletException {
    super.init();
    
    System.out.println("hi it's app servlet!");
    
    App.main(null);
  }
  
}
