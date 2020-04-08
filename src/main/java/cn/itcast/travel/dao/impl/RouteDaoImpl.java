package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
//        String sql = "select count(*) from tab_route where cid = ?";
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List arry = new ArrayList();
        if (cid!=0){
            sb.append(" and cid = ? ");
            arry.add(cid);
        }
        if (rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            arry.add("%"+rname+"%");
        }
        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,arry.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
//        String sql = "select * from tab_route where cid = ? limit ?,?";
        String sql = "select * from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);
        List arry = new ArrayList();
        if (cid!=0){
            sb.append(" and cid = ? ");
            arry.add(cid);
        }

        if (rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            arry.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql = sb.toString();

        arry.add(start);
        arry.add(pageSize);


        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), arry.toArray());

    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public int favoritecount(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";
         return template.queryForObject(sql, Integer.class, rid);
    }


}
