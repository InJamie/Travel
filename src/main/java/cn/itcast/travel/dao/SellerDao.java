package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    public Seller findByRid(int rid);
}
