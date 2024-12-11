package org.example;

import org.example.model.User;
import org.example.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();

        // Add a user
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        repository.addUser(user);

        // Fetch and display user
        User fetchedUser = repository.getUserById(1);
        System.out.println("Fetched User: " + fetchedUser.getName());

        // Update user
        fetchedUser.setAge(31);
        repository.updateUser(fetchedUser);

        // Display all users
        repository.getAllUsers().forEach(u -> System.out.println(u.getName()));

        // Delete user
        repository.deleteUser(1);

    }
}