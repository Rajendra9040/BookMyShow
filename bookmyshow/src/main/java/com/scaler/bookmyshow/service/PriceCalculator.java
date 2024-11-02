package com.scaler.bookmyshow.service;

import com.scaler.bookmyshow.model.Seat;
import com.scaler.bookmyshow.model.Show;
import com.scaler.bookmyshow.model.ShowSeat;
import com.scaler.bookmyshow.model.ShowSeatType;
import com.scaler.bookmyshow.repository.SeatRepository;
import com.scaler.bookmyshow.repository.ShowSeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCalculator {
    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final SeatRepository seatRepository;

    public Integer calculatePrice(List<ShowSeat> showSeats, Show show) {
        Integer amount = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShowId(show.getId());
        Map<Long, Seat> allSeats = seatRepository.findAllByIdIn(
                showSeats.stream()
                    .map(ShowSeat::getSeatId)
                    .collect(Collectors.toList())
            ).stream()
            .collect(Collectors.toMap(Seat::getId, Function.identity()));
        Map<Long, Integer> priceMap = new HashMap<>();

        for (ShowSeatType showSeatType: showSeatTypes) {
            priceMap.put(showSeatType.getSeatTypeId(), showSeatType.getPrice());
        }

        for (ShowSeat showSeat: showSeats) {
            amount += priceMap.get(allSeats.get(showSeat.getSeatId()).getSeatTypeId());
        }
        return amount;
    }
}
