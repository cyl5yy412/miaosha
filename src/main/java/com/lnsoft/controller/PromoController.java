package com.lnsoft.controller;

import com.lnsoft.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created By Chr on 2019/1/9/0009.
 */
@Controller
@RequestMapping("/promo")
public class PromoController {

    @Autowired
    private PromoService promoService;
}
