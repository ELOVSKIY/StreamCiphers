package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import Ciphers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Controller {
    private byte[] fileBytes;
    private StreamEncipher cipher;
    private String filePath = null;

    @FXML
    TextArea plainArea;

    @FXML
    TextArea cipherArea;

    @FXML
    Text filePathText;

    @FXML
    ComboBox<String> cipherType;

    @FXML
    TextField field1;

    @FXML
    TextField field2;


    @FXML
    TextField field3;

    @FXML
    TextField field4;

    @FXML
    void choseFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(filePathText.getScene().getWindow());
        if (file != null) {
            filePath = file.getPath();
            filePathText.setText(file.getName());
        }
    }

    @FXML
    void onEncipherClick() {
        if (testFile() && setCipher()) {
            readFile();
            plainArea.setText(UtilsKt.parseByteArrayToBitString(fileBytes));
            fileBytes = cipher.encode(fileBytes);
            cipherArea.setText(UtilsKt.parseByteArrayToBitString(fileBytes));
            writeFile();
        }
    }

    @FXML
    void onDecipherClick() {
        if (testFile() && setCipher()) {
            readFile();
            plainArea.setText(UtilsKt.parseByteArrayToBitString(fileBytes));
            fileBytes = cipher.decode(fileBytes);
            cipherArea.setText(UtilsKt.parseByteArrayToBitString(fileBytes));
            writeFile();
        }
    }

    private boolean testFile() {
        if (filePath == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chose file for correct work.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean setCipher() {
        if (cipherType.getValue() != null) {
            if (cipherType.getValue().equals("LFSR")) {
                if (checkField(field1)) {
                    cipher = new LSFR(field1.getText());
                    return true;
                }
            }
            if (cipherType.getValue().equals("Geffe")) {
                if (checkField(field1) && checkField(field2) && checkField(field3)) {
                    cipher = new Geffe(field1.getText(), field2.getText(), field3.getText());
                    field4.setText(((Geffe) cipher).getExample());
                    return true;
                }
            }
            if (cipherType.getValue().equals("RC4")) {
                byte[] bytes = getBytesFromKey();
                if (bytes != null) {
                    cipher = new RC4(bytes);
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Incorrect key.");
                    alert.showAndWait();
                    return false;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Incorrect data.");
        alert.showAndWait();
        return false;
    }

    boolean checkField(TextField field) {
        String register = field.getText();
        if (register.length() != 29)
            return false;
        for (int i = 0; i < register.length(); i++) {
            if (register.charAt(i) != '1' && register.charAt(i) != '0')
                return false;
        }
        return true;
    }

    private void readFile() {
        try {
            fileBytes = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error with reading file.");
            alert.showAndWait();
        }
    }

    private void writeFile() {
        try (FileOutputStream writer = new FileOutputStream(filePath)) {
            writer.write(fileBytes);
            writer.flush();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error with writing file.");
            alert.showAndWait();
        }
    }

    private byte[] getBytesFromKey() {
        String key = field1.getText();
        String[] strBytes = key.split(" ");
        byte[] bts = new byte[strBytes.length];
        try {
            for (int i = 0; i < bts.length; i++) {
                bts[i] = Byte.parseByte(strBytes[i]);
            }
            return bts;
        } catch (Exception e) {
            return null;
        }
    }
}
