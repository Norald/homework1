package pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Faculty;
import model.UserFinalStatementResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Class for creating PDF statement.
 *
 * @author Vladislav Prokopenko
 */
@Service
public class CreateStatement {
    private String title;
    private String sunbject;
    private String author;
    private String creator;
    private String report;
    private String budgetPlaces;
    private String totalPlaces;
    private String fullname;
    private String idn;
    private String examResult;
    private String certificatePoint;
    private String totalResult;
    private String date;

    public CreateStatement() {
    }

    /**
     * Setting fonts that can work with cyrillic.
     * Fonts contains in resources/fonts
     */
    public String FONT_REGULAR = this.getClass().getClassLoader().getResource("fonts/HelveticaRegular.ttf").toString();
    public String FONT_BOLD = this.getClass().getClassLoader().getResource("fonts/HelveticaBold.ttf").toString();


    private static final Logger LOG = LogManager.getLogger(CreateStatement.class.getName());


    /**
     * Setting language of statement.
     *
     * @param locale - localisation: 'uk' or 'en'
     */
    public void setLanguageToLocale(String locale) {
        if (locale.equals("en")) {
            title = "PDF statement for faculty admission";
            sunbject = "PDF statement for faculty admission ";
            author = "Admin ";
            creator = "Admin ";
            report = "PDF statement for ";
            budgetPlaces = "Count of budget places ";
            totalPlaces = "Count of total places ";
            fullname = "Full Name";
            idn = "Identification number";
            examResult = "Total exam result";
            certificatePoint = "Certificate point";
            totalResult = "Total result";
            date = "Date of statement ";
        } else {
            title = "PDF звіт по вступу на факультет ";
            sunbject = "PDF звіт по вступу на факультет ";
            author = "Адмін ";
            creator = "Адмін ";
            report = "PDF звіт за факультетом: ";
            budgetPlaces = "Кількість бюджетних місць ";
            totalPlaces = "Загальна кількість місць ";
            fullname = "ПІБ";
            idn = "Ідентифікаційний номер";
            examResult = "Загальний результат по екзаменам";
            certificatePoint = "Середній бал атестату";
            totalResult = "Загальний результат";
            date = "Дата звіту ";
        }
    }


    /**
     * Creating PDF document
     *
     * @param file    name and path of file
     * @param faculty faculty
     * @param results list of users results
     * @param locale  locale
     * @return document
     */
    public Document createPDF(String file, Faculty faculty, ArrayList<UserFinalStatementResult> results, String locale) {

        setLanguageToLocale(locale);
        Document document = null;
        Font font = FontFactory.getFont(FONT_REGULAR, BaseFont.IDENTITY_H, true);
        Font fontBold = FontFactory.getFont(FONT_BOLD, BaseFont.IDENTITY_H, true);


        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document, faculty, fontBold);

            createTable(document, results, faculty, font);

            document.close();

        } catch (FileNotFoundException e) {


            LOG.error(e.getMessage(), e);
        } catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        return document;

    }


    /**
     * Adding metadata
     *
     * @param document document
     */
    private void addMetaData(Document document) {
        document.addTitle(title);
        document.addSubject(sunbject);
        document.addAuthor(author);
        document.addCreator(creator);
    }

    /**
     * Adding tittle page to document.
     *
     * @param document document
     * @param faculty  faculty
     * @param font     font for document
     * @throws DocumentException
     */
    private void addTitlePage(Document document, Faculty faculty, Font font)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph(sunbject, font));

        creteEmptyLine(preface, 1);
        preface.add(new Paragraph(date + new java.sql.Date(System.currentTimeMillis()), font));
        preface.add(new Paragraph(report
                + faculty.getName(), font));

        preface.add(new Paragraph(budgetPlaces
                + faculty.getBudgetAmount(), font));
        preface.add(new Paragraph(totalPlaces
                + faculty.getTotalAmount(), font));
        document.add(preface);
    }

    /**
     * Creating empty line in document
     *
     * @param paragraph
     * @param number
     */
    private void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Creating table in document
     *
     * @param document
     * @param results
     * @param faculty
     * @param font     font for table
     * @throws DocumentException
     */
    private void createTable(Document document, ArrayList<UserFinalStatementResult> results, Faculty faculty, Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(6);


        PdfPCell c1 = new PdfPCell(new Phrase("#", font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(fullname, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(idn, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(examResult, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(certificatePoint, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(totalResult, font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        //showing only total amount of faculty places
        if (results.size() <= faculty.getTotalAmount()) {
            for (int i = 0; i < results.size(); i++) {
                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(String.valueOf(i + 1));
                table.addCell(new Phrase(results.get(i).getFullname(), font));
                table.addCell(String.valueOf(results.get(i).getIdn()));
                table.addCell(String.valueOf(results.get(i).getTotalExamResult()));
                table.addCell(String.valueOf(results.get(i).getCertificatePoint()));
                table.addCell(String.valueOf(results.get(i).getTotalResult()));
            }

        } else {
            for (int i = 0; i < faculty.getTotalAmount(); i++) {
                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(String.valueOf(i + 1));
                table.addCell(new Phrase(results.get(i).getFullname(), font));
                table.addCell(String.valueOf(results.get(i).getIdn()));
                table.addCell(String.valueOf(results.get(i).getTotalExamResult()));
                table.addCell(String.valueOf(results.get(i).getCertificatePoint()));
                table.addCell(String.valueOf(results.get(i).getTotalResult()));
            }
        }

        document.add(table);
    }
}
