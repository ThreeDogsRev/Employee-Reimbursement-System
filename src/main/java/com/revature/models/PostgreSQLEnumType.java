package com.revature.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.persistence.EnumType;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

/**
 * This class overrides the default EnumType from Hibernate to be
 * compatible with PostgreSQL.
 */
public class PostgreSQLEnumType extends org.hibernate.type.EnumType<EnumType> {

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }

}
