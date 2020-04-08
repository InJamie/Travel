package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao dao = new FavoriteDaoImpl();
    @Override
    public Boolean isFavorite(String rid, int uid) {
        //调用dao
        Favorite favorite = dao.isFavorite(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        dao.addFavorite(Integer.parseInt(rid),uid);
    }
}
