package it.polito.ezshop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.ezshop.dao.UserDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUserIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.User;

public class UserManager {

	private static UserManager instance;

	private UserManager() {

	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	private User findById(Integer id) {

		List<User> foundUser = UserDao.getInstance().findByCriteria(new HashMap<String, Object>() {
			{
				put("id", id);
			}
		});

		if (foundUser != null && !foundUser.isEmpty()) {

			return foundUser.get(0);
		}

		return null;
	}

	public Integer createUser(String username, String password, String role)
			throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {

		User newUser = new User();

		try {

			if (role != null && RoleTypeEnum.valueOf(role) != null) {
				newUser.setRole(role);
			} else {
				throw new InvalidRoleException();
			}
		} catch (Exception e) {
			throw new InvalidRoleException();
		}

		if (username != null && !username.isEmpty()) {
			newUser.setUsername(username);
		} else {
			throw new InvalidUsernameException();
		}
		if (password != null && !password.isEmpty()) {
			newUser.setPassword(password);
		} else {
			throw new InvalidPasswordException();
		}

		newUser = UserDao.getInstance().save(newUser);

		return newUser.getId();
	}

	public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {

		if (AuthenticationManager.getInstance().getAuthUser() != null && AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())) {

			User deletingUser = findById(id);

			if (deletingUser != null) {

				UserDao.getInstance().delete(deletingUser);
			} else {
				throw new InvalidUserIdException();
			}
		}

		return true;

	}

	public List<it.polito.ezshop.data.User> getAllUsers() throws UnauthorizedException {

		List<it.polito.ezshop.data.User> iusers = new ArrayList<it.polito.ezshop.data.User>();

		if (AuthenticationManager.getInstance().getAuthUser() != null && AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())) {

			List<User> allUser = UserDao.getInstance().findAll();

			for (User user : allUser) {
				iusers.add(user);
			}

			return iusers;
		} else {
			throw new UnauthorizedException();
		}

	}

	public boolean updateUserRights(Integer id, String role)
			throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
		if (AuthenticationManager.getInstance().getAuthUser() != null && AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())) {

			User updatingUser = findById(id);

			if (updatingUser != null) {

				if (RoleTypeEnum.valueOf(role) != null) {
					updatingUser.setRole(role);

					UserDao.getInstance().save(updatingUser);
				} else {
					throw new InvalidRoleException();
				}

			} else {
				throw new InvalidUserIdException();
			}

			return true;

		} else {
			throw new UnauthorizedException();
		}

	}

}
