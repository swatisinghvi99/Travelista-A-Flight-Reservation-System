package com.example.frs.dao;

import com.example.frs.bean.ScheduleBean;
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

public class ScheduleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager em;
    
    public List<ScheduleBean> list() {
        Session session = sessionFactory.openSession();
        List<ScheduleBean> schedules =new ArrayList<ScheduleBean>();
        try
        {
            String sql_query="from FRS_TBL_Schedule";
            schedules = session.createQuery(sql_query).list();
            session.close();
            return schedules;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ScheduleBean get(String scheduleID) {
        Session session = sessionFactory.openSession();
        ScheduleBean schedules = new ScheduleBean();
        try
        {
            schedules =  (ScheduleBean) session.get(ScheduleBean.class, scheduleID);
            session.close();
            return schedules;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void save(ScheduleBean schedule) {
        Session session = sessionFactory.openSession();
        if(schedule != null)
        {
            Transaction t = session.beginTransaction();
            session.save(schedule);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Schedule Bean is NULL");
            return;
        }

    }

    public void update(ScheduleBean schedule) {
        Session session = sessionFactory.openSession();
        if(schedule != null)
        {
            Transaction t = session.beginTransaction();
            session.update(schedule);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Schedule Bean is NULL");
            return;
        }
    }


    public void delete(String scheduleID) {
        Session session = sessionFactory.openSession();
        try {
            Transaction t = session.beginTransaction();

            Query delete_query = session.createQuery("delete from FRS_TBL_Schedule where schedule_id = :id");
            delete_query.setParameter("id", scheduleID);
            delete_query.executeUpdate();
            t.commit();
            session.close();

        } catch (Exception e) {
            System.out.println("There is some delete exception");
            return;
        }
    }
}
