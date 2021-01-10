package com.company;

import org.soulwing.snmp.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

class SNMP_Scanner {
    private static String community= "public";
    private static String[] oids = {".1.3.6.1.2.1.1.6.0", ".1.3.6.1.2.1.25.1.1.0", ".1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.25.1.6.0", ".1.3.6.1.2.1.1.1.0", ".1.3.6.1.2.1.25.2.2.0"};

    public static void main(String[] args) throws IOException {
        System.out.println("1) Beispiel mit vorgegebener IP fuer OIDs \n2) eigene IP mit bestimmter OID\n3) OIDs ausgeben\n4) Sind Hosts erreichbar\n5) Beenden ");
        String option = new String();
        option = "1";
        Scanner sc = new Scanner(System.in);
        while(!option.equals("5")){
            option = sc.nextLine();
            if(option.equals("1")){
                exampleMib();
            }
            else if(option.equals("2")){
                System.out.println("Ip eingeben: ");
                String ip = sc.nextLine();
                System.out.println("OID eingeben");
                String oid = sc.nextLine();
                createMib(ip,oid);
            }
            else if(option.equals("3")){
                printOids();
            }
            else if(option.equals("4")){
                isReachable();
            }
        }
    }
    public static void exampleMib(){
        String[] oids = {".1.3.6.1.2.1.1.6.0", ".1.3.6.1.2.1.25.1.1.0", ".1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.25.1.6.0", ".1.3.6.1.2.1.1.1.0", ".1.3.6.1.2.1.25.2.2.0"};
        String ip = "10.10.30.220";
        Mib mib = newMIB();

        SimpleSnmpV2cTarget snmpV2cTarget = new SimpleSnmpV2cTarget();
        snmpV2cTarget.setAddress(ip);
        snmpV2cTarget.setCommunity(community);

        try (SnmpContext snmpContext = SnmpFactory.getInstance().newContext(snmpV2cTarget,mib)){
            int i = 0;
            while(i<6){
                SnmpResponse<VarbindCollection> inf = snmpContext.get(oids[i]);
                VarbindCollection output = inf.get();
                Varbind finalOutput = output.get(0);
                System.out.println("1111111");
                if(i==0){
                    System.out.println("Location: " + finalOutput+"\n\n");
                }
                else if(i==1){
                    System.out.println("Up-Time: " + finalOutput+"\n\n");
                }
                else if(i==2){
                    System.out.println("Name: " + finalOutput+"\n\n");
                }
                else if(i==3){
                    System.out.println("Process: " + finalOutput+"\n\n");
                }
                else if(i==4){
                    System.out.println("Hardware: " + finalOutput+"\n\n");
                }
                else if(i==5){
                    System.out.println("Ram-Size: " + finalOutput+"\n\n");
                }
                i++;
            }

        }catch(Exception e){
            System.out.println("Exeption thrown");
        }
    }
    public static void createMib(String ip, String oid){
        Mib mib = newMIB();
        SimpleSnmpV2cTarget snmpV2cTarget = new SimpleSnmpV2cTarget();
        snmpV2cTarget.setAddress(ip);
        snmpV2cTarget.setCommunity(community);
        SnmpContext snmpContext = SnmpFactory.getInstance().newContext(snmpV2cTarget,mib);

        SnmpResponse<VarbindCollection> inf = snmpContext.get(oid);
        VarbindCollection output = inf.get();
        String finalOutput = output.get(0).toString();
        System.out.println("Info: "+finalOutput);
    }

    public static void printOids(){
        for (int i = 0; i<6; i++){
            if(i==0){
                System.out.println("Location: " + oids[0]+"\n");
            }
            else if(i==1){
                System.out.println("Up-Time: " + oids[1]+"\n");
            }
            else if(i==2){
                System.out.println("Name: " + oids[2]+"\n");
            }
            else if(i==3){
                System.out.println("Process: " + oids[3]+"\n");
            }
            else if(i==4){
                System.out.println("Hardware: " + oids[4]+"\n");
            }
            else if(i==5){
                System.out.println("Ram-Size: " + oids[5]+"\n");
            }
        }
    }

    public static void isReachable() throws IOException {
        String sub = "10.10.30.";
        for(int i = 0; i < 255; i++) {
            String thisone = String.valueOf(i);
            String address = sub + thisone;
            InetAddress inet = InetAddress.getByName(address);
            if (inet.isReachable(5000)) {
                System.out.println(address + " is reachable");
            }
        }
    }

    public static Mib newMIB(){
        Mib mib = MibFactory.getInstance().newMib();
        try {
            mib.load("SNMPv2-MIB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mib;
    }
}