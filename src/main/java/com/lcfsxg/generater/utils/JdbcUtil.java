package com.lcfsxg.generater.utils;

import com.lcfsxg.generater.config.xml.JdbcInfo;
import lombok.AllArgsConstructor;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author lichunfeng
 * @date 2018/10/16 14:15
 */
@AllArgsConstructor
public class JdbcUtil {

    private String url;
    private String username;
    private String password;

    public JdbcUtil(JdbcInfo config) {
        this.url = config.getUrl();
        this.username = config.getUserName();
        this.password = config.getPassWord();
    }

    //返回连接
    public Connection getConnection() {
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用动态参数，更新数据-->插入记录，更新记录，删除记录（不需要返回）
     * @param sql
     * @param params
     */
    public void executeUpdate(String sql, Object... params) {//
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
    }

    public Integer executeCount(String sql) {//返回表中有多少条记录
        Integer count = 0;
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> executeQuery(String sql, T t, Object... params) {//查询数据库，需要返回
        Class<? extends Object> _class = t.getClass();
        Field[] fields = _class.getDeclaredFields();

        Connection conn = getConnection();
        PreparedStatement ps = null;

        ArrayList<T> list = new ArrayList<T>();
        try {
            ps = conn.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            ResultSet rs = ps.executeQuery();//获得结果集

            while (rs.next()) {
                T returnobj = (T) _class.newInstance();
                for (Field field : fields) {
                    String fieldName = field.getName();//获得属性的名字
                    Column annotation = field.getAnnotation(Column.class);
                    String columnName;
                    if (annotation != null) {
                        columnName = annotation.name();
                    } else {
                        columnName = fieldName;
                    }
                    try {
                        Object obj = rs.getString(columnName);//通过名字获得结果集中的数据
                        String upName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method method = _class.getDeclaredMethod("set" + upName, field.getType());//获得方法
                        method.invoke(returnobj, obj);//设置值
                    } catch (Exception e) {

                    }

                }
                list.add(returnobj);
            }
            if (list.size() == 0) {
                return null;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return null;
    }


    //关闭链接操作
    public static void close(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭Statement-->发送sql语句
    public static void close(Statement stat) {
        if (null != stat) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭ResultSet
    public static void close(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

