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
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletRequest;
import models.dto.RoomRegistryPdfReportDto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static utils.UrlUtils.getAbsoluteUrl;

public class RoomRegistryPDFReport {

    private final OutputStream out;

    private final List<RoomRegistryPdfReportDto> data;

    private final HttpServletRequest request;

    private Locale locale = Locale.ROOT;

    private ResourceBundle bundle;

    public static final String FONT = "/fonts/Arial.ttf";

    private PdfFont font;

    /**
     * @param out Output stream to which pdf will be written
     * @param data List of objects which will fill table in pdf
     * @param request Request from which, url will be determined, need to correctly make href to room id
     */
    public RoomRegistryPDFReport(OutputStream out, List<RoomRegistryPdfReportDto> data, HttpServletRequest request) {
        this.out = out;
        this.data = data;
        this.request = request;
    }

    /**
     * Writes document to Output Stream
     */
    public void buildDocument(){
        PdfWriter writer = new PdfWriter(out);
        try {
            font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        } catch (IOException e){
            e.printStackTrace();
        }
        bundle = ResourceBundle.getBundle("pdf", locale);
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
        userIdCol.add(new Paragraph(bundle.getString("userId")));
        Cell fistNameCol = new Cell();
        fistNameCol.add(new Paragraph(bundle.getString("firstName")));
        Cell lastNameCol = new Cell();
        lastNameCol.add(new Paragraph(bundle.getString("lastName")));
        Cell checkInDateCol = new Cell();
        checkInDateCol.add(new Paragraph(bundle.getString("checkInDate")));
        Cell checkOutDateCol = new Cell();
        checkOutDateCol.add(new Paragraph(bundle.getString("checkOutDate")));
        Cell roomIdCol = new Cell();
        roomIdCol.add(new Paragraph(bundle.getString("roomId")));
        table.addCell(userIdCol).addCell(fistNameCol).addCell(lastNameCol).addCell(checkInDateCol).addCell(checkOutDateCol).addCell(roomIdCol);
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
        Paragraph header = new Paragraph(bundle.getString("roomRegistryReport"));
        header.setTextAlignment(TextAlignment.CENTER);
        document.add(header);
    }

    public void setLocale(Locale locale ){
        this.locale = locale;
    }
}
