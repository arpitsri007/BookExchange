package com.intuit.bookexchange.service;

import com.intuit.bookexchange.entity.UserEntity;
import com.intuit.bookexchange.exceptions.UserNotFoundException;
import com.intuit.bookexchange.model.User;
import com.intuit.bookexchange.repository.IUserRepository;
import lombok.NonNull;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User addUser(User user) {

        // adding a new user
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());
        userEntity.setUserEmail(user.getUserEmail());
        userEntity.setRewardPoint(user.getRewardPoint());

        userRepository.save(userEntity);

        User addeduser = new User();
        BeanUtils.copyProperties(userEntity, addeduser);

        return addeduser;
    }

    @Override
    public User searchUser(@NonNull Integer userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        User existingUser = new User();

        if(userEntity.isPresent()) {
           BeanUtils.copyProperties(userEntity, existingUser);
        } else {
            throw new UserNotFoundException("User is not found");
        }

        return existingUser;
    }

    @Override
    public void updateUserRewardPoint(Integer userId, Integer rewardPoint) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        UserEntity userEntity;

        if(userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
            userEntity.setRewardPoint(userEntity.getRewardPoint()+rewardPoint);
        } else {
            throw new UserNotFoundException("User is not found");
        }

        userRepository.save(userEntity);
    }


}
