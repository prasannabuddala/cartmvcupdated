package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DALs.ProductStockDAL;
import DAOs.ProductStockDAO;

@WebServlet("/CheckProductQuantityController")
public class CheckProductQuantityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pid = Integer.parseInt(request.getParameter("pidd"));
		ProductStockDAO psdao = new ProductStockDAL();
		boolean stockAvailable = psdao.checkStock(pid);
		response.setContentType("text/plain");
		if (stockAvailable == true)
			response.getWriter().print("success");
		else
			response.getWriter().print("failure");

	}

}
