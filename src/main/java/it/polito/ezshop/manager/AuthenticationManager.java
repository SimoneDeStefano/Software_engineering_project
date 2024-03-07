package it.polito.ezshop.manager;

import it.polito.ezshop.dao.UserDao;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.model.User;

public class AuthenticationManager {

	private User authUser = null;

	public User getAuthUser() {
		return authUser;
	}

	private static AuthenticationManager AuthenticationManager;

	private AuthenticationManager() {
	}

    public static AuthenticationManager getInstance() {
		if (AuthenticationManager == null) {
			AuthenticationManager = new AuthenticationManager();
		}
		return AuthenticationManager;
	}

	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {

		User user = null;

		user = UserDao.getInstance().findByUsername(username);
		if (user != null) {

			if (user.getPassword().equals(password)) {
				this.authUser = user;

			} else {
				throw new InvalidPasswordException();
			}
		} else {
			throw new InvalidUsernameException();
		}

		return user;
	}

	public boolean logout() {
		if (this.authUser != null) {
			this.authUser = null;
			return true;
		}
		return false;
	}
}
