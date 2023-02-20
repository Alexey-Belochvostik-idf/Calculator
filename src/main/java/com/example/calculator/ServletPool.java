package com.example.calculator;

import com.example.calculator.servicedb.PoolBd;
import com.example.calculator.servicedb.PoolBdImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;


@WebServlet("/pool")
public class ServletPool extends HttpServlet {

    private final PoolBd poolBd = new PoolBdImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pool.html");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        double lengthWallA = Double.parseDouble(req.getParameter("lengthWallA"));
        double lengthWallB = Double.parseDouble(req.getParameter("lengthWallB"));
        double lengthWallC = Double.parseDouble(req.getParameter("lengthWallC"));
        double lengthWallD = Double.parseDouble(req.getParameter("lengthWallD"));
        double heightRoom = Double.parseDouble(req.getParameter("heightRoom"));
        String wallTiles = req.getParameter("wallTiles");
        double wallTilesNew = poolBd.wallTilesBd(wallTiles);

        double squareWall = (lengthWallA + lengthWallB + lengthWallC + lengthWallD) * heightRoom;
        double costWall = squareWall * wallTilesNew;

        double lengthFloor = Double.parseDouble(req.getParameter("lengthFloor"));
        double widthFloor = Double.parseDouble(req.getParameter("widthFloor"));
        String floorTiles = req.getParameter("floorTiles");

        double floorTilesNew = poolBd.floorTilesBd(floorTiles);
        double costFloor = (lengthFloor * widthFloor) * floorTilesNew;

        double additional;
        if (req.getParameterValues("add") != null) {
            String[] addition = req.getParameterValues("add");
           additional = poolBd.plumbing(addition);
        }else{
            additional = 0;
        }
        double sum = costWall + costFloor + additional;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        String roundOff = df.format(sum);
        try {
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().println("Площадь стен :" + squareWall + " м2");
            resp.getWriter().println("Площадь пола : " + (lengthFloor * widthFloor) + " м2");
            resp.getWriter().println("Итого : " + roundOff + " BYN");
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
