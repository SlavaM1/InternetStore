package com.example.InternetStore.cart.services;


import com.example.InternetStore.cart.Dto.PromoCodeDto;
import com.example.InternetStore.cart.models.PromoCode;
import com.example.InternetStore.cart.repositories.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    //сохранить промокод в БД
    public void promoCodeAddService(PromoCodeDto promoCodeDto) {
        PromoCode promoCode = new PromoCode();
        promoCode.setPromoCode(promoCodeDto.getPromoCode());
        promoCode.setFirstPromoCode(promoCodeDto.getFirstPromoCode());
        promoCode.setLastPromoCode(promoCodeDto.getLastPromoCode());
        promoCode.setDiscount(promoCodeDto.getDiscount());
        promoCode.setActiveCode(true);
        promoCodeRepository.save(promoCode);
    }

    //деактивировать промокод
    public void promoCodeDisableService(long id) {
        PromoCode promoCode;
        promoCode = promoCodeRepository.findById(id);
        promoCode.setActiveCode(false);
        promoCodeRepository.save(promoCode);
    }

    //активировать промокод
    public void promoCodeEnableService(long id) {
        PromoCode promoCode;
        promoCode = promoCodeRepository.findById(id);
        promoCode.setActiveCode(true);
        promoCodeRepository.save(promoCode);
    }
}
