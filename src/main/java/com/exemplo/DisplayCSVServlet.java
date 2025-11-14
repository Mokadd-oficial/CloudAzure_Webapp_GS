
package com.exemplo;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayCSVServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");

        String csvPath = getServletContext().getRealPath("/csv/dados.csv");
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));

        PrintWriter out = response.getWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            out.println(line);
        }
        reader.close();
    }
}
