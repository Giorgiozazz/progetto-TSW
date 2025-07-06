package it.unisa.admin.control;

import it.unisa.model.OrdineBean;
import it.unisa.OrdineModelDM;
import it.unisa.model.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class OrdiniGenerali extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String cfFiltro = request.getParameter("cfFiltro");
	    String dataDa = request.getParameter("dataDa");
	    String dataA = request.getParameter("dataA");

	    List<OrdineBean> ordini = null;

	    try {
	        OrdineModelDM ordineDAO = new OrdineModelDM();

	        if ((cfFiltro == null || cfFiltro.isEmpty()) &&
	            (dataDa == null || dataDa.isEmpty()) &&
	            (dataA == null || dataA.isEmpty())) {

	            ordini = ordineDAO.doRetrieveAll();

	        } else {
	            ordini = ordineDAO.findByFilters(cfFiltro, dataDa, dataA);
	        }

	        request.setAttribute("ordini", ordini);
	        request.setAttribute("cfFiltro", cfFiltro);
	        request.setAttribute("dataDa", dataDa);
	        request.setAttribute("dataA", dataA);

	    } catch (Exception e) {
	        request.setAttribute("errore", "Errore durante il recupero ordini: " + e.getMessage());
	    }

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/ordiniGenerali.jsp");
	    dispatcher.forward(request, response);
	}
}
