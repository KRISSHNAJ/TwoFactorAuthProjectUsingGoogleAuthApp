package youtube.krishnaitprojects.customutils;

import java.io.File;
import java.io.FileOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class QRCodeGenerator {

	public static void generateQRCode(String barcodeText, String filePath) throws Exception {
		int width = 200;
		int height = 200;
		String fileType = "png";
		File qrFile = new File(filePath);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.QR_CODE, width, height);

		MatrixToImageWriter.writeToStream(bitMatrix, fileType, new FileOutputStream(qrFile));
	}

}
