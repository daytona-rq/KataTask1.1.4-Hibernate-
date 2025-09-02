package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        try {
            UserService userService = new UserServiceImpl();
            userService.createUsersTable();
            userService.saveUser("Elena", "Ivanova", (byte) 18);
            userService.saveUser("Mike", "Surova", (byte) 26);
            userService.saveUser("Andrew", "White", (byte) 39);
            userService.saveUser("Michael", "Jordan", (byte) 45);
            System.out.println(userService.getAllUsers());
            userService.cleanUsersTable();
            userService.dropUsersTable();

        } finally {
            Util.closeSessionFactory();
        }

    }
}
