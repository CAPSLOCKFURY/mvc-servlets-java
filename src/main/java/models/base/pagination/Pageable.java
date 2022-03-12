package models.base.pagination;

import jakarta.servlet.http.HttpServletRequest;

public class Pageable {

    private final int page;
    private int entitiesPerPage;
    private boolean lookAhead = false;

    public Pageable(int page, int entitiesPerPage){
        this.page = page;
        this.entitiesPerPage = entitiesPerPage;
    }

    public Pageable(int page, int entitiesPerPage, boolean lookAhead){
        this.page = page;
        this.entitiesPerPage = entitiesPerPage;
        this.lookAhead = lookAhead;
        if(lookAhead){
            this.entitiesPerPage++;
        }
    }

    public String paginateQuery(String sql){
        int offset = (page - 1) * entitiesPerPage;
        if(lookAhead && offset > 0){
            offset--;
        }
        return sql.concat(String.format(" limit %d offset %d", entitiesPerPage, offset));
    }

    public static Pageable of(HttpServletRequest request, int entitiesPerPage, boolean lookAhead){
        int page = 1;
        if(request.getParameter("page") != null){
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ignored){

            }
        }
        return new Pageable(page, entitiesPerPage, lookAhead);
    }

}
