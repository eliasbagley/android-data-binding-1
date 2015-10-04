package com.eliasbagley.cursorexample.Network;

/**
 * Created by eliasbagley on 10/3/15.
 */

import com.eliasbagley.cursorexample.Models.Article;
import com.eliasbagley.cursorexample.Network.ServiceResponse.ArticlesServiceResponse;
import com.google.gson.Gson;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import fi.iki.elonen.NanoHTTPD;
import timber.log.Timber;

public class MockServer extends NanoHTTPD {

    public MockServer() throws IOException {
        super(8080);
        start();
        System.out.println( "\nRunning! Point your browers to http://localhost:8080/ \n" );
    }

    public static void main(String[] args) {
        try {
            new MockServer();
        }
        catch( IOException ioe ) {
            System.err.println( "Couldn't start server:\n" + ioe );
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        Timber.e("URI: " + session.getUri());

//        String msg = "<html><body><h1>Hello server</h1>\n";
//        Map<String, String> parms = session.getParms();
//        if (parms.get("username") == null) {
//            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
//        } else {
//            msg += "<p>Hello, " + parms.get("username") + "!</p>";
//        }
//        return newFixedLengthResponse( msg + "</body></html>\n" );

        return newFixedLengthResponse(Response.Status.OK, "application/json", articleResponse());
    }

    private static String articleResponse() {
        final Article a1 = new Article();
        a1.id = 1;
        a1.title = "a1";
        a1.body = "body1";

        final Article a2 = new Article();
        a2.id = 2;
        a2.title = "a2";
        a2.body = "body2";

        Article a4 = new Article();
        a4.id = 4;
        a4.title = "a4";
        a4.body = "body4";

        Article a5 = new Article();
        a5.id = 5;
        a5.title = "a5";
        a5.body = "body5";

        List<Article> articles = new ArrayList<>();

        articles.add(a1);
        articles.add(a2);
        articles.add(a4);
        articles.add(a5);

        ArticlesServiceResponse response = new ArticlesServiceResponse();
        response.articles =  articles;


        String json = new Gson().toJson(response);
        Timber.e("json: " + json);

//        return json;
        return "[ articles : { id : 1, title: test, body: testbody } ]";
    }


    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
