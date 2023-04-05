import java.io.*;
import java.net.*;
import java.util.*;

class IP
{
    String ip;
    String subnet;
    int cidr;
    IP(String currip, String subn, int cidrn)
    {
        ip=currip;
        subnet=subn;
        cidr=cidrn;
    }

    static String CIDRtoSubnet(short prflen)
    {
        int shft = 0xffffffff<<(32-prflen);
        int oct1 = ((byte) ((shft&0xff000000)>>24)) & 0xff;
        int oct2 = ((byte) ((shft&0x00ff0000)>>16)) & 0xff;
        int oct3 = ((byte) ((shft&0x0000ff00)>>8)) & 0xff;
        int oct4 = ((byte) (shft&0x000000ff)) & 0xff;
        String submask = oct1+"."+oct2+"."+oct3+"."+oct4;
        return submask;
    }
    
    static int SubnetToCIDR(String subnet)
    {
        String subnetbitpat=toBinary(subnet);
        int x= subnetbitpat.indexOf("0");
        return x<0?32:x;
    }
    
    static String toBinary(String ip)
    {
        StringTokenizer st=new StringTokenizer(ip,".");
        String res="";
        while(st.hasMoreTokens())
        {
            String octet=st.nextToken();
            String temp=Integer.toBinaryString(Integer.parseInt(octet));
            while(temp.length()<8) temp="0"+temp;
            res+=temp;
        }
        return res;
    }
    
    static String toIp(String bitpat)
    {
        String res="";
        for(int i=0;i<32;i+=8)
        {
            String octet=bitpat.substring(i,i+8);
            int oct=Integer.parseInt(octet,2);
            res+=oct+".";
        }
        res=res.substring(0,res.length()-1);
        return res;
    }
    
    IP getSimilarIP()
    {
        String ipbitpat=toBinary(ip);
        String sbnbitpat=toBinary(subnet);
        String host=ipbitpat.substring(0, sbnbitpat.indexOf("0"));
        int netlen=32-host.length();
        long max=(long)Math.pow(2,netlen)-3;
        long rand=(long) (new Random().nextFloat()*max)+1;
        String randbit=Long.toBinaryString(rand);
        while(randbit.length()<netlen) randbit="0"+randbit;
        String simipbit=host+randbit;
        String simip=toIp(simipbit);
        return new IP(simip,subnet,cidr);    
    }
}

class Interf extends IP
{
    String displayname;
    String name;
    String mac;
    Interf(String dn, String nm, String currip, String subn, int cidrn, String hwaddr)
    {
        super(currip, subn, cidrn);
        displayname=dn;
        name=nm;
        mac=hwaddr;
    }

    public String toString()
    {
        return name+"\t"+mac+"\t"+ip;
    }
    
    public String getName()
    {
        return name;
    }
    
    boolean equals(Interf ob)
    {
        return name.equals(ob.name);
    }
}
