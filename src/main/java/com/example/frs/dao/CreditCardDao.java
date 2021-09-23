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

@Repository
@Transactional
@Getter
@Setter

public class CreditCardDao {
    @Autowired
    private SessionFactory sessionFactory;


    public CreditCardBean getCardNo(String userID) {
        try
        {
            Session session = sessionFactory.openSession();
            String sql_query="from FRS_TBL_CreditCard WHERE user_id = :userID";
            Query query = session.createQuery(sql_query);
            query.setParameter("userID", userID);
            Object obj = query.getSingleResult();
            session.close();
            if(obj == null){
                return null;
            }
            return (CreditCardBean) obj;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return  null;
        }
    }
}
