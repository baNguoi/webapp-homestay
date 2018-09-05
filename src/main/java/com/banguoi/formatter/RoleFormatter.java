package com.banguoi.formatter;

import com.banguoi.model.Role;
import com.banguoi.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class RoleFormatter implements Formatter<Role> {

    private RoleService roleService;

    @Autowired
    public RoleFormatter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role parse(String text, Locale locale) throws ParseException {

        Long id = Long.parseLong(text);
        Role roles = roleService.findById(id);
        return roles;
    }

    @Override
    public String print(Role object, Locale locale) {
        return object.toString();
    }
}
