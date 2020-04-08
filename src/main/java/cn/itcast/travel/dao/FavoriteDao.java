package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;

public interface FavoriteDao {
    public Favorite isFavorite(int rid, int uid);

    public void addFavorite(int rid,int uid);
}
