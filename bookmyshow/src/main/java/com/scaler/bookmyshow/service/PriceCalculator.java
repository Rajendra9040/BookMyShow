package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.model.SeatType;
import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.ShowSeatType;
import com.scaler.bookmyshow.repository.ShowSeatTypeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceCalculator {
    private final ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public Integer calculatePrice(List<ShowSeat> showSeats, Show show) {
        Integer amount = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        Map<SeatType, Integer> priceMap = new HashMap<>();

        for (ShowSeatType showSeatType: showSeatTypes) {
            priceMap.put(showSeatType.getSeatType(), showSeatType.getPrice());
        }

        for (ShowSeat showSeat: showSeats) {
            amount += priceMap.get(showSeat.getSeat().getSeatType());
        }
        return amount;
    }
}
