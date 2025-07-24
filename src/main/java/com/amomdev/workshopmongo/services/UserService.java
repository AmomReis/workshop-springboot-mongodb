package com.amomdev.workshopmongo.services;

import com.amomdev.workshopmongo.domain.User;
import com.amomdev.workshopmongo.dto.UserDTO;
import com.amomdev.workshopmongo.repository.UserRepository;
import com.amomdev.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado."));
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(User obj) {
        try {
            User newObj = findById(obj.getId());
            updateData(newObj, obj);
            return repository.save(newObj);
        } catch (RuntimeException e) {
            throw new ObjectNotFoundException(obj.getId());
        }
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public User fromDTO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}
