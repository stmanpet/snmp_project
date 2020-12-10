package com.company;

import org.soulwing.snmp.*;

import java.io.IOException;

class SNMP_Scanner {

    public static void main(String[] args) throws IOException {
        Mib mib = MibFactory.getInstance().newMib();
        mib.load("SNMPv2-MIB");

        SimpleSnmpV2cTarget snmpTarget = new SimpleSnmpV2cTarget();             //Klasse Target für PC zum auslesen
        snmpTarget.setAddress("10.10.30.254");                                  //target ist nas
        snmpTarget.setCommunity("public");                                      //Rechte auf public
        SnmpContext snmpContext = SnmpFactory.getInstance().newContext(snmpTarget, mib);   //snmp afrage
        VarbindCollection result;
        SnmpResponse<VarbindCollection> snmpResponse;

        snmpResponse = snmpContext.get("1.3.6.1.2.1.1.1.0");                   //objekt id auslesen
        result = snmpResponse.get();
        System.out.println(result.get(0).toString());                           //ausgabe
    }
}