package org.fp024.study.spring5recipes.court.web.view;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fp024.study.spring5recipes.court.domain.Reservation;
import org.springframework.stereotype.Component;

@Component
public class PdfReservationSummary extends CustomAbstractPdfView {

  @Override
  protected void buildPdfDocument(
      Map<String, Object> model,
      Document document,
      PdfWriter writer,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    @SuppressWarnings("unchecked")
    List<Reservation> reservations = (List<Reservation>) model.get("reservations");

    PdfPTable table = new PdfPTable(5);

    addTableHeader(table);
    for (Reservation reservation : reservations) {
      addContent(table, reservation);
    }
    document.add(table);
  }

  private void addContent(PdfPTable table, Reservation reservation) throws BadElementException {
    table.addCell(reservation.getCourtName());
    table.addCell(reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    table.addCell(Integer.toString(reservation.getHour()));
    table.addCell(reservation.getPlayer().getName());
    table.addCell(reservation.getPlayer().getPhone());
  }

  private void addTableHeader(PdfPTable table) throws BadElementException {
    table.addCell("Court Name");
    table.addCell("Date");
    table.addCell("Hour");
    table.addCell("Player Name");
    table.addCell("Player Phone");
  }
}
