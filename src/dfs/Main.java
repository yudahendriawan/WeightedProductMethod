/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author pc
 */
public class Main {

    public static void main(String[] args) {
        //banyaknya path pada graph
        int vertices = 31;
        Graph graph = new Graph(vertices);

        graph.addEdge();

        DepthFirstSearch p = new DepthFirstSearch();

        p.printAllPaths(graph, 2, 14);
        //  p.hasil();
       //System.out.println(p.temp);
        for(int i = 0; i<p.temp.size(); i++){
            System.out.println("("+(i+1)+")"+p.temp.get(i));
        }

        Double[][] data = new Double[p.temp.size()][3];

        System.out.println("\n{Jarak, Jumlah Wisata, Kepadatan}");
        for (int i = 0; i < p.temp.size(); i++) {
            System.out.print("[C" + (i + 1) + "] {");
            for (int j = 0; j < 3; j++) {
                data[i][j] = p.temp.get(i).get(j);
                System.out.print(+data[i][j]);
                if (j == 2) {
                    System.out.print("");
                } else {
                    System.out.print(",");
                }
            }
            System.out.println("}");
        }

        ArrayList<Criteria> listCriteria = new ArrayList<>();
        for (Double[] dataKu : data) {
            Criteria criteria = new Criteria();
            criteria.setJarak(dataKu[0]);
            criteria.setWisata(dataKu[1]);
            criteria.setKepadatan(dataKu[2]);

            listCriteria.add(criteria);
        }

        //PROSES PERHITUNGAN METODE WP
        //Inisiasi Bobot
        int bobotJarak = 10;
        int bobotWisata = 50;
        int bobotKepadatan = 1;
        double totalBobot = bobotJarak + bobotKepadatan + bobotWisata;

        //Penormalan Bobot
        double bobotNormalJarak = bobotJarak / totalBobot;
        double bobotNormalWisata = bobotWisata / totalBobot;
        double bobotNormalKepadatan = bobotKepadatan / totalBobot;

        //inisiasi array utk menyimpan vektor S
        //memangkatkan setiap kriteria dengan bobot 
        //dengan pangkat bobot + utk kriteria benefit (wisata) dan - utk kriteria cost(kepadatan,jarak).
        double[] vektorS = new double[listCriteria.size()];
        for (int i = 0; i < listCriteria.size(); i++) {
            vektorS[i] = Math.pow(listCriteria.get(i).getJarak(), -bobotNormalJarak)
                    * Math.pow(listCriteria.get(i).getWisata(), bobotNormalWisata)
                    * Math.pow(listCriteria.get(i).getKepadatan(), -bobotNormalKepadatan);

        }

        //menjumlahkan vektor S dan print vektor S
        double sigmaVektorS = 0;
        System.out.println("\nVektor S pada Setiap Alternatif");
        for (int k = 0; k < vektorS.length; k++) {
            System.out.println("[S" + (k + 1) + "]. " + vektorS[k]);
            sigmaVektorS = sigmaVektorS + vektorS[k];
        }
        System.out.println("Jumlah vektor S = " + sigmaVektorS + "\n");

        //hitung vektor V pada setiap alternatif
        Double[] vektorV = new Double[listCriteria.size()];
        for (int i = 0; i < listCriteria.size(); i++) {
            vektorV[i] = vektorS[i] / sigmaVektorS;
        }

        //print vektor V
        System.out.println("\n Vektor V pada setiap alternatif");
        for (int k = 0; k < vektorV.length; k++) {
            System.out.println("[V" + (k + 1) + "]. " + vektorV[k]);
            // sigmaS = sigmaS + s1[k];
        }

        //mengcopy vektor V utk membandingkan 
        Double[] vektorVSortDesc = Arrays.copyOf(vektorV, vektorV.length);

        System.out.println("\nSorting Vektor V by Descending Sort");
        Arrays.sort(vektorVSortDesc, Collections.reverseOrder());
        for (int k = 0; k < vektorVSortDesc.length; k++) {
            System.out.println("[" + (k + 1) + "]. " + vektorVSortDesc[k]);
        }

        //mencari peringkat pertama
        int rank1 = 0;
        int rank2 = 0;
        int rank3 = 0;
        for (int i = 0; i < vektorVSortDesc.length; i++) {
            if (vektorVSortDesc[0].equals(vektorV[i])) {
                rank1 = i + 1;
            } else if (vektorVSortDesc[1].equals(vektorV[i])) {
                rank2 = i + 1;
            } else if (vektorVSortDesc[2].equals(vektorV[i])) {
                rank3 = i + 1;
            }
        }
        System.out.println("\nPeringkat pertama adalah C" + rank1
                + "\nPeringkat kedua adalah C" + rank2 + ""
                + "\nPeringkat ketiga adalah C" + rank3);

        System.out.println("\nList Rank 1");

        //listCriteria.get(rank1-1);
        System.out.println(p.temp.get(rank1 - 1));

        Double[] pathResult = new Double[p.temp.get(rank1 - 1).size()];

        //copy element of arraylist in index rank1 to array 1 dim.
        for (int i = 0; i < pathResult.length; i++) {
            pathResult[i] = p.temp.get(rank1 - 1).get(i);
        }

        //to get just path, not include criteria
        Double[] pathResultFix = Arrays.copyOfRange(pathResult, 3, pathResult.length);

        //change double to int 
        int[] pathResultFixInt = new int[pathResultFix.length];
        for (int i = 0; i < pathResultFixInt.length; i++) {
            pathResultFixInt[i] = pathResultFix[i].intValue();
        }

        //print path
        System.out.println("\nList Path Terbaik Based on Weighted Product");
        System.out.print("[");
        for (int i = 0; i < pathResultFixInt.length; i++) {
            System.out.print(pathResultFixInt[i]);
            if (i != pathResultFixInt.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println("");

        String routePointList = "";
        String origin_ = "";
        String destination_ = "";
        for (int i = 0; i < pathResultFixInt.length; i++) {
            for (int j = 0; j < graph.getLongLat().length; j++) {
                if (i > 0 && i < pathResultFixInt.length - 1) {
                    if (String.valueOf(pathResultFixInt[i]).equals(graph.getLongLat()[j])) {
                        routePointList = routePointList + graph.getLongLat()[j + 1];
                        if (i != pathResultFixInt.length - 2) {
                            routePointList = routePointList + "/";
                        }
                    }
                }
                if (i == 0) {
                    if (String.valueOf(pathResultFixInt[i]).equals(graph.getLongLat()[j])) {
                        origin_ = origin_ + graph.getLongLat()[j + 1];
                    }
                }
                if (i == pathResultFixInt.length - 1) {
                    if (String.valueOf(pathResultFixInt[i]).equals(graph.getLongLat()[j])) {
                        destination_ = destination_ + graph.getLongLat()[j + 1];
                    }
                }

            }
        }
        System.out.println("Route Point List \t: " + routePointList);
        System.out.println("Origin Point \t\t: " + origin_);
        System.out.println("Destination Point \t: " + destination_);

        Double latitudeOrigin = Double.valueOf(origin_.split(",")[0]);
        Double longitudeOrigin = Double.valueOf(origin_.split(",")[1]);
        
        //System.out.println(longitudeOrigin+","+latitudeOrigin);
        //  origin = Point.fromLngLat(longitudeOrigin,latitudeOrigin);
        //  }

        //  for(String s : Arrays.asList(destination_.split(","))){
        Double latitudeDest = Double.valueOf(destination_.split(",")[0]);
        Double longitudeDest = Double.valueOf(destination_.split(",")[1]);
        //   destination = Point.fromLngLat(longitudeDest,latitudeDest);

    }

}
