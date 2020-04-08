package cn.itcast.travel.service;

public interface FavoriteService {
    public Boolean isFavorite(String rid,int uid);

    public void addFavorite(String rid,int uid);
}
