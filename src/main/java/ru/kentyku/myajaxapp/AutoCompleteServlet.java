/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.myajaxapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kentyku
 */
@WebServlet(name = "AutoCompleteServlet", urlPatterns = {"/autocomplete"})
public class AutoCompleteServlet extends HttpServlet {

    CountriesTableReader ctr;
    private ArrayList<Country> countriesList = new ArrayList<Country>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        //считываем данные из запроса
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");//получение из запроса 
        //скрипта значения поля формы(введенное пользователем значение)

        StringBuffer sb = new StringBuffer();//для временного сохранения строки (для формирования xml)

        //читаем из БД
        ctr = new CountriesTableReader();
//            ctr.createdb();
        
        countriesList = ctr.readCountries();
//        System.out.println(request.getParameter("id")); 
//        System.out.println("TEST");
        if (targetId != null) {//если значение не нулевое
            targetId = targetId.trim().toLowerCase();//убираем спереди и в конце пробелы, и делаем все буквы прописными
        } else {
//            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean namesAdded = false;

        if (action.equals("complete")) {//идентифицируем наш запрос из всех возможных запросов

            // проверяем что запрос не пустой
            if (!targetId.equals("")) {

//                Iterator it = composers.keySet().iterator();

                for(Country itemCountry :countriesList) {
//                    String id = (String) it.next();
//                    Composer composer = (Composer) composers.get(id);

                    if ( //сравниваем название страны (сделав все буквы прописными) с запросом в форме
                            itemCountry.getName().toLowerCase().startsWith(targetId)
                           ) {

                        sb.append("<country>");
//                        sb.append("<id>" + composer.getId() + "</id>");
                        sb.append("<name>" + itemCountry.getName() + "</name>");
//                        sb.append("<lastName>" + composer.getLastName() + "</lastName>");
                        sb.append("</country>");
                        namesAdded = true;
                    }
                }
            }
            if (namesAdded) {
                //если имена добавлены, то отправляем в ответ на запрос xml строку
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<countries>" + sb.toString() + "</countries>");//xml строка
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AutoCompleteServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AutoCompleteServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");                       
//        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
