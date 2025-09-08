package co.com.bdb.automation.utilities;

public class GenerateXml {


    public static String construirXML(String xmlOriginal, String newCedula) {
        return xmlOriginal.replaceAll("<AA_NIT>.*?</AA_NIT>", "<AA_NIT>" + newCedula + "</AA_NIT>");

    }

}
