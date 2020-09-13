package com.aurora.user.services;

import com.aurora.user.dtos.UserDetailsDTO;
import com.aurora.user.entities.UserDetails;
import com.aurora.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Transactional
    public UserDetailsDTO saveUpdate(UserDetailsDTO inputUser){
        try{
            UserDetails userDetails = new UserDetails();
            userDetails.setName(inputUser.getName());
            userDetails.setAge(Integer.parseInt(inputUser.getAge()));
            return getUserDetailsDTO(repository.save(userDetails));
        } catch (Exception e){LOGGER.warn("/***************** Exception in UserService -> saveUpdate(): "+ e);}
        return null;
    }

    public UserDetailsDTO getById(Long id){ return getUserDetailsDTO(repository.getOne(id));}

    public List<UserDetailsDTO> getByName(String name){
        List<UserDetails> userDetailList = repository.findUserByName(name);
        if(CollectionUtils.isEmpty(userDetailList)){return null;}
        return userDetailList
                .stream()
                .map(this::getUserDetailsDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUserDetailsDTO(UserDetails userDetails){
        return new UserDetailsDTO(
          userDetails.getId().toString(),
          userDetails.getName(),
          userDetails.getAge().toString()
        );
    }
}
