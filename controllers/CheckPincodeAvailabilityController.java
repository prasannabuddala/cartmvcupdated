package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DALs.PincodeVerificationDAL;
import DAOs.PincodeVerificationDAO;
import Modals.CartItem;

@WebServlet("/CheckPincodeAvailabilityController")
public class CheckPincodeAvailabilityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		HttpSession session = request.getSession();
		List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartitems");

		PincodeVerificationDAO pvdao = new PincodeVerificationDAL();
		boolean isPincodeServiceable = pvdao.isPincodeServiceable(pincode, cartItems);

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		if (isPincodeServiceable) {
			out.print("success");
		} else {
			out.print("failure");
		}
	}

}
