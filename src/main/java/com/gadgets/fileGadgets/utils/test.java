package com.gadgets.fileGadgets.utils;

public class test {

    private static long convert(long roleid){
        return roleid & 65535;
    }

    public static void main(String[] args) {
        long roleid=1821269229571L;
        long serverid = convert(roleid);
        System.out.println("roleid="+roleid);
        System.out.println("serverid="+serverid);
    }
}
