package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class
// тесты фильтров, указанных в задании
class ClassTests {

    @Test
    void filtrEx1() {
        List<Flight> flights = FlightBuilder.createFlights();
        FiltrEx1 ex1 = new FiltrEx1();
        List<Flight> flightsEx1 = ex1.filter(flights);
        List<Flight> flightsEq = new ArrayList<>();
        flightsEq.add(flights.get(2));
        assertEquals(flightsEx1, flightsEq);
    }

    @Test
    void filtrEx2() {
        List<Flight> flights = FlightBuilder.createFlights();
        FiltrEx2 ex2 = new FiltrEx2();
        List<Flight> flightsEx2 = ex2.filter(flights);
        List<Flight> flightsEq = new ArrayList<>();
        for(int i = 0; i < flights.size(); i++){
            if(i != 3){
                flightsEq.add(flights.get(i));
            }
        }
        assertEquals(flightsEx2, flightsEq);
    }

    @Test
    void filtrEx3() {
        List<Flight> flights = FlightBuilder.createFlights();
        FiltrEx3 ex3 = new FiltrEx3();
        List<Flight> flightsEx3 = ex3.filter(flights);
        List<Flight> flightsEq = new ArrayList<>();
        for(int i = 0; i < flights.size()-2; i++){
            flightsEq.add(flights.get(i));
        }
        assertEquals(flightsEx3, flightsEq);
    }
}