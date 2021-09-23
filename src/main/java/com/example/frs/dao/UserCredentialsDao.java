package com.example.frs.dao;

import com.example.frs.bean.CredentialsBean;
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

@Repository
@Transactional
@Getter
@Setter
public class UserCredentialsDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager em;

    public CredentialsBean getByUserId(String userID, String password) {
        Session session = sessionFactory.openSession();
        try
        {
            String sql_query="from FRS_TBL_User_Credentials WHERE user_id= :userID and password = :password";
            Query query = session.createQuery(sql_query);
            query.setParameter("password", password);
            query.setParameter("userID", userID);
            Object obj = query.getSingleResult();
            session.close();
            if(obj == null) {
                return null;
            }
            return (CredentialsBean)obj;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public void changeLoginStatus0(String userID) {
        Session session = sessionFactory.openSession();
        try
        {
            Transaction t = session.beginTransaction();
            String sql_query="update FRS_TBL_User_Credentials c set c.login_status = 0 where c.user_id= :userID";
            Query query = session.createQuery(sql_query);
            query.setParameter("userID", userID);
            query.executeUpdate();
            t.commit();
            session.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void changeLoginStatus1(String userID) {
        Session session = sessionFactory.openSession();
        try
        {
            Transaction t = session.beginTransaction();
            String sql_query="update FRS_TBL_User_Credentials c set c.login_status = 1 where c.user_id= :userID";
            Query query = session.createQuery(sql_query);
            query.setParameter("userID", userID);
            query.executeUpdate();
            t.commit();
            session.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }
}
