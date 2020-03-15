/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.*;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        turtle.color(PenColor.BLACK);
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180-(360/(double)sides);
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int)Math.ceil(360.0/(180-angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angleEle = 180-calculateRegularPolygonAngle(sides);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(angleEle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */

    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double X = targetX-currentX;
        double Y = targetY-currentY;

        if (X==0){
            return Y>=0?(360-currentBearing)%360:180-currentBearing;
        }
        if (Y==0){
            return X>0?90-currentBearing:270-currentBearing;
        }
        double t = Math.toDegrees(Math.atan(X/Y));

        if (X>0&&Y>0){
            return t-currentBearing;
        }else if(X>0&&Y<0){
            return t+180-currentBearing;
        }else if(X<0&&Y<0){
            return t+180-currentBearing;
        }else if(X<0&&Y>0){
            return t+360-currentBearing;
        }
        return 0;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        boolean flag=true;
        double angle=0;
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < xCoords.size()-1; i++) {

            angle=calculateBearingToPoint(angle,xCoords.get(i),yCoords.get(i),xCoords.get(i+1),yCoords.get(i+1));
            res.add(angle);
        }
        return res;
    }

    public static double getD(Point a,Point b){
        return Math.sqrt(Math.pow(a.x()-b.x(),2)+Math.pow(a.y()-b.y(),2));
    }

    public static double getA(Point a,Point b){
        return calculateBearingToPoint(0,(int)a.x(),(int)a.y(),(int)b.x(),(int)b.y());
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */

    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size()<3){
           return points;
        }

        //find corner point
        Point base = new Point(512,512);
        Set<Point> res = new HashSet<>();
        double curA = 0,curD =0,tempA;
        boolean flag=true;
        for (Point point : points) {
            if (base.x()>=point.x()&&base.y()>=point.y()){
                base = point;
            }
        }
        Point BP = base, A = base, B=base;
        do{
            flag=true;
            for (Point point : points) {
                if (res.contains(point))
                    continue;
                tempA = Math.abs(getA(BP,A)-getA(A,point));
                if (!flag) {
                    if (curA > tempA) {
                        B=point;
                        curA = tempA;
                        curD = getD(A, B);
                    } else if (Math.abs(curA - tempA) < 0.001 && curD < getD(A, point)) {
                        B = point;
                        curA = tempA;
                        curD = getD(A, B);
                    }
                } else {
                    curA = tempA;
                    B=point;
                    curD = getD(A, B);
                    flag = false;
                }
            }
            if (B==base){
                break;
            }
            BP=A;
            A=B;
            res.add(A);
        }while(true);
        res.add(base);
        return res;
    }


    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        for(int i=1;i<=185;i++)
        {
            if(i%4==0)turtle.color(PenColor.BLACK);
            if(i%4==1)turtle.color(PenColor.BLUE);
            if(i%4==2)turtle.color(PenColor.CYAN);
            if(i%4==3)turtle.color(PenColor.YELLOW);
            turtle.forward(i);
            turtle.turn(91);
        }

        turtle.draw();
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
