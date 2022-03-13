package com.example.InternetStore.market.controllers;


import com.example.InternetStore.market.services.MarketService;
import com.example.InternetStore.utils.path.MarketPath;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(MarketPath.MARKET)
class MarketController {
    private final MarketService marketService;

    //вывод доступных товаров из БД
    @GetMapping
    private String MarketMain(Model model,
                              HttpServletRequest request,
                              @PageableDefault(sort = {"id"},
                                      direction = Sort.Direction.DESC) Pageable pageable) {

        return marketService.MarketMainService(model,request,pageable);
    }

    //по категориям
    @GetMapping(MarketPath.MARKET_CATEGORY)
    private String MarketCategory(Model model,
                                   @PathVariable(value = "id") long id) {
        return marketService.MarketCategoryService(model, id);
    }

    //детали товара
    @GetMapping(MarketPath.MARKET_DETAILS)
    private String MarketDetails(@PathVariable(value = "id") long id, Model model) {
        return marketService.MarketDetailsService(id,model);
    }

    //страница добавления товара
    @GetMapping(MarketPath.MARKET_ADD)
    private String blogAdd(Model model) {
        model.addAttribute("category", marketService.findAll());
        return "market/market-add";
    }

    //добавляем товар
    @PostMapping(MarketPath.MARKET_ADD)
    private String marketProductAdd(@RequestParam String name,
                               @RequestParam float price,
                               @RequestParam String full_text,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("id") List<Long> id) throws IOException {
        marketService.marketProductAddService(name,price,full_text,file,id);
        return "redirect:/market";
    }

    //добавить категорию
    @GetMapping(MarketPath.MARKET_ADD_TO_CATEGORY)
    private String addMarketToCategory(Model model){
        return "market/category";
    }

    //добавить категорию
    @PostMapping(MarketPath.MARKET_ADD_TO_CATEGORY)
    private String marketCategory(@RequestParam String name){
        marketService.marketAddCategoryService(name);
        return "redirect:/market";
    }

}
