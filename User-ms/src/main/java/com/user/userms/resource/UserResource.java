package com.user.userms.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.user.userms.config.OrdermsClient;
import com.user.userms.model.Error;
import com.user.userms.model.User;
import com.user.userms.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public  class UserResource{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserRepo repo;

    @Autowired
    private OrdermsClient ordermsClient;

    @GetMapping("/userOrders")
    @HystrixCommand(fallbackMethod = "myFallBackMethod",
            commandProperties = {@HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "30000")})

    public Object getALlOrdersFromOrderms() {
        LOGGER.info("calling orderms from userms");
        return ordermsClient.getAllOrders();
    }

    private String myFallBackMethod() {
        return "SOMETHING HAPPENED WITH ORDERMS, IM FALLBACK";
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> listUsers = repo.findAll();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        repo.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Optional<User> oldUser = repo.findById(user.getId());
        if (oldUser.isPresent()) {
            oldUser.get().setName(user.getName());
            repo.save(oldUser.get());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        Error error = new Error("updateUser", "User not found with given Id");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        Optional<User> user = repo.findById(userId);
        if (user.isPresent()) {
            repo.deleteById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            Error error = new Error("deleteUser", "User not found with given Id");
            return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
        }
    }
}