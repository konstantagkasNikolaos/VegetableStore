package vegetableStore.vService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import vegetableStore.DAO.Vegetable;
import vegetableStore.tools.FileTools;
import vegetableStore.tools.MyLogger;

@Component
public class PdfService extends MyLogger {

	@Autowired
	VegetableService service;

	@Value(value = "${PDFHeaderTitles}")
	List<String> headerTitles;

	@Value(value = "${fileName}")
	String pdfFileName;

	public void sendResponse(HttpServletResponse response) {

		Path file = Paths.get(pdfFileName);

		if (Files.exists(file)) {

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);

			try {

				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();

			} catch (IOException ex) {

				log.info("exception thrown in sentResponse");
				ex.printStackTrace();
			}
		}

	}

	public void getPDF() {

		try {

			log.info("ENTERING getPDF");

			/* check if file exists and if does delete it */

			FileTools fileTools = new FileTools();

			if (fileTools.checkIfFileExists(pdfFileName)) {

				log.info("file " + pdfFileName + " exists. Deleting");
				fileTools.deleteFile(pdfFileName);
			}

			/* make and open document */
			
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
			document.open();

			/* make paragraphs */

			Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Paragraph paragraphTitle = new Paragraph("Vegetable Store's Products List", fontTitle);
			paragraphTitle.setSpacingAfter(32f);
			Paragraph paragraph2 = new Paragraph("");

			document.add(paragraphTitle);
			document.add(paragraph2);

			/* get list with vegetables */

			List<Vegetable> vegetables = service.getVegetables();

			/* make PDF table */

			PdfPTable table = new PdfPTable(2);

			addTableHeader(table);
			addRows(table, vegetables);

			document.add(table);
			document.close();

			log.info("PFD made successfully");

		} catch (FileNotFoundException e) {

			log.error(" file not found " + pdfFileName);
			e.printStackTrace();
		} catch (DocumentException e) {
			log.error("DocumentException");
			e.printStackTrace();
		}

		log.info("EXITING getPDF");

	}

	private void addTableHeader(PdfPTable table) {

		int headerSize = headerTitles.size();

		headerTitles.forEach(columnTitle -> {

			PdfPCell header = new PdfPCell();

			header.setBackgroundColor(BaseColor.GREEN);
			header.setBorderWidth(headerSize);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setPhrase(new Phrase(columnTitle));

			table.addCell(header);
		});

		log.info("table header added with header size " + headerSize);

	}

	private void addRows(PdfPTable table, List<Vegetable> vegetables) {

		vegetables.forEach(item -> {

			/* create cell phrase and set text alignment */

			PdfPCell cellId = new PdfPCell(new Phrase(Long.toString(item.getId())));
			cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellName = new PdfPCell(new Phrase(item.getName()));
			cellName.setHorizontalAlignment(Element.ALIGN_CENTER);

			/* add cell to table */

			table.addCell(cellId);
			table.addCell(cellName);

			log.info("added row for vegetable with id=" + item.getId());
		});

	}

}
