package mavenapplication;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class MyConnection {

    static Client client = null;

    public static Client getClient() {
        if (null == client) {

            try {
                SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
                sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        //return new X509Certificate[0];
                        return null;
                    }

                }}, new java.security.SecureRandom());

                // ClientConfig cfg = new ClientConfig();
                HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Login.username, Login.password);

                //HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    @Override
                    public boolean verify(String string, SSLSession ssls) {
                        return true;
                    }
                };
                //HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

                client = JerseyClientBuilder.newBuilder()
                        .sslContext(sslcontext)
                        //.hostnameVerifier((s1, s2) -> true)
                        .hostnameVerifier(allHostsValid)
                        //.register(feature)
                        .build()
                        .register(feature);
            } catch (NoSuchAlgorithmException | KeyManagementException ex) {
                throw new RuntimeException(ex);
            }
        }
        return client;
    }

    public static Connection getConnection() {

        Connection con = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?"
                    + "autoReconnect=true"
                    + "&useSSL=false"
                    + "&useUnicode=true"
                    + "&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false"
                    + "&serverTimezone=CET", "root", "root");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return con;
    }

}
