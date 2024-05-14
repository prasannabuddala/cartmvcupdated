package DALs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAOs.PincodeVerificationDAO;
import Modals.CartItem;
import servlets.DatabaseConnection;

public class PincodeVerificationDAL implements PincodeVerificationDAO {
	private DatabaseConnection dbConnection = new DatabaseConnection();
	Connection cn;

	public PincodeVerificationDAL() {
		try {
			Class.forName("org.postgresql.Driver");
			cn = dbConnection.getConnection();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isPincodeServiceable(int pincode, List<CartItem> cartItems) {
		boolean serviceable = true;
		String query = "SELECT 1 FROM ServiceableRegions_pr SR INNER JOIN ProductCategoryWiseServiceableRegions_pr PCWR ON SR.srrg_id = PCWR.srrg_id "
				+ "WHERE PCWR.prct_id = (select pcategoryid from public.\"Products_pr\" where pid = ?) AND ? BETWEEN SR.srrg_pinfrom AND SR.srrg_pinto LIMIT 1";
		try {
			PreparedStatement pstmt = cn.prepareStatement(query);
			for (CartItem cartItem : cartItems) {
				pstmt.setInt(1, cartItem.getPid());
				pstmt.setInt(2, pincode);
				ResultSet rs = pstmt.executeQuery();
				if (!rs.next()) {
					System.out.println("false for: " + cartItem.getPid() + " " + pincode);
					serviceable = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			serviceable = false;
		}

		return serviceable;
	}

}
