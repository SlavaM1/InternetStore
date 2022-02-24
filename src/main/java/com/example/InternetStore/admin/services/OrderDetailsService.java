package com.example.InternetStore.admin.services;

import com.example.InternetStore.market.models.Market;
import com.example.InternetStore.market.models.OrderDetails;
import com.example.InternetStore.market.repositories.MarketRepository;
import com.example.InternetStore.market.repositories.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {
    private final OrderDetailsRepository orderDetailsRepository;
    private  final MarketRepository marketRepository;

    //вывод всез заказов из БД
    public Iterable<OrderDetails> findAll() {
       return orderDetailsRepository.findAll();
    }


    //подробный вывод товаров из БД
    public String OrderDetailsService(long id, Model model) {
        if (!orderDetailsRepository.existsById(id)) {
            return "redirect:/order";
        }

        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);

        /* преобразование string в hashmap
         * тут используется сложный велосипед, товары хранятся в БД в текстовом формате
         * нам нужно показать админу все товары и общую стоимость
         * это преобразование текста обратно в хэш таблицу
         */
        //получаем данные из БД
        String value = orderDetails.get().getOrder_details();
        //тут приводим к понятному виду наш текст
        value = value.replaceAll("[^\\.0123456789]", " ");
        String[] words = value.split("  ");

        //создаем хэш таблицу куда будем все это класть
        HashMap<Long, List<Double>> productDetails = new HashMap<>();

        //бежим циклом через 3 индекса и добавляем, id , цену и количество
        for (int index = 0; words.length > index; index += 3) {

            //преобразуем из стринга к нормальным типам данных
            long idItem = Long.parseLong(words[index]);
            double quantityItem = Double.parseDouble(words[index + 1]);
            double priceItem = Double.parseDouble(words[index + 2]);

            //кладем это все в хэш таблицу
            ArrayList<Double> item = new ArrayList<Double>();
            item.add(quantityItem);
            item.add(priceItem);
            productDetails.put(idItem, item);
        }

        //теперь все что есть отдаем в модель
        ArrayList<OrderDetails> res = new ArrayList<>();
        orderDetails.ifPresent(res::add);
        model.addAttribute("OrderDetails", res);

        Set<Long> keys = productDetails.keySet();
        Iterable<Market> productDetailsId = marketRepository.findAllById(keys);
        model.addAttribute("productDetails", productDetailsId);

        model.addAttribute("priceOrder", productDetails != null ? productDetails : new ArrayList<>());

        return "order/order-details";
    }
}
