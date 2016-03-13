/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

import com.com3014.group1.projectmatching.core.enums.PaymentEnum;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.usertype.UserType;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Daniel
 */
public class PaymentType implements UserType {
    
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.DECIMAL, Types.VARCHAR};
    }
    
    @Override
    public Class returnedClass() {
        return Payment.class;
    }
    
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }
    
    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x!=null)
            return x.hashCode();
        else
            return 0;
    }
    
    @Override
    public Object nullSafeGet(ResultSet rs, String[] columns, SessionImplementor si, Object owner) throws HibernateException, SQLException {
        Payment payment = null;
  
        float amount = rs.getFloat(columns[0]);
        String type = rs.getString(columns[1]);
        
        if(type != null){
            payment = new Payment();
            payment.setPaymentType(PaymentEnum.valueOf(type));
            payment.setAmount(amount);   
        }
        return payment;
    }
   
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor si) throws HibernateException, SQLException {
        if(value==null){
            st.setNull(index, Types.DECIMAL);
            st.setNull(index+1, Types.VARCHAR);
        }
        else{
            st.setFloat(index, ((Payment)value).getAmount());
            st.setString(index+1, ((Payment)value).getPaymentType().getValue());
        }
    }
    
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if(value==null) {
            return null;
        }
        else{
            Payment newObj=new Payment();
            Payment existObj=(Payment)value;
            newObj.setAmount(existObj.getAmount());
            newObj.setPaymentType(existObj.getPaymentType());
        
            return newObj;
        }
    }
    
    @Override
    public boolean isMutable() {
        return false;
    }
    
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object  deepCopy=deepCopy(value);
  
        if(!(deepCopy instanceof Serializable))
            return (Serializable)deepCopy;
  
        return null;
    }
    
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }
    
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}
