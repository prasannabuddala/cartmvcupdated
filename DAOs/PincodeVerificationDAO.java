package DAOs;

import java.util.List;

import Modals.CartItem;

public interface PincodeVerificationDAO {

	boolean isPincodeServiceable(int pincode, List<CartItem> cartItems);

}
