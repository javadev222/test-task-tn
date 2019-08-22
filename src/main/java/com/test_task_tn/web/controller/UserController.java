package com.test_task_tn.web.controller;

import com.test_task_tn.business.domain.User;
import com.test_task_tn.business.service.impl.UserService;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@Value
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    UserService userService;

    /**
     * This method returns an User entity with assigned id
     *
     * @param id - id of user in database
     * @return user
     */
    @GetMapping(value = "/{user_id}")
    public ResponseEntity<User> get(@PathVariable("user_id") long id) {
        return userService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    /**
     * The request parameters are optional. Parameters are used to filter the list
     * of users in response body.
     *
     * @param isOnline filters users by status online/offline
     * @param id       is the Timestamp, in case it presents, the request contains
     *                 users whose status is changed after this timestamp only
     * @return List of users
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "is_online", required = false) Boolean isOnline,
                                             @RequestParam(value = "user_id", required = false) Long id) {
        return ResponseEntity.ok(userService.getAll(id, isOnline));
    }

    /**
     * Method is used to save a new User entity to database
     *
     * @param user receive user as Json
     * @return ResponseEntity, contains an HttpStatus and ID of the created user
     */
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    /**
     * The method changes the status of users,
     *
     * @param id            - to identify the user in database
     * @param currentStatus - new status of user, should be true/false
     * @return ResponseEntity with an HttpStatus and contains in ResponseBody
     * id - id of user,
     * new - new status,
     * old - old status
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity changeStatus(@PathVariable("id") Long id,
                                       @RequestParam("online") Boolean currentStatus) {
        return ResponseEntity.ok(userService.changeStatus(id, currentStatus));
    }
}
