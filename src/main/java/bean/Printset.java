package bean;

import java.util.Date;

public class Printset {

    private Integer id;

    private String sntext;
    private String sncode;
    private String printserver;
    private String standard;
    private String templatePath;

    private Integer isUsed;

    private String doPDFPath;
    private Integer   isPrint;

    private String tempPDFName;//临时存储文件名字 不存数据库


    public String getTempPDFName() {
        return tempPDFName;
    }

    public void setTempPDFName(String tempPDFName) {
        this.tempPDFName = tempPDFName;
    }

    public String getSntext() {
        return sntext;
    }

    public void setSntext(String sntext) {
        this.sntext = sntext;
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public String getPrintserver() {
        return printserver;
    }

    public void setPrintserver(String printserver) {
        this.printserver = printserver;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getDoPDFPath() {
        return doPDFPath;
    }

    public void setDoPDFPath(String doPDFPath) {
        this.doPDFPath = doPDFPath;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }
}
