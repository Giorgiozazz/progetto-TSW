<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.json.JSONArray, it.unisa.model.ProductBean, it.unisa.ProductModel, it.unisa.ProductModelDM" %>
<%
    String q = request.getParameter("q");
    List<String> suggestions = new ArrayList<>();

    if (q != null && !q.isEmpty()) {
        ProductModelDM model = new ProductModelDM();
        Collection<ProductBean> results = model.doSearchByKeyword(q);

        for (ProductBean p : results) {
            suggestions.add(p.getMarca() + " " + p.getModello());
        }
    }

    JSONArray json = new JSONArray(suggestions);
    out.print(json.toString());
%>