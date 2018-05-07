import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouterCommands {

    private TelnetClient telnet;

    private String ip;
    private String username;
    private String password;
    public RouterCommands(String ip, String username, String password) {

        this.ip = ip;
        this.username = username;
        this.password = password;

        reconnect();
    }

    public void reconnect() {
        telnet = new TelnetClient(ip, username, password);
    }

    public boolean ping(String ip) {
        String result = telnet.sendCommand("ping " + ip);

        // parse result
        if(result.contains("Success"))
            return true;

        return false;
    }
    public boolean reboot() {

        try {
        String result = telnet.sendCommand("reboot");
        if (result.contains("Fail"))
            return false;
        } catch(Exception e) {}
        return true;
    }
    public boolean restore() {
        try {
            String result = telnet.sendCommand("restore");
            if (result.contains("Fail"))
                return false;
        }
        catch (Exception e) {}
        return true;
    }
    public boolean dhcp(String startip, String endip) {
        String result = telnet.sendCommand("set dhcppool " + startip + " " + endip);
        if (result.contains("Fail"))
            return false;
        return true;
    }
     public boolean set(String index, String ssid, String channel, String beacon, String auth, String key, Boolean en){
        String result;
        if (en)
        result = telnet.sendCommand("set wlan " + index + " ssid " + ssid + " channel " + channel + " beacontype " + beacon + " auth " + auth + " keys " + key + " enabled");
        else result = telnet.sendCommand("set wlan " + index + " ssid " + ssid + " channel " + channel + " beacontype " + beacon + " auth " + auth + " keys " + key +  " disabled");
         // parse result
         if(result.contains("Success"))
             return true;

         return false;
    }
    public char trace (String ip) {
        String result = telnet.sendCommand("tracert " + ip);
        String[] lines = result.split("\n");
        return (lines[lines.length -2].charAt(2));
    }
    public boolean firewall (String level) {
            String result = telnet.sendCommand("set firewall " + level);
                    if (result.contains("Fail"))
                        return false;
            return true;
    }
    public  RouterInfo displayInfo() {

            String result = telnet.sendCommand("display deviceinfo");
            Pattern pattern = Pattern.compile("(.+):\\s+(.+)");
            Matcher matcher = pattern.matcher(result);
            RouterInfo routerInfo = new RouterInfo();
            while (matcher.find()) {

                String name = matcher.group(1);
                String value = matcher.group(2);
                System.out.println(name + " ==== " + name);

                if(name.equals("Model"))
                    routerInfo.model = value;
                if (name.equals("Firmware Version"))
                    routerInfo.firmwareVersion = value;
                if (name.equals("MAC address"))
                    routerInfo.Mac = value;
                if (name.equals("Firmware Date"))
                    routerInfo.firmwareDate = value;
            }

            return routerInfo;
    }

    public DSLinfo displayDSL() {
            String result = telnet.sendCommand("display dsl");
            Pattern pattern = Pattern.compile("(.+):\\s+(.+)");
            Matcher matcher = pattern.matcher(result);
            DSLinfo dsLinfo = new DSLinfo();
            while (matcher.find()) {
                String name = matcher.group(1);
                String value = matcher.group(2);
                System.out.println(name + " ==== " + name);

                if (name.equals("Status"))
                    dsLinfo.Status = value;
                if (name.equals("ModulationType"))
                    dsLinfo.Modulation = value;
                if (name.equals("UpStreamCurrRate"))
                    dsLinfo.UpRate = value;
                if (name.equals("DownStreamCurrRate"))
                    dsLinfo.DownRate = value;
                if (name.equals("UpStreamNoiseMargin"))
                    dsLinfo.UpNoise = value;
                if (name.equals("DownStreamNoiseMargin"))
                    dsLinfo.DownNoise = value;
                if (name.equals("UpStreamAttenuation"))
                    dsLinfo.UpAtten = value;
                if (name.equals("DownStreamAttenuation"))
                    dsLinfo.DownAtten = value;
            }
            return dsLinfo;
    }



    public static void main(String[] args) {
        try {


            RouterCommands router = new RouterCommands("192.168.1.1", "!!Huawei", "@HuaweiHgw");

            //boolean result = router.ping("192.168.1.1");

            //System.out.println("this is router output: " + result);

            //boolean result2 = router.set("1","woody", "1", "wpa2", "psk", "woody12345", true);
            //boolean result2 = router.firewall ("off");
            //System.out.println("this is router output: " + result2);
            char result = router.trace("192.168.1.2");
            System.out.println("This is router output " + result);

           //DSLinfo result = router.displayDSL();
            //System.out.println("this is router info " + result.Status + "\n" + result.UpRate + "\n" + result.DownRate + "\n" + result.UpNoise);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
