package com.example.frs.dao;

import com.example.frs.bean.CredentialsBean;
import com.example.frs.bean.ProfileBean;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Repository
@Transactional
@Getter
@Setter

public class CustomerProfileDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ProfileBean> list() {
        Session session = sessionFactory.openSession();
        List<ProfileBean> users=new ArrayList<ProfileBean>();
        try
        {
            String sql_query="from FRS_TBL_User_Profile";
            users=session.createQuery(sql_query).list();
            session.close();
            return users;
        }
        catch(RuntimeException e)
        {
            return users;
        }
    }

    public ProfileBean getByUserId(String userID) {
        Session session = sessionFactory.openSession();
        ProfileBean Users;
        try
        {
            String sql_query="from FRS_TBL_User_Profile WHERE user_id = :userID";
            Query query = session.createQuery(sql_query);
            query.setParameter("userID", userID);
            Object obj = query.getSingleResult();
            session.close();
            if(obj == null) {
                return null;
            }
            return (ProfileBean)obj;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public void save(ProfileBean profile){
        Session s = sessionFactory.openSession();
        if(profile != null)
        {
            profile.setUser_id(profile.getEmail_id());
            CredentialsBean credential = new CredentialsBean(profile.getUser_id(), profile.getPassword(), "C", 1);
            Transaction t = s.beginTransaction();
            s.save(profile);
            s.save(credential);
            t.commit();
            s.close();
        }
        else {
            System.out.println("Profile Bean is NULL");
            return;
        }

    }
}
