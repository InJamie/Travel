package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao dao = new RouteDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装对象
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(dao.findTotalCount(cid,rname));
        //计算总页数
        int totalpage = dao.findTotalCount(cid,rname)%pageSize==0?dao.findTotalCount(cid,rname)/pageSize:dao.findTotalCount(cid,rname)/pageSize+1;
        pageBean.setTotalPage(totalpage);
        //封装获取对象
        int start = (currentPage - 1)*pageSize;//开始记录数
        List<Route> routeList = dao.findByPage(cid, start, pageSize,rname);
        pageBean.setList(routeList);

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //根据rid获取route
        Route route = dao.findOne(rid);
        //根据返回route.sid通过sellerservice获取卖家信息
        Seller seller = sellerDao.findByRid(route.getSid());
        route.setSeller(seller);
        //根据返回的route.rid通过rimageservice获取照片集合
        List<RouteImg> imgs = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(imgs);
        //获取收藏数
        int favoritecount = dao.favoritecount(rid);
        route.setCount(favoritecount);

        return route;
    }


}
