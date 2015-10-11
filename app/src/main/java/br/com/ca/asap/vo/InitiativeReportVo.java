package br.com.ca.asap.vo;

/**
 * InitiativeReportVo
 *
 */
public class InitiativeReportVo {

    private String initiativeId = null;
    private double totalNum = 0;
    private double onTimeNum = 0;
    private double lateNum = 0;
    private double lastDayNum = 0;

    public InitiativeReportVo(){
    }

    public InitiativeReportVo(String initiativeId,
            double totalNum,
            double onTimeNum,
            double lateNum,
            double lastDayNum){

        this.initiativeId = initiativeId;
        this.totalNum = totalNum;
        this.onTimeNum = onTimeNum;
        this.lateNum = lateNum;
        this.lastDayNum = lastDayNum;
    }

    public String getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(String initiativeId) {
        this.initiativeId = initiativeId;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public double getOnTimeNum() {
        return onTimeNum;
    }

    public void setOnTimeNum(double onTimeNum) {
        this.onTimeNum = onTimeNum;
    }

    public double getLateNum() {
        return lateNum;
    }

    public void setLateNum(double lateNum) {
        this.lateNum = lateNum;
    }

    public double getLastDayNum() {
        return lastDayNum;
    }

    public void setLastDayNum(double lastDayNum) {
        this.lastDayNum = lastDayNum;
    }




}
