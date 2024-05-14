package DALs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAOs.ProductStockDAO;
import servlets.DatabaseConnection;

public class ProductStockDAL implements ProductStockDAO {
	private DatabaseConnection dbConnection = new DatabaseConnection();
	Connection cn;

	public ProductStockDAL() {
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

	public boolean checkStock(int pid) {
		boolean stockAvailability = false;
		int stock;
		try {
			PreparedStatement pst = cn
					.prepareStatement("select prod_stock from public.productstock_pr where prod_id = ?");
			pst.setInt(1, pid);
			ResultSet rs = pst.executeQuery();
			if (rs != null) {
				rs.next();
				stock = rs.getInt("prod_stock");
				System.out.println("stock: " + stock);
				if (stock > 1) {
					stockAvailability = true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return stockAvailability;
	};
}
