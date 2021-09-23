package com.example.frs.dao;

import com.example.frs.bean.RouteBean;
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

public class RouteDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager em;

    public List<RouteBean> list() {
        Session session = sessionFactory.openSession();
        List<RouteBean> routes =new ArrayList<RouteBean>();
        try
        {
            String sql_query="from FRS_TBL_Route";
            routes = session.createQuery(sql_query).list();
            session.close();
            return routes;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<RouteBean> get(String routeID) {
        Session session = sessionFactory.openSession();
        List<RouteBean> routes = new ArrayList<RouteBean>();
        try
        {
            String sql_query="from FRS_TBL_Route WHERE route_id="+routeID;
            routes = session.createQuery(sql_query).list();
            session.close();
            return routes;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void save(RouteBean route) {
        Session session = sessionFactory.openSession();
        if(route != null)
        {
            Transaction t = session.beginTransaction();
            session.save(route);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Route Bean is NULL");
            return;
        }

    }


    public void update(RouteBean route) {
        Session session = sessionFactory.openSession();
        if(route != null)
        {
            Transaction t = session.beginTransaction();
            session.update(route);
            t.commit();
            session.close();
        }
        else{
            System.out.println("Route Bean is NULL");
            return;
        }
    }


    public void delete(String routeID) {
        Session session = sessionFactory.openSession();
        try {
            Transaction t = session.beginTransaction();

            Query delete_query = session.createQuery("delete from  FRS_TBL_Schedule where route_id = :id");
            delete_query.setParameter("id", routeID);
            delete_query.executeUpdate();

            Query delete_query2 = session.createQuery("delete from FRS_TBL_Route where route_id = :id");
            delete_query2.setParameter("id", routeID);
            delete_query2.executeUpdate();
            t.commit();
            session.close();

        } catch (Exception e) {
            System.out.println("There is some delete exception");
            return;
        }
    }
}
