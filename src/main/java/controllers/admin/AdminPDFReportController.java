package controllers.admin;

import forms.ReportConfigurationForm;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.base.pagination.Pageable;
import models.dto.RoomRegistryPdfReportDto;
import pdf.RoomRegistryPDFReport;
import service.AdminRoomsService;
import web.base.*;
import web.base.security.ManagerOnly;
import web.base.security.Security;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class AdminPDFReportController {

    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();

    @WebMapping(url = "/admin/report", method = RequestMethod.GET)
    public CommandResult configureReport(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        return new CommandResult("/admin/configure-report.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/admin/report/pdf", method = RequestMethod.POST)
    public CommandResult generateReport(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        ReportConfigurationForm form = new ReportConfigurationForm();
        form.mapRequestToForm(request);
        Pageable pageable = new Pageable(form.getPage(), form.getEntitiesPerPage());
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
        List<RoomRegistryPdfReportDto> data = roomsService.findDataForRoomRegistryReport(form, pageable);
        RoomRegistryPDFReport pdfReport = new RoomRegistryPDFReport(out, data, request);
        pdfReport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        pdfReport.buildDocument();
        return new CommandResult("", RequestDirection.VOID);
    }
}
