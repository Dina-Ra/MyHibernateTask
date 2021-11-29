package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.cleanUsersTable();

        userService.saveUser("Андрей", "Андреев", (byte) 23);
        userService.saveUser("Антон", "Антонов", (byte) 24);
        userService.saveUser("Иван", "Иванов", (byte) 32);
        userService.saveUser("Петр", "Петров", (byte) 34);


        userService.removeUserById(1);

        userService.getAllUsers();

        userService.dropUsersTable();

        Util.FactoryClose();
    }
}
