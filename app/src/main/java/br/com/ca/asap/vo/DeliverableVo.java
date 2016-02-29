package br.com.ca.asap.vo;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DeliverableVo
 *
 * Represents the data of a deliverable.
 * Note that the information about the late state is calculated based on the deliverable due date and current date.
 *
 */
public class DeliverableVo {

    private String deliverable_id;
    private String initiative_id_fk;
    private String deliverable_title;
    private String deliverable_description;
    private String deliverable_comments;
    private String deliverable_status;
    private String deliverable_due_date;
    private String deliverable_responsible;
    private String deliverable_rating;
    private String deliverable_isLate;

    /**
     * Constructor
     *
     * @param deliverable_id
     * @param initiative_id_fk
     * @param deliverable_title
     * @param deliverable_description
     * @param deliverable_comments
     * @param deliverable_status
     * @param deliverable_due_date
     * @param deliverable_responsible
     * @param deliverable_rating
     */
    public DeliverableVo(String deliverable_id,
                         String initiative_id_fk,
                         String deliverable_title,
                         String deliverable_description,
                         String deliverable_comments,
                         String deliverable_status,
                         String deliverable_due_date,
                         String deliverable_responsible,
                         String deliverable_rating) {
        this.deliverable_id = deliverable_id;
        this.initiative_id_fk = initiative_id_fk;
        this.deliverable_title = deliverable_title;
        this.deliverable_description = deliverable_description;
        this.deliverable_comments = deliverable_comments;
        this.deliverable_due_date = deliverable_due_date;
        this.deliverable_responsible = deliverable_responsible;
        this.deliverable_status = deliverable_status;
        this.deliverable_rating = deliverable_rating;

        if (isLate(deliverable_due_date)){
            this.deliverable_isLate = "true";
        } else {
            this.deliverable_isLate = "false";
        }
    }

    /**
     * Constructor
     *
     * Creates with the isLate information informed
     *
     * @param deliverable_id
     * @param initiative_id_fk
     * @param deliverable_title
     * @param deliverable_description
     * @param deliverable_comments
     * @param deliverable_status
     * @param deliverable_due_date
     * @param deliverable_responsible
     * @param deliverable_rating
     * @param deliverable_isLate
     */
    public DeliverableVo(String deliverable_id,
                         String initiative_id_fk,
                         String deliverable_title,
                         String deliverable_description,
                         String deliverable_comments,
                         String deliverable_status,
                         String deliverable_due_date,
                         String deliverable_responsible,
                         String deliverable_rating,
                         String deliverable_isLate) {
        this.deliverable_id = deliverable_id;
        this.initiative_id_fk = initiative_id_fk;
        this.deliverable_title = deliverable_title;
        this.deliverable_description = deliverable_description;
        this.deliverable_comments = deliverable_comments;
        this.deliverable_due_date = deliverable_due_date;
        this.deliverable_responsible = deliverable_responsible;
        this.deliverable_status = deliverable_status;
        this.deliverable_rating = deliverable_rating;
        this.deliverable_isLate = deliverable_isLate;
    }

    /**
     * Calculate if is late based on the deliverable date and current date
     *
     * @param dueDateStr
     * @return
     *
     */
    private Boolean isLate(String dueDateStr){

        Date dueDate = null;
        Date currentDate = new Date();
        Date yesterday = null;
        //SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");

        try {
            dueDate = ft.parse(dueDateStr);
        } catch (ParseException e) {
            Log.d("DeliverableVo", "Unparseable date");
        }

        //Yesterday
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -1); //minus number would decrement the days
        yesterday = cal.getTime();

        if(yesterday.compareTo(dueDate) > 0){
            return true;
        }

        return false;
    }

    /**
     * Getter/ Setter methods
     *
     **/

    public String getDeliverable_id() {
        return deliverable_id;
    }

    public void setDeliverable_id(String deliverable_id) {
        this.deliverable_id = deliverable_id;
    }

    public String getInitiative_id_fk() {
        return initiative_id_fk;
    }

    public void setInitiative_id_fk(String initiative_id_fk) {
        this.initiative_id_fk = initiative_id_fk;
    }

    public String getDeliverable_title() {
        return deliverable_title;
    }

    public void setDeliverable_title(String deliverable_title) {
        this.deliverable_title = deliverable_title;
    }

    public String getDeliverable_description() {
        return deliverable_description;
    }

    public void setDeliverable_description(String deliverable_description) {
        this.deliverable_description = deliverable_description;
    }

    public String getDeliverable_comments() {
        return deliverable_comments;
    }

    public void setDeliverable_comments(String deliverable_comments) {
        this.deliverable_comments = deliverable_comments;
    }

    public String getDeliverable_status() {
        return deliverable_status;
    }

    public void setDeliverable_status(String deliverable_status) {
        this.deliverable_status = deliverable_status;
    }

    public String getDeliverable_due_date() {
        return deliverable_due_date;
    }

    public void setDeliverable_due_date(String deliverable_due_date) {
        this.deliverable_due_date = deliverable_due_date;
    }

    public String getDeliverable_responsible() {
        return deliverable_responsible;
    }

    public void setDeliverable_responsible(String deliverable_responsible) {
        this.deliverable_responsible = deliverable_responsible;
    }

    public String getDeliverable_rating() {
        return deliverable_rating;
    }

    public void setDeliverable_rating(String deliverable_rating) {
        this.deliverable_rating = deliverable_rating;
    }

    public String getDeliverable_isLate() {
        return deliverable_isLate;
    }

    public void setDeliverable_isLate(String deliverable_isLate) {
        this.deliverable_isLate = deliverable_isLate;
    }
}
