import java.util.*;
class Runner
{
    void run()throws Exception
    {
        Scanner sc=new Scanner(System.in);
        Nets ob=new Nets();
        ArrayList<Interf> arr=ob.getInterf();
        int ch=arr.size();
        while(true)
        {
            System.out.println("Choose interface");
            for(int i=0;i<arr.size();i++)
            {
                System.out.println(i+". "+arr.get(i));
            }
            System.out.println("Enter choice");
            ch=Integer.parseInt(sc.nextLine());
            if(ch<arr.size()) break;
        }
        Interf choseninterface=arr.get(ch);
        System.out.println("Do you want to flash MAC Address?(Y/n)");
        boolean flashmac=sc.nextLine().toLowerCase().charAt(0)=='y';
        System.out.println("Do you want to flash IP Address?(Y/n)");
        boolean flaship=sc.nextLine().toLowerCase().charAt(0)=='y';
        boolean dhcp=false;
        if(flaship)
        {
            System.out.println("Do you want to use DHCP or Static IP in current subnet?(Y=DHCP/n=Static)");
            dhcp=sc.nextLine().toLowerCase().charAt(0)=='y';
        }
        System.out.println("Do you want to restart network?(Y/n)");
        boolean rstrt=sc.nextLine().toLowerCase().charAt(0)=='y';
        System.out.println("Do you want to use VPN?(Y/n)");
        boolean vpn=sc.nextLine().toLowerCase().charAt(0)=='y';
        System.out.println("Enter interval in seconds");
        int sec=Integer.parseInt(sc.nextLine());
        while(true)
        {
            System.out.println(exec(ob, choseninterface, flashmac, flaship, dhcp, rstrt, vpn));
            System.out.println("Waiting "+sec+" seconds");
            Thread.sleep(sec*1000);
        }
    }

    String exec(Nets ob, Interf interf, boolean flashmac, boolean flaship, boolean dhcp, boolean rstrt, boolean vpn) throws Exception
    {
        System.out.println("Starting flash");
        if(flashmac) ob.changeMac(interf);
        if(flaship)
        {
            if(dhcp) ob.changeIPDHCP(interf);
            else ob.changeIPStatic(interf);
        }
        if(rstrt) ob.restartNetwork(interf);
        if(vpn) ob.startVPN();
        System.out.println("Flashed");
        ArrayList<Interf> arr=ob.getInterf();
        for(Interf x:arr)
        {
            if(x.equals(interf)) return x.toString();
        }
        return "";
    }
    
    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try
                {
                    Process ec=Runtime.getRuntime().exec("windscribe disconnect");
                    ec.waitFor();
                }
                catch(Exception excep){}
            }
        });
    }

    public static void main(String args[])
    {
        registerShutdownHook();
        try
        {
            new Runner().run();
        }
        catch(Exception excep)
        {
            excep.printStackTrace();
        }
    }
}
