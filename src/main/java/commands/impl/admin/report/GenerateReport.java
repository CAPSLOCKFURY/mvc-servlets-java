package commands.impl.admin.report;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import forms.ReportConfigurationForm;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.base.pagination.Pageable;
import models.dto.RoomRegistryPdfReportDto;
import pdf.RoomRegistryPDFReport;
import service.RoomsService;

import java.io.IOException;
import java.util.List;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/report/pdf", method = RequestMethod.POST)
public class GenerateReport implements Command {

    private final RoomsService roomsService = RoomsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
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
        pdfReport.buildDocument();
        return new CommandResult("", RequestDirection.VOID);
    }
}
