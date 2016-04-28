package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Daniel
 */
public class LocationType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.DECIMAL, Types.DECIMAL};
    }

    @Override
    public Class returnedClass() {
        return Location.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return ObjectUtils.nullSafeEquals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        if (o!=null)
            return o.hashCode();
        else
            return 0;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] columns, SessionImplementor si, Object o) throws HibernateException, SQLException {
        Location location = null;
  
        double lat = rs.getDouble(columns[0]);
        double lon = rs.getDouble(columns[1]);
        
        location = new Location();
        location.setLatitude(lat);
        location.setLongitude(lon);
        
        return location;
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object obj, int index, SessionImplementor si) throws HibernateException, SQLException {
        if(obj==null){
            ps.setNull(index, Types.DECIMAL);
            ps.setNull(index+1, Types.DECIMAL);
        }
        else{
            ps.setDouble(index, ((Location)obj).getLatitude());
            ps.setDouble(index+1, ((Location)obj).getLongitude());
        }
    }

    @Override
    public Object deepCopy(Object obj) throws HibernateException {
        if(obj==null) {
            return null;
        }
        else{
            Location newObj=new Location();
            Location existObj=(Location)obj;
            newObj.setLatitude(existObj.getLatitude());
            newObj.setLongitude(existObj.getLongitude());
        
            return newObj;
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object obj) throws HibernateException {
        Object  deepCopy=deepCopy(obj);
  
        if(!(deepCopy instanceof Serializable))
            return (Serializable)deepCopy;
  
        return null;
    }

    @Override
    public Object assemble(Serializable srlzbl, Object obj) throws HibernateException {
        return deepCopy(obj);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }
    
}
