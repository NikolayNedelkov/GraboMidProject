package dataclasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.UserException;

//User that can buy vouchers and write comments 
public class User {

	private static final String EMAIL_PATTERN =

			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final String userName;
	private String password;
	private final String email;
	private Cart cart;
	private boolean isLogin;

	public User(String name, String password, String email) throws UserException {
		this.cart = new Cart();
		if (name.trim().length() > 3)
			this.userName = name;
		else
			throw new UserException("Invalid name");
		setPassword(password);
		if (checkForPattern(email))
			this.email = email;
		else
			throw new UserException("Invalid email");
		
	}

	public String getName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Cart getCart() {
		return cart;
	}

	public void setPassword(String password) {
		if (password.trim().length() >= 4 && password.trim().length() <= 19)
			this.password = password;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	private boolean checkForPattern(String url) {
		if (url != null) {
			try {
				Pattern patt = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = patt.matcher(url);
				return matcher.matches();
			} catch (RuntimeException e) {
				return false;
			}

		}
		return false;
	}

}
