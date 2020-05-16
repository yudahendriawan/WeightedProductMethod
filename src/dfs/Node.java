/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfs;

/**
 *
 * @author Yuda Hendriawan
 */
public class Node {

    int source;
    int destination;
    double distance;
    double roadDensity;

//    public Node(int source, int destination) {
//        this.source = source;
//        this.destination = destination;
//    }
    public Node(int source, int destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Node(int source, int destination, double distance, double roadDensity) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.roadDensity = roadDensity;
    }

    Node() {
    }

    public double getRoadDensity() {
        return roadDensity;
    }

    public void setRoadDensity(double roadDensity) {
        this.roadDensity = roadDensity;
    }

    public int getSource() {
        return source;
    }

    public void setSource(double source) {
        this.source = (int) source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(double destination) {
        this.destination = (int) destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
