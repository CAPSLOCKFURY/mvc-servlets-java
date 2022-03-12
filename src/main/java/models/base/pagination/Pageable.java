package models.base.pagination;

import jakarta.servlet.http.HttpServletRequest;

public class Pageable {

    private final int page;
    private final int entitiesPerPage;

    public Pageable(int page, int entitiesPerPage){
        this.page = page;
        this.entitiesPerPage = entitiesPerPage;
    }

    public String paginateQuery(String sql){
        return sql.concat(String.format(" limit %d offset %d", entitiesPerPage, (page - 1) * entitiesPerPage));
    }

    public static Pageable of(HttpServletRequest request, int entitiesPerPage){
        int page = 1;
        if(request.getParameter("page") != null){
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ignored){

            }
        }
        return new Pageable(page, entitiesPerPage);
    }

}
