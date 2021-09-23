package com.example.frs.dao;
import com.example.frs.bean.FlightBean;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.*;
@Repository
@Transactional
@Getter
@Setter

public class FlightDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager em;

    public List<FlightBean> list() {
        Session session = sessionFactory.openSession();
        List<FlightBean> flights=new ArrayList<FlightBean>();
        try
        {
            String sql_query="from FRS_TBL_Flight";
            flights = session.createQuery(sql_query).list();
            session.close();
            return flights;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public FlightBean get(String flightID) {
        Session session = sessionFactory.openSession();
        FlightBean flights=new FlightBean();
        try
        {
            flights =  (FlightBean) session.get(FlightBean.class, flightID);
            session.close();
            return flights;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void save(FlightBean flight) {
        Session session = sessionFactory.openSession();
        if(flight != null)
        {
            Transaction t = session.beginTransaction();
            session.save(flight);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Flight Bean is NULL");
            return;
        }

    }


    public void update(FlightBean flight) {
        Session session = sessionFactory.openSession();
        if(flight != null)
        {
            Transaction t = session.beginTransaction();
            session.update(flight);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Flight Bean is NULL");
            return;
        }
    }


    public void delete(String flightID) {
        Session session = sessionFactory.openSession();
        try {
            Transaction t = session.beginTransaction();
            Query delete_query = session.createQuery("delete from  FRS_TBL_Schedule where flight_id = :id");
            delete_query.setParameter("id", flightID);
            delete_query.executeUpdate();

            Query delete_query2 = session.createQuery("delete from FRS_TBL_Flight where flight_id = :id");
            delete_query2.setParameter("id", flightID);
            delete_query2.executeUpdate();
            t.commit();
            session.close();

        } catch (Exception e) {
            System.out.println("There is some delete exception");
            return;
        }
    }
}
