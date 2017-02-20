package org.cleanstack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cleanstack.common.Throwables;

public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -4865940183647424756L;

    @Override
    public void init() {
	String cheminWebApp = getServletContext().getRealPath("/");
	System.setProperty("cheminWebApp", cheminWebApp);

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
	res.setContentType("text/html;charset=utf-8");
	try (PrintWriter out = res.getWriter()) {
	    out.println(Manager.init());
	} catch (IOException e) {
	    throw Throwables.propagate(e);
	}
    }
}