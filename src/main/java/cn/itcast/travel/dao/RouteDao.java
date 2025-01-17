package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     *根据cid查询总记录数
     */
    public int findTotalCount(int cid,String rname);
    /**
     * 根据 cid star pagesize 查询当前页面的数据集合
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    public Route findOne(int rid);

    public int favoritecount(int rid);
}
