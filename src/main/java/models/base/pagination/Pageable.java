package models.base.pagination;

import jakarta.servlet.http.HttpServletRequest;

public class Pageable {

    private final int page;
    private int entitiesPerPage;
    private boolean lookAhead = false;

    /**
     * @param page current page, must be >= 1, offset in sql
     * @param entitiesPerPage entities per page must be > 0, limit in sql
     */
    public Pageable(int page, int entitiesPerPage){
        this.page = page;
        this.entitiesPerPage = entitiesPerPage;
    }

    /**
     * @param page current page, must be >= 1, offset in sql
     * @param entitiesPerPage entities per page must be > 0, limit in sql
     * @param lookAhead if this is true, entitiesPerPage will be +1, this is needed to correctly determine whether list of objects have next page in sql
     */
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
            offset -= page - 1;
        }
        return sql.concat(String.format(" limit %d offset %d", entitiesPerPage, offset));
    }

    /**
     * Static Factory that creates {@link Pageable} from request, it searches for page parameter in request
     * @param request Request from which pageable will be created
     * @return created Pageable
     */
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
