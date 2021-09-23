package com.example.frs.dao;

import com.example.frs.bean.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Repository
@Transactional
@Getter
@Setter

public class AdminDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ScheduleReservationBean> viewBookedTicketAll(){
        try {
            Session session = sessionFactory.openSession();
            String query = "select new com.example.frs.bean.ScheduleReservationBean(r, s, f, rser) from FRS_TBL_Route r inner join FRS_TBL_Schedule s on r.route_id = s.route_id inner join FRS_TBL_Flight f on  f.flight_id = s.flight_id inner join FRS_TBL_Reservation rser on s.schedule_id = rser.schedule_id";
            Query q = session.createQuery(query);
            List<ScheduleReservationBean> al = q.list();
            session.close();
            return al;

        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
