/**
 * This class is generated by jOOQ
 */
package com.grb.indonesia.jooq.data.jooq.tables;


import com.grb.indonesia.jooq.data.jooq.Pinpoint;
import com.grb.indonesia.jooq.data.jooq.tables.records.TestDateRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TestDate extends TableImpl<TestDateRecord> {

    private static final long serialVersionUID = 2107874010;

    /**
     * The reference instance of <code>pinpoint.test_date</code>
     */
    public static final TestDate TEST_DATE = new TestDate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TestDateRecord> getRecordType() {
        return TestDateRecord.class;
    }

    /**
     * The column <code>pinpoint.test_date.id</code>.
     */
    public final TableField<TestDateRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>pinpoint.test_date.dt</code>.
     */
    public final TableField<TestDateRecord, Timestamp> DT = createField("dt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1970-10-10 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>pinpoint.test_date</code> table reference
     */
    public TestDate() {
        this("test_date", null);
    }

    /**
     * Create an aliased <code>pinpoint.test_date</code> table reference
     */
    public TestDate(String alias) {
        this(alias, TEST_DATE);
    }

    private TestDate(String alias, Table<TestDateRecord> aliased) {
        this(alias, aliased, null);
    }

    private TestDate(String alias, Table<TestDateRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Pinpoint.PINPOINT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestDate as(String alias) {
        return new TestDate(alias, this);
    }

    /**
     * Rename this table
     */
    public TestDate rename(String name) {
        return new TestDate(name, null);
    }
}