import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author Ville Kärkkänen
 */

public class Bill {
    httpController http = new httpController();

    /**
     * Luo halutun solun tekstillä
     * @param text Stringi
     * @return solu
     */
    private static PdfPCell createCellB(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBorder(14);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    /**
     * luo PDF-solun halutulla tekstillä ilman rajaa
     * @param text Stringi
     * @return cell
     */
    private static PdfPCell createCellNB(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBorder(0);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    /**
     * Metodi pdf:n luomiselle
     * @param varaus_ID Varaus_ID
     * @param toimitustapa paperinen vai sähköinen toimitustapa (String)
     */
    public void CreatePDF(String varaus_ID, String toimitustapa) {

        //Laskuttajan tiedot
        String bill_company = "Village People Oy";
        String bill_adress = "Karjalankatu 5";
        String bill_adress_citycode = "80200";
        String bill_adress_city = "Joensuu";

        String sql = String.format("SELECT * FROM Asiakas INNER JOIN Varaus ON Asiakas.Asiakas_ID = Varaus.asiakas_ID " +
                "WHERE Varaus_ID = \"%s\"", varaus_ID);
        String[][] data = null;
        try {
            data = http.runSQL(sql);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j <data[i].length; j++) {
                System.out.println(i + " " + j + " " + data[i][j]);
            }
        }

        //Maksajan tiedot
        String m_etunimi = data[0][1];
        String m_sukunimi = data[0][2];
        String m_lahiosoite = data[0][5];
        String m_postinumero = data[0][6];
        String m_paikkakunta = data[0][7];
        String m_email = data[0][4];

        String sqlLasku = String.format("SELECT * FROM Lasku WHERE Varaus_ID = \"%s\"", varaus_ID);
        String[][] dataLasku = null;
        System.out.println(sqlLasku);
        try {
            dataLasku = http.runSQL(sqlLasku);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (int i = 0; i < dataLasku.length; i++) {
            for (int j = 0; j <dataLasku[i].length; j++) {
                System.out.println(i + " " + j + " " + dataLasku[i][j]);
            }
        }

        String bill_number = dataLasku[0][0];           //Laskun numero
        String m_lasku_pvm = dataLasku[0][4];          //Laskun pvm
        String m_lasku_erapaiva = dataLasku[0][3];     //Eräpäivä

        String m_maksuehto = "14 pv netto";        //Maksuehto
        String bill_ref_num = "1234";              //Viitenumero

        String m_asiakas_ID = data[0][0];         //Ostajan tilausnumero
        String m_viivastuskorko = "7,50%";        //Viivästyskorko
        String m_huomautusaika = "7 pv";          //Huomautusaika

        String bill_iban = "FI54 4684 2623 5654 94";    //IBAN
        String bill_bic = "ITELFIHH";                   //BIC
        String bill_y = "1234567-8";                    //Y-tunnus

        String m_alkupvm = "";
        String m_loppupvm = "";
        String m_Toimipiste_ID = "";


        double bill_total = Double.parseDouble(dataLasku[0][2]);         //maksettava yhteensä
        double bill_total_no_tax = bill_total * 0.80;           //arvolisävero yhteensä
        double bill_tax =  0;   //yhteensä ilman arvolisäveroa

        bill_total = Math.round(bill_total);
        bill_total_no_tax = Math.round(bill_total_no_tax);

        bill_tax = bill_total - bill_total_no_tax;
        bill_tax = Math.round(bill_tax);

        //Laskun nimen generointi
        String pdf = m_sukunimi + "_" + m_lasku_pvm + ".pdf";
        System.out.println(pdf);

        Document document1 = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document1, new FileOutputStream(pdf));
            document1.open();
            document1.add(new Paragraph(bill_company));
            //document1 on "pdf" buttonille
            PdfPCell a1 = createCellNB("Laskun numero");      //Solujen luonti
            PdfPCell a2 = createCellNB(varaus_ID);
            PdfPCell a3 = createCellNB("Laskun pvm");
            PdfPCell a4 = createCellNB(m_lasku_pvm);
            PdfPCell a5 = createCellNB("Eräpäivä");
            PdfPCell a6 = createCellNB(m_lasku_erapaiva);

            PdfPCell b1 = createCellNB("Toimituspäivä");
            PdfPCell b2 = createCellNB(m_lasku_pvm);
            PdfPCell b3 = createCellNB("Maksuehto");
            PdfPCell b4 = createCellNB(m_maksuehto);
            PdfPCell b5 = createCellNB("Viitenumero");
            PdfPCell b6 = createCellNB(varaus_ID + "-" + m_asiakas_ID);
            PdfPCell b7 = createCellNB("Toimitustapa");
            PdfPCell b8 = createCellNB(toimitustapa);
            PdfPCell b9 = createCellNB("Ostajan tilausnumero");
            PdfPCell b10 = createCellNB(m_asiakas_ID);
            PdfPCell b11 = createCellNB("Viivästyskorko");
            PdfPCell b12 = createCellNB(m_viivastuskorko);
            PdfPCell b13 = createCellNB("Huomautusaika");
            PdfPCell b14 = createCellNB(m_huomautusaika);

            PdfPCell c1 = createCellNB("Kuvaus");
            PdfPCell c2 = createCellNB("Yksikköhinta €");
            PdfPCell c3 = createCellNB("Määrä");
            PdfPCell c4 = createCellNB("ALV %");
            PdfPCell c5 = createCellNB("Yhteensä €");

            PdfPCell d1 = createCellNB("Yhteensä ilman arvonlisäveroa");
            PdfPCell d2 = createCellNB(String.valueOf(bill_total_no_tax) + "€");
            PdfPCell d3 = createCellNB("Arvonlisävero yhteensä");
            PdfPCell d4 = createCellNB(String.valueOf(bill_tax) + "€");
            PdfPCell d5 = createCellNB("Maksettava yhteensä");
            PdfPCell d6 = createCellNB(String.valueOf(bill_total) + "€");

            PdfPCell e1 = createCellB("Eräpäivä");
            PdfPCell e2 = createCellB("Viitenumero");
            PdfPCell e3 = createCellB("Yhteensä €");
            PdfPCell e4 = createCellB(m_lasku_erapaiva);
            PdfPCell e5 = createCellB(varaus_ID + "-" + m_asiakas_ID);
            PdfPCell e6 = createCellB(String.valueOf(bill_total) + "€");
            PdfPCell e7 = createCellB("IBAN");
            PdfPCell e8 = createCellB("BIC");
            PdfPCell e9 = createCellB("Y-tunnus");
            PdfPCell e10 = createCellB(bill_iban);
            PdfPCell e11 = createCellB(bill_bic);
            PdfPCell e12 = createCellB(bill_y);
            PdfPCell e13 = createCellB("Saajan tiedot");
            PdfPCell e14 = createCellB("");
            PdfPCell e15 = createCellB("");
            PdfPCell e16 = createCellB(bill_company);
            PdfPCell e17 = createCellB(bill_adress);
            PdfPCell e18 = createCellB(bill_adress_citycode + " " + bill_adress_city);
            PdfPCell e19 = createCellB("Maksajan tiedot");
            PdfPCell e20 = createCellB("");
            PdfPCell e21 = createCellB("");
            PdfPCell e22 = createCellB(m_etunimi + " " + m_sukunimi + " " + m_email);
            PdfPCell e23 = createCellB(m_lahiosoite);
            PdfPCell e24 = createCellB(m_postinumero + " " + m_paikkakunta);

            PdfPCell x1 = createCellNB("");
            PdfPCell x2 = createCellNB("");
            e1.setBorder(5);                    //Muutetaan solujen rajoja sopiviksi
            e2.setBorder(5);
            e3.setBorder(13);
            e7.setBorder(5);
            e8.setBorder(5);
            e9.setBorder(13);
            e13.setBorder(4);
            e14.setBorder(0);
            e15.setBorder(8);
            e16.setBorder(22);
            e17.setBorder(2);
            e18.setBorder(10);
            e19.setBorder(4);
            e20.setBorder(0);
            e21.setBorder(8);
            e22.setBorder(22);
            e23.setBorder(2);
            e24.setBorder(10);
            x1.setBorder(1);
            x2.setBorder(2);

            PdfPTable tableA = new PdfPTable(2);           //Taulukko A
            float[] columnsA = {1f, 1f};              //sarakkeiden jakaumat
            tableA.setWidthPercentage(40);              //taulukon leveys
            tableA.setSpacingAfter(10f);               //paljonko vli taulukon jlkeen
            tableA.setHorizontalAlignment(Element.ALIGN_RIGHT);  //Taulukon kohdistus
            tableA.setWidths(columnsA);              //listn sarakkaiden jaukama taulukkoon
            tableA.addCell(a1);                  //Listn solut taulukkoon
            tableA.addCell(a2);
            tableA.addCell(a3);
            tableA.addCell(a4);
            tableA.addCell(a5);
            tableA.addCell(a6);

            PdfPTable tableB = new PdfPTable(2);
            float[] columnsB = {1f, 1f};
            tableB.setWidthPercentage(60);
            tableB.setSpacingAfter(100f);
            tableB.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableB.setWidths(columnsB);
            tableB.addCell(b1);
            tableB.addCell(b2);
            tableB.addCell(b3);
            tableB.addCell(b4);
            tableB.addCell(b5);
            tableB.addCell(b6);
            tableB.addCell(b7);
            tableB.addCell(b8);
            tableB.addCell(b9);
            tableB.addCell(b10);
            tableB.addCell(b11);
            tableB.addCell(b12);
            tableB.addCell(b13);
            tableB.addCell(b14);

            PdfPTable tableC = new PdfPTable(5);
            float[] columnsC = {20f, 8f, 4f, 4f, 6f};
            tableC.setWidthPercentage(100);
            tableC.setSpacingAfter(10f);
            tableC.setWidths(columnsC);
            tableC.addCell(x1);
            tableC.addCell(x1);
            tableC.addCell(x1);
            tableC.addCell(x1);
            tableC.addCell(x1);
            tableC.addCell(c1);
            tableC.addCell(c2);
            tableC.addCell(c3);
            tableC.addCell(c4);
            tableC.addCell(c5);

            tableC.addCell(x2);
            tableC.addCell(x2);
            tableC.addCell(x2);
            tableC.addCell(x2);
            tableC.addCell(x2);
            PdfPTable tableD = new PdfPTable(2);
            float[] columnsD = {1f, 1f};
            tableD.setWidthPercentage(75);
            tableD.setSpacingAfter(100f);
            tableD.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableD.setWidths(columnsD);
            tableD.addCell(d1);
            tableD.addCell(d2);
            tableD.addCell(d3);
            tableD.addCell(d4);
            tableD.addCell(d5);
            tableD.addCell(d6);

            PdfPTable tableE = new PdfPTable(3);
            float[] columnsE = {10f, 4f, 6f};
            tableE.setWidthPercentage(100);
            tableE.setSpacingAfter(10f);
            tableE.setWidths(columnsE);
            tableE.addCell(e1);
            tableE.addCell(e2);
            tableE.addCell(e3);
            tableE.addCell(e4);
            tableE.addCell(e5);
            tableE.addCell(e6);
            tableE.addCell(e7);
            tableE.addCell(e8);
            tableE.addCell(e9);
            tableE.addCell(e10);
            tableE.addCell(e11);
            tableE.addCell(e12);
            tableE.addCell(e13);
            tableE.addCell(e14);
            tableE.addCell(e15);
            tableE.addCell(e16);
            tableE.addCell(e17);
            tableE.addCell(e18);
            tableE.addCell(e19);
            tableE.addCell(e20);
            tableE.addCell(e21);
            tableE.addCell(e22);
            tableE.addCell(e23);
            tableE.addCell(e24);

            document1.add(tableA);
            document1.add(tableB);
            document1.add(tableC);
            document1.add(tableD);
            document1.add(tableE);

            document1.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}