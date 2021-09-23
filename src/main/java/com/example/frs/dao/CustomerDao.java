package com.example.frs.dao;

import com.example.frs.bean.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;


@Repository
@Transactional
@Getter
@Setter

public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<RSFBean> searchFlight(String source, String destination, String date)
    {
        String[] DateChunk = date.split("-");
        LocalDate localDate = LocalDate.of(Integer.parseInt(DateChunk[0]), Integer.parseInt(DateChunk[1]), Integer.parseInt(DateChunk[2]));
        java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String day = dayOfWeek.toString().toLowerCase().trim();

        try {
            Session session = sessionFactory.openSession();
            String query = "select new com.example.frs.bean.RSFBean(r, s, f) from FRS_TBL_Route r inner join FRS_TBL_Schedule s on r.route_id = s.route_id inner join FRS_TBL_Flight f on f.flight_id = s.flight_id where r.source = :source and r.destination = :destination and s.available_days = :days";
            Query q = session.createQuery(query);
            q.setParameter("source", source);
            q.setParameter("destination", destination);
            q.setParameter("days", day);
            List<RSFBean> al = q.list();
            session.close();
            return al;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void reserveTicket(ReservationBean reservation)
    {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
            session.close();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public List<ScheduleReservationBean> viewBookedTicket(String user_id){
        try {
            Session session = sessionFactory.openSession();
            String query = "select new com.example.frs.bean.ScheduleReservationBean(r, s, f, rser) from FRS_TBL_Route r inner join FRS_TBL_Schedule s on r.route_id = s.route_id inner join FRS_TBL_Flight f on  f.flight_id = s.flight_id inner join FRS_TBL_Reservation rser on s.schedule_id = rser.schedule_id where rser.user_id = :id";
            Query q = session.createQuery(query);
            q.setParameter("id",user_id);
            List<ScheduleReservationBean> al = q.list();
            session.close();
            return al;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void deleteBookedTicket(String reservationID) {

        try {
            Session session = sessionFactory.openSession();
            Transaction t = session.beginTransaction();
            Query del_q = session.createQuery("delete from FRS_TBL_Reservation where reservation_id = :id");
            del_q.setParameter("id", reservationID);
            del_q.executeUpdate();

            Query del_q2 = session.createQuery("delete from FRS_TBL_Passenger where reservation_id = :id");
            del_q2.setParameter("id", reservationID);
            del_q2.executeUpdate();
            t.commit();
            session.close();

        } catch (Exception e) {
            System.out.println("There is some delete exception " + e);
            return;
        }
    }

    public void addPassenger(List<PassengerBean> passengers)
    {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            for (PassengerBean p : passengers) {
                session.save(p);
            }
            transaction.commit();
            session.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ScheduleReservationBean> printBookedTicket(String reservationID) {
        try
        {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("select new com.example.frs.bean.ScheduleReservationBean(r, s, f, rser) from FRS_TBL_Route r inner join FRS_TBL_Schedule s on r.route_id = s.route_id inner join FRS_TBL_Flight f on  f.flight_id = s.flight_id inner join FRS_TBL_Reservation rser on s.schedule_id = rser.schedule_id where rser.reservation_id = :id");
            query.setParameter("id", reservationID);
            List<ScheduleReservationBean> al = query.list();
            session.close();
            return al;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

}
