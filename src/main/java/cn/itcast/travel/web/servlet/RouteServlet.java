package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService service = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取需要参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");

        //此步判断不能放在route_list中   有疑问
        if (rname==null){
            rname="";
        }


        rname  = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //判空并转换为int类型
        int cid=0;
        if (cidStr != null &&!"null".equals(cidStr)&& cidStr.length()>0){
            cid = Integer.parseInt(cidStr);
        }

        int pageSize;//默认每页显示条数为5条
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }

        int currentPage;//默认当前页数为第一页
        if (currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        PageBean<Route> json = service.pageQuery(cid, currentPage, pageSize,rname);
        writeValue(json,response);
        //调用service
    }

    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取rid
        int rid = Integer.parseInt(request.getParameter("rid"));
        //调用routeservice获取route
        Route route = service.findOne(rid);
        //写回route
        writeValue(route,response);
    }

    public void isFavorite(HttpServletRequest request,HttpServletResponse response) throws IOException {
            //获取uid rid
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            uid=0;
        }else{
            uid=user.getUid();
        }
        System.out.println(uid);
        //调用service判断是否存在favorite
        Boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag,response);

    }

    public void addFavorite(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            //用户存在
            uid = user.getUid();
        }else {
            //用户不存在
            return;
        }

        favoriteService.addFavorite(rid,uid);


    }

}
