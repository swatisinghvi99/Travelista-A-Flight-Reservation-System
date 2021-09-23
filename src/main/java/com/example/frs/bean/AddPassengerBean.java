package com.example.frs.bean;

import com.example.frs.bean.PassengerBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddPassengerBean {
    List<PassengerBean> passengers = new ArrayList<>();
    public void addPassenger(PassengerBean p){
        passengers.add(p);
    }
}