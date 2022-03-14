package commands.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.tagutils.*;
import commands.base.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.dto.RoomRegistryPdfReportDto;
import pdf.RoomRegistryPDFReport;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@WebMapping(url = "/pdf", method = RequestMethod.GET)
public class PdfTest implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
        List<RoomRegistryPdfReportDto> data = new LinkedList<>();
        data.add(new RoomRegistryPdfReportDto(1L, "Vadim", "Demb", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 2L));
        data.add(new RoomRegistryPdfReportDto(2L, "Вадим", "Демб", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 1L));
        RoomRegistryPDFReport pdfReport = new RoomRegistryPDFReport(out, data, request);
        pdfReport.buildDocument();
        return new CommandResult("", RequestDirection.VOID);
    }
}
