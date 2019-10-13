package com.target.mbe.gardening.tools.api.service.sqlite;

import ch.qos.logback.classic.Logger;
import com.target.mbe.gardening.tools.api.error.InternalErrorException;
import com.target.mbe.gardening.tools.api.service.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class SqlitePersistence implements Persistence
{
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SqlitePersistence.class);

    String m_path = "gardening-tools.sqlite";
    Connection m_conn;

    public SqlitePersistence()
    {
        this.m_path = m_path;
        try
        {
            init();
        }
        catch (SQLException | ClassNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public void init() throws SQLException, ClassNotFoundException
    {
        LOGGER.debug("DB init");

        Class.forName("org.sqlite.JDBC");
        open();
        createTableIfNotEXists();
//        close();
        LOGGER.debug("DB init successfully");
    }

    public void finalize() throws SQLException
    {
        LOGGER.debug("finalize");
        close();
    }

    private void createTableIfNotEXists() throws SQLException
    {
        LOGGER.debug("createTable");
        Statement stmt = null;

        try
        {
            stmt = m_conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tools" +
                "(toolid TEXT PRIMARY KEY     NOT NULL," +
                 "color  TEXT    NOT NULL," +
                 "amount INT     NOT NULL," +
                 "extra  TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch ( SQLException e )
        {
            System.err.println( e.getMessage() );
            throw e;
        }
        LOGGER.debug("Table created successfully");
    }

    public void open() throws SQLException
    {
        LOGGER.debug("DB open");
        try
        {
            if (m_conn == null)
                m_conn = DriverManager.getConnection("jdbc:sqlite:" + m_path);
        }
        catch ( SQLException e )
        {
            System.err.println( e.getMessage() );
            close();
            throw e;
        }

        LOGGER.debug("DB open successfully");
    }

    public void close() throws SQLException
    {
        LOGGER.debug("DB close");
        try
        {
            if (m_conn != null)
            {
                m_conn.close();
                m_conn = null;
            }
        }
        catch ( SQLException e)
        {
            System.err.println( e.getMessage() );
            throw e;
        }
        LOGGER.debug("DB close successfully");
    }

    @Override
    public List<String> getIds() throws InternalErrorException
    {
        LOGGER.debug("DB get all ids.");

        List<String> lst = new ArrayList<>();
        try
        {
            if (m_conn == null)
                throw new SQLException("DB is not open");

            String sql = "SELECT toolid FROM tools";

            Statement stmt  = m_conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next())
                lst.add(rs.getString("toolid"));
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e);
        }

        return lst;
    }

    @Override
    public boolean isExists(String toolid) throws InternalErrorException
    {
        return getIds().contains(toolid);
    }

    @Override
    public Dictionary<String, Object> getTool(String toolid) throws InternalErrorException
    {
        LOGGER.debug("DB get. pid:" + toolid);
        Dictionary result = new Hashtable();
        try
        {
            if (m_conn == null)
                throw new SQLException("DB is not open");

            String sql = "SELECT * FROM tools WHERE toolid='" + toolid + "'" ;

            Statement stmt  = m_conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next())
            {
                if (rs.getString("toolid").equals(toolid))
                {
                    result.put("toolid", toolid);
                    result.put("color", rs.getString("color"));
                    result.put("amount", rs.getInt("amount"));
                    result.put("extra", rs.getString("extra"));



//                    color = new String (rs.getString("color"));
//                    amount = new Integer(rs.getInt("amount"));
//                    extra = new String (rs.getString("extra"));
                    break;
                }
            }

            LOGGER.debug("DB get successfully. toolid:" + toolid);
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e);
        }
        return result;
    }

    @Override
    public void createTool(String toolid, String color, int amount, String extra) throws InternalErrorException
    {
        LOGGER.debug("DB insert. toolid:" + toolid);
        try
        {
            if (m_conn == null)
                throw new SQLException("DB is not open");

            String sql = "INSERT INTO tools(toolid, color, amount, extra) VALUES(?,?,?,?)";
            PreparedStatement pstmt = m_conn.prepareStatement(sql);
            pstmt.setString(1, toolid);
            pstmt.setString(2, color);
            pstmt.setInt(3, amount);
            pstmt.setString(4, extra == null ? "" : extra);
            pstmt.executeUpdate();

            LOGGER.debug("DB insert successfully. toolid:" + toolid);
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void updateTool(String toolidOld, String toolidNew, String color, int amount, String extra)
        throws InternalErrorException
    {
        LOGGER.debug("DB update. toolid:" + toolidOld);

        try
        {
            if (m_conn == null)
                throw new SQLException("DB is not open");

            String sql = "UPDATE tools SET toolid = '" + toolidNew + "',color = '" + color + "',amount = '" + amount + "',extra = '" + (extra == null ? "" : extra) + "' WHERE toolid='" + toolidOld + "'";
            Statement stmt = m_conn.createStatement();
            int rowcounts = stmt.executeUpdate(sql);

            if (rowcounts == 0)
                throw new SQLException("DB config is not found. toolid:" + toolidOld);
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e);
        }


        LOGGER.debug("DB update successfully. toolid:" + toolidOld);
    }

    @Override
    public void deleteTool(String toolid) throws InternalErrorException
    {
        try
        {
            LOGGER.debug("DB delete. toolid:" + toolid);

            if (m_conn == null)
                throw new SQLException("DB is not open");

            String sql = "DELETE FROM tools WHERE toolid='" + toolid + "'";
            Statement stmt = m_conn.createStatement();
            stmt.executeUpdate(sql);

            LOGGER.debug("DB delete successfully. toolid:" + toolid);
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e);
        }
    }
}
