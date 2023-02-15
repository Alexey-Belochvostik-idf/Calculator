package com.example.calculator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

@WebServlet("/pool")
public class ServletPool extends HttpServlet {

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
        double plt1 = Double.parseDouble(req.getParameter("wallTiles"));
        double squareWall = (lengthWallA + lengthWallB + lengthWallC + lengthWallD) * heightRoom;
        double costWall = squareWall * plt1;

        double lengthFloor = Double.parseDouble(req.getParameter("lengthFloor"));
        double widthFloor = Double.parseDouble(req.getParameter("widthFloor"));
        double plt2 = Double.parseDouble(req.getParameter("floorTile"));
        double costFloor = (lengthFloor * widthFloor) * plt2;

        String[] addition = req.getParameterValues("add");
        double[] numbers = Arrays.stream(addition)
                .mapToDouble(Double::parseDouble).toArray();
        double xxx = 0;
        for (double in : numbers) {
            xxx += in;
        }

        double sum = costWall + costFloor + xxx;
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
