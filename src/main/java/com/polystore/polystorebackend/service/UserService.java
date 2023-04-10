package com.polystore.polystorebackend.service;

import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> createUser(List<User> users){
        return userRepository.saveAll(users);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }


    public User getUserByName(String username){
        return userRepository.findByUsername(username).orElse(new User());
    }

    public String deleteById(int id){
        userRepository.deleteById(id);
        return "Success at deleting user " + id;
    }

    public User updateUser(User user){
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) return null;
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    public List<User> findAll(){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User getReferenceById(int id){
        return userRepository.getReferenceById(id);
    }
}
