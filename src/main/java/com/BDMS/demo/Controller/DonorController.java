// DonorController.java
package com.BDMS.demo.Controller;

import com.BDMS.demo.Service.UserService;
import com.BDMS.demo.persistent.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DonorController {
    private final UserService userService;

    @Autowired
    public DonorController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/donorListPage")
    public String showDonors(@RequestParam(required = false) String location,
                             @RequestParam(required = false) String bloodGroup,
                             Model model) {
        List<UserEntity> donors;
        if ((location != null && !location.isEmpty()) || (bloodGroup != null && !bloodGroup.isEmpty())) {
            donors = userService.searchUsersByLocationAndBloodType(location, bloodGroup);
        } else {
            donors = userService.getAllUsers();
        }
        model.addAttribute("donors", donors);
        return "donorListPage";
    }
}