package com.banguoi.formatter;

import com.banguoi.model.User;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class UserFormatter implements Formatter<User> {

    private UserService userService;

    @Autowired
    public UserFormatter(UserService userService) {
        this.userService = userService;
    }
    @Override
    public User parse(String text, Locale locale) throws ParseException {
        Long id = Long.parseLong(text);
        User user = userService.findById(id);
        return user;
    }

    @Override
    public String print(User object, Locale locale) {
        return object.toString();
    }
}
