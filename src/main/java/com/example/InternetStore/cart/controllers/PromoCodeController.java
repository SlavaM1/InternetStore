package com.example.InternetStore.cart.controllers;


import com.example.InternetStore.cart.Dto.PromoCodeDto;
import com.example.InternetStore.cart.services.PromoCodeService;
import com.example.InternetStore.utils.path.AdminPath;
import com.example.InternetStore.utils.path.PromoCodePath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(AdminPath.ADMIN)
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    //добавление промокода
    @GetMapping(PromoCodePath.PROMO_CODE_ADD)
    public String PromoCodeAdd(Model model){
        return "promo-code/promo-code-add";
    }

    @PostMapping(PromoCodePath.PROMO_CODE_ADD)
    public String PromoCodeAddPost(@ModelAttribute PromoCodeDto promoCodeDto){
        promoCodeService.promoCodeAddService(promoCodeDto);
        return "redirect:/admin";
    }
}
