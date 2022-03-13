package com.example.InternetStore.market.services;


import com.example.InternetStore.market.models.Categories;
import com.example.InternetStore.market.models.Market;
import com.example.InternetStore.market.repositories.CategoriesRepository;
import com.example.InternetStore.market.repositories.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketService {

    @Value("${upload.path}")
    private String uploadPath;
    private final MarketRepository marketRepository;
    private final CategoriesRepository categoriesRepository;

    //вывод доступных товаров из БД
    public String MarketMainService(Model model, HttpServletRequest request, Pageable pageable) {
        int page = 0; //default page number is 0 (yes it is weird)
        int size = 5; //default page size is 10


        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("category", categoriesRepository.findAll());
        model.addAttribute("markets", marketRepository.findAll(PageRequest.of(page, size)));
        return "market/market-main";
    }


    //по категориям
    public String MarketCategoryService(Model model, long id) {
        model.addAttribute("category", categoriesRepository.findAll());
        model.addAttribute("markets", marketRepository.findByCategories(categoriesRepository.findById(id)));
        // model.addAttribute("markets", );
        return "market/market-main-category";
    }

    //детали товара
    public String MarketDetailsService(long id, Model model) {

        if (!marketRepository.existsById(id)) {
            return "redirect:/market";
        }

        Optional<Market> market = marketRepository.findById(id);
        ArrayList<Market> res = new ArrayList<>();
        market.ifPresent(res::add);

        model.addAttribute("market", res);

        return "market/market-details";
    }

    //вывод всех категорий
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }


    //добавляем товар
    public void marketProductAddService(String name, float price, String full_text, MultipartFile file, List<Long> ids) throws IOException {
        Market market = new Market(name, full_text, price);

        //добавление изображенния, проверка что он есть
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            //если такой директории нет, то создадим её
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            //генерируем к названию файла рандом имя, чтоб не было коллизий
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            market.setFilename(resultFilename);
        }

        //добавляем категории в товар
        List<Categories> categoriesList = new ArrayList<>();

        for (long id : ids) {
            categoriesList.add(categoriesRepository.findById(id));
        }
        market.setCategories(categoriesList);

        marketRepository.save(market);
    }


    //добавить категорию
    public void marketAddCategoryService(String name) {
        Categories categories = new Categories();
        categories.setNameCategory(name);
        categoriesRepository.save(categories);
    }
}
