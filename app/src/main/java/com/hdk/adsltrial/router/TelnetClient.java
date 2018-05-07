package com.hdk.adsltrial.router;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class TelnetClient {

    private org.apache.commons.net.telnet.TelnetClient telnet = new org.apache.commons.net.telnet.TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt = "ATP>";

    public TelnetClient(String server, String user, String password) {
        try {
            // Connect to the specified server
            telnet.connect(server, 23);

            // Get input and output stream references
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());

            // Log the user on
            readUntil("Login: ");
            write(user);
            readUntil("Password: ");
            write(password);

            // Advance to a prompt
            readUntil(prompt);
        } catch(IOException e) {
            throw new RouterException("Please connect to the router first.");
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            boolean found = false;
            char ch = (char) in.read();
            while (true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (IOException e) {
            new DisconnectedException(e.getMessage());
        }
        return null;
    }

    public void write(String value) {

        out.println(value);
        out.flush();
        System.out.println(value);
    }

    public String sendCommand(String command) {
        write(command);
        return readUntil(prompt);
    }

    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            TelnetClient telnet = new TelnetClient("192.168.1.1", "!!Huawei", "@HuaweiHgw");
            telnet.sendCommand("ping 192.168.1.1");
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
