package com.revature.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;


/*
 * This class overrided the default EnumType from hibernate to be
 * compatable with PostgreSQL.
 */
public class PostgreSQLEnumType extends org.hibernate.type.EnumType {
     
    public void nullSafeSet(
            PreparedStatement st, 
            Object value, 
            int index, 
            SharedSessionContractImplementor session) 
        throws HibernateException, SQLException {
        if(value == null) {
            st.setNull( index, Types.OTHER );
        }
        else {
            st.setObject( 
                index, 
                value.toString(), 
                Types.OTHER 
            );
        }
    }
}
