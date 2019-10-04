package com.eci.arepCalc;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Minimal web app example for Heroku using SparkWeb
 *
 * @author daniel
 */
public class SparkForm {

    /**
     * Metodo main de la aplicacion, corre la aplicacion por medio del puerto 5000 o
     * 4567
     * 
     * @param args argumentos de entrada de la aplicacion
     */
    public static void main(String[] args) {
        port(getPort());
        get("/", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
    }

    /**
     * Metodo que se encarga de estructurar el html de la pagina de input
     * 
     * @param req parametros ingresados
     * @param res respuesta de la pagina
     * @return
     */
    private static String inputDataPage(Request req, Response res) {
        String pageContent = "<!DOCTYPE html>" + "<html>" + "<body>" + "<h2>Calc</h2>" + "<form action=\"/results\">"
                + "  Ingrese el numero que quiere elevar /:<br>"
                + "  <input type=\"text\" name=\"double\" value=\" \" / \"\">  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">" + "</form>"
                + "<p>If you click the \"Submit\" button, the form-data will be sent to a page called \"/results\".</p>"
                + "</body>" + "</html>";
        return pageContent;
    }

    /**
     * Metodo que se encarga de estructurar el html de la pagina de response
     * 
     * @param req parametros ingresados
     * @param res respuesta de la pagina
     * @return
     * @throws IOException
     */
    private static String resultsPage(Request req, Response res) throws IOException {
        String ans = "0";
        String strValue = req.queryParams("double");
        System.out.println(strValue);
        strValue = strValue.replaceAll("\\s+","");
        System.out.println(strValue);
        URL url = new URL("https://vbfm9hzpra.execute-api.us-east-1.amazonaws.com/Beta?value="+strValue);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            ans = reader.readLine();
        } catch (IOException x) {
            System.err.println(x);
        }
        
        
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Respuesta</h2>"
                + "<form action=\"/results\">"
                + "  <br>respuesta :<br>"
                + "  <br>"
                + ans
                + "  </br>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     *
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

}
