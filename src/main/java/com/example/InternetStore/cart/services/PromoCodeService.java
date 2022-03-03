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
        promoCodeRepository.save(promoCode);
    }
}
