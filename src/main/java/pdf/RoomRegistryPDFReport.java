package pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletRequest;
import models.dto.RoomRegistryPdfReportDto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static utils.UrlUtils.getAbsoluteUrl;

public class RoomRegistryPDFReport {

    private final OutputStream out;

    private final List<RoomRegistryPdfReportDto> data;

    private final HttpServletRequest request;

    public static final String FONT = "/fonts/Arial.ttf";

    private PdfFont font;

    public RoomRegistryPDFReport(OutputStream out, List<RoomRegistryPdfReportDto> data, HttpServletRequest request) {
        this.out = out;
        this.data = data;
        this.request = request;
        try {
            font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buildDocument(){
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDocument = new PdfDocument(writer);
        addMetaData(pdfDocument.getDocumentInfo());
        Document document = new Document(pdfDocument);
        document.setFont(font);
        writeHeader(document);
        Table dataTable = new Table(new float[]{100f, 200f, 200f, 200f, 200f, 100f});
        document.add(dataTable);
        addColumns(dataTable);
        writeRows(dataTable);
        dataTable.complete();
        document.close();
    }

    private void addColumns(Table table){
        Cell userIdCol = new Cell();
        userIdCol.add(new Paragraph("User id"));
        Cell fistNameCol = new Cell();
        fistNameCol.add(new Paragraph("First Name"));
        Cell lastNameCol = new Cell();
        lastNameCol.add(new Paragraph("Last Name"));
        Cell checkInDateCol = new Cell();
        checkInDateCol.add(new Paragraph("Check In Date"));
        Cell checkOutDateCol = new Cell();
        checkOutDateCol.add(new Paragraph("Check Out Date"));
        Cell roomIdCol = new Cell();
        roomIdCol.add(new Paragraph("Room Id"));

        table.addCell(userIdCol);
        table.addCell(fistNameCol);
        table.addCell(lastNameCol);
        table.addCell(checkInDateCol);
        table.addCell(checkOutDateCol);
        table.addCell(roomIdCol);
    }


    private void writeRows(Table table){
        data.forEach(model -> {
            Cell userId = new Cell().add(new Paragraph(String.valueOf(model.getUserId())));
            Cell firstName = new Cell().add(new Paragraph(model.getFirstName()));
            Cell lastName = new Cell().add(new Paragraph(model.getLastName()));
            Cell checkInDate = new Cell().add(new Paragraph(model.getCheckInDate().toString()));
            Cell checkOutDate = new Cell().add(new Paragraph(model.getCheckOutDate().toString()));
            Link roomIdHref = new Link(String.valueOf(model.getRoomId()),
                    PdfAction.createURI(getAbsoluteUrl("/room?id=" + model.getRoomId(), request)));
            Paragraph roomIdParagraph = new Paragraph(roomIdHref.setFontColor(ColorConstants.BLUE).setUnderline());
            Cell roomId = new Cell().add(roomIdParagraph);
            table.addCell(userId).addCell(firstName).addCell(lastName).addCell(checkInDate).addCell(checkOutDate).addCell(roomId);
        });
    }

    private void addMetaData(PdfDocumentInfo info){
        info.setTitle("Room Registry Report");
        info.setCreator("Epam Hotel");
        info.setAuthor("Epam Hotel");
    }

    private void writeHeader(Document document){
        Paragraph header = new Paragraph("Room Registry Report");
        header.setTextAlignment(TextAlignment.CENTER);
        document.add(header);
    }
}
