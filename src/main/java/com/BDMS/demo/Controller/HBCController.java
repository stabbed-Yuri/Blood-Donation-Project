package com.BDMS.demo.Controller;

import com.BDMS.demo.persistent.HBCEntity;
import com.BDMS.demo.Service.HBCService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HBCController {
    private  final HBCService hbcService;

    @Autowired
    public HBCController(HBCService hbcService) {
        this.hbcService = hbcService;
    }

    @GetMapping("/hbcListPage")
    public String getAllHBCEntities(Model model) {

        List<HBCEntity> hbc;
        hbc=  hbcService.getAllHBCEntities();
        model.addAttribute("hbc", hbc);
        return "hbcListPage";
    }
}
