package com.intuit.bookexchange.service;

import com.intuit.bookexchange.model.User;

public interface IUserService {

    User addUser(User user);

    User searchUser(Integer userId);

    void updateUserRewardPoint(Integer userId, Integer rewardPoint);
}
