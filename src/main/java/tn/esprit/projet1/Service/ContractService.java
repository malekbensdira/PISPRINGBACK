import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class ContractService {

    public byte[] generateContract(String clientName, String propertyDetails, double price) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Contrat d'Achat Immobilier"));
            document.add(new Paragraph("Client: " + clientName));
            document.add(new Paragraph("Bien immobilier: " + propertyDetails));
            document.add(new Paragraph("Prix: " + price + " €"));
            document.add(new Paragraph("Signature: _____________"));

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
