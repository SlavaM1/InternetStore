package com.example.InternetStore.cart.controllers;


import com.example.InternetStore.cart.Dto.PromoCodeDto;
import com.example.InternetStore.cart.repositories.PromoCodeRepository;
import com.example.InternetStore.cart.services.PromoCodeService;
import com.example.InternetStore.utils.path.AdminPath;
import com.example.InternetStore.utils.path.PromoCodePath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping(AdminPath.ADMIN)
public class PromoCodeController {
    private final PromoCodeService promoCodeService;
    private final PromoCodeRepository promoCodeRepository;

    //добавление промокода
    @GetMapping(PromoCodePath.PROMO_CODE_ADD)
    public String PromoCodeAdd(){
        return "promo-code/promo-code-add";
    }

    @PostMapping(PromoCodePath.PROMO_CODE_ADD)
    public String PromoCodeAddPost(@ModelAttribute PromoCodeDto promoCodeDto){
        promoCodeService.promoCodeAddService(promoCodeDto);
        return "redirect:/admin";
    }

    @GetMapping(PromoCodePath.PROMO_CODE_MAIN)
    public String promoCodeMain(Model model){
        model.addAttribute("promoCode", promoCodeRepository.findAll());
        return "promo-code/promo-code-main";
    }

    @PostMapping(PromoCodePath.PROMO_CODE_ACTIVE)
    public String promoCodeDisable(@PathVariable long id){
        promoCodeService.promoCodeDisableService(id);
        return "redirect:" + AdminPath.ADMIN + PromoCodePath.PROMO_CODE_MAIN;
    }

    @PostMapping(PromoCodePath.PROMO_CODE_ENABLE)
    public String promoCodeEnable(@PathVariable long id, HttpServletRequest request){
        promoCodeService.promoCodeEnableService(id);
        return "redirect:" + AdminPath.ADMIN + PromoCodePath.PROMO_CODE_MAIN;
    }
}
