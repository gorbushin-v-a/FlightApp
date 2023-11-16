package org.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

// Main class and filters
// демонстрация работы фильтров
public class Main {
    public static void main(String[] args) {
        System.out.println("List flights:");
        List<Flight> flights = FlightBuilder.createFlights();
        for (Flight element : flights) {
            System.out.println(element.toString());
        }

        System.out.println("Example 1:");
        FiltrEx1 ex1 = new FiltrEx1();
        List<Flight> flightsEx1 = ex1.filter(flights);
        for (Flight item : flightsEx1) {
            System.out.println(item.toString());
        }

        System.out.println("Example 2:");
        FiltrEx2 ex2 = new FiltrEx2();
        List<Flight> flightsEx2 = ex2.filter(flights);
        for (Flight value : flightsEx2) {
            System.out.println(value.toString());
        }

        System.out.println("Example 3:");
        FiltrEx3 ex3 = new FiltrEx3();
        List<Flight> flightsEx3 = ex3.filter(flights);
        for (Flight flight : flightsEx3) {
            System.out.println(flight.toString());
        }
    }

}

// интерфейс, который имплементируют фильтры, для новых правил фильтрации
interface Filtration {
    List<Flight> filter(List<Flight> flights);
}

// фильтры,перечисленные в задании
// 1.вылет до текущего момента времени
class FiltrEx1 implements Filtration {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> resultFlights = new ArrayList<>();
        for(int i = 0; i < flights.size(); i++){
            Flight currFlight = flights.get(i);
            if(currFlight.getSegments().get(0).getDepartureDate().isBefore(now)){
                resultFlights.add(currFlight);
            }
        }
        return resultFlights;
    }
}

// 2.имеются сегменты с датой прилёта раньше даты вылета
class FiltrEx2 implements Filtration {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> resultFlights = new ArrayList<>();
        for(int i = 0; i < flights.size(); i++){
            boolean flag = true;
            Flight currFlight = flights.get(i);
            List<Segment> segments = currFlight.getSegments();
            for(int j = 0; j < segments.size(); j++){
                if (segments.get(j).getDepartureDate().isAfter(segments.get(j).getArrivalDate())) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                resultFlights.add(currFlight);
            }
        }
        return resultFlights;
    }
}

// 3.общее время, проведённое на земле превышает два часа
// (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним)
class FiltrEx3 implements Filtration {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> resultFlights = new ArrayList<>();
        for(int i = 0; i < flights.size(); i++){
            long timeOnEarth = 0;
            Flight currFlight = flights.get(i);
            List<Segment> segments = currFlight.getSegments();
            for(int j = 1; j < segments.size(); j++){
                long millisA = segments.get(j-1).getArrivalDate()
                        .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
                long millisD = segments.get(j).getDepartureDate()
                        .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
                timeOnEarth += (millisD-millisA);
            }
            if(timeOnEarth < 7200000){
                resultFlights.add(currFlight);
            }
        }
        return resultFlights;
    }
}
