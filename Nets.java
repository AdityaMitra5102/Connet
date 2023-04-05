import java.io.*;
import java.net.*;
import java.util.*;
class Nets {

    public ArrayList<Interf> getInterf() throws SocketException {
        ArrayList<Interf> arr=new ArrayList<Interf>();
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        {
            String dn=netint.getDisplayName();
            String name=netint.getName();
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            String ip="";
            int k=-1;
            for (InetAddress inetAddress : Collections.list(inetAddresses)) 
            {
                k+=1;
                String ips=inetAddress.toString();
                if(!ips.contains(":"))
                {
                    ip=ips;
                    if(ip.startsWith("/"))
                    {
                        ip=ip.substring(1);
                    }
                    break;
                }
            }
            InterfaceAddress address=netint.getInterfaceAddresses().get(k);
            short prflen=address.getNetworkPrefixLength();
            String submask=IP.CIDRtoSubnet(prflen);
            byte[] hardwareAddress = netint.getHardwareAddress();
            String mac="";
            if (hardwareAddress != null) 
            {
                String[] hexadecimalFormat = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) 
                {
                    hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                }
                mac=(String.join("-", hexadecimalFormat));
            }
            Interf ob=new Interf(dn,name,ip,submask,prflen,mac);
            arr.add(ob);
        }
        return arr;
    }

    void changeMac(Interf inf) throws Exception
    {
        Process ec=Runtime.getRuntime().exec("sudo macchanger -A "+inf.name);
        ec.waitFor();
    }

    void changeIPDHCP(Interf inf) throws Exception
    {
        Process ec=Runtime.getRuntime().exec("sudo dhclient -r "+inf.name);
        ec.waitFor();
        ec=Runtime.getRuntime().exec("sudo dhclient "+inf.name);
        ec.waitFor();
    }

    void changeIPStatic(Interf inf) throws Exception
    {
        IP nip=inf.getSimilarIP();
        Process ec=Runtime.getRuntime().exec("sudo ifconfig "+inf.name+" "+nip.ip+" netmask "+nip.subnet);
        ec.waitFor();
    }

    void restartNetwork(Interf inf) throws Exception
    {
        Process ec=Runtime.getRuntime().exec("sudo ifconfig "+inf.name+" down");
        ec.waitFor();
        ec=Runtime.getRuntime().exec("sudo ifconfig "+inf.name+" up");
        ec.waitFor();
    }
    void startVPN() throws Exception
    {
        Process ec=Runtime.getRuntime().exec("windscribe connect");
        ec.waitFor();
    }
}  
