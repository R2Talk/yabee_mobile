package br.com.ca.blueocean.vo;

import java.io.Serializable;
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
 * BEWARE: This Vo Class implements Serializable becouse this is mandatory to be returned from a startActivityForResult call
 *
 * @author Rodrigo Carvalho
 */
public class DeliverableVo implements Serializable{

    private String iddeliverable;
    private String idinitiative;
    private String title;
    private String description;
    private String comments;
    private String status;
    private String duedate;
    private String idresponsibleuser;
    private String rating;
    private String isPriority;
    private String priorityComment;
    private String prioritizedBy;
    private String deliverableValue;
    private String code;
    private String currentusername;
    private String deliverable_isLate;

    //
    // CONSTRUCTOR
    //

    public DeliverableVo(){

    }

    /**
     * Constructor
     *
     * @param iddeliverable
     * @param idinitiative
     * @param title
     * @param description
     * @param comments
     * @param status
     * @param duedate
     * @param idresponsibleuser
     * @param rating
     * @param isPriority
     * @param priorityComment
     * @param prioritizedBy
     * @param deliverableValue
     */
    public DeliverableVo(String iddeliverable,
                         String idinitiative,
                         String title,
                         String description,
                         String comments,
                         String status,
                         String duedate,
                         String idresponsibleuser,
                         String rating,
                         String isPriority,
                         String priorityComment,
                         String prioritizedBy,
                         String deliverableValue,
                         String code,
                         String currentusername
                         ) {
        this.iddeliverable = iddeliverable;
        this.idinitiative = idinitiative;
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.status = status;
        this.duedate = duedate;
        this.idresponsibleuser = idresponsibleuser;
        this.rating = rating;
        this.isPriority = isPriority;
        this.priorityComment = priorityComment;
        this.prioritizedBy = prioritizedBy;
        this.deliverableValue = deliverableValue;
        this.code = code;
        this.currentusername = currentusername;

        if (isLate(duedate)){
            this.deliverable_isLate = "true";
        } else {
            this.deliverable_isLate = "false";
        }
    }

    /**
     * Constructor
     *
     * full version, with isLate informed.
     *
     * @param iddeliverable
     * @param idinitiative
     * @param title
     * @param description
     * @param comments
     * @param status
     * @param duedate
     * @param idresponsibleuser
     * @param rating
     * @param isPriority
     * @param priorityComment
     * @param prioritizedBy
     * @param deliverableValue
     * @param deliverable_isLate
     */
    public DeliverableVo(String iddeliverable,
                         String idinitiative,
                         String title,
                         String description,
                         String comments,
                         String status,
                         String duedate,
                         String idresponsibleuser,
                         String rating,
                         String isPriority,
                         String priorityComment,
                         String prioritizedBy,
                         String deliverableValue,
                         String code,
                         String currentusername,
                         String deliverable_isLate) {
        this.iddeliverable = iddeliverable;
        this.idinitiative = idinitiative;
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.status = status;
        this.duedate = duedate;
        this.idresponsibleuser = idresponsibleuser;
        this.rating = rating;
        this.isPriority = isPriority;
        this.priorityComment = priorityComment;
        this.prioritizedBy = prioritizedBy;
        this.deliverableValue = deliverableValue;
        this.code = code;
        this.currentusername = currentusername;
        this.deliverable_isLate = deliverable_isLate;
    }


    //
    // GETTER AND SETTER
    //

    public String getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(String isPriority) {
        this.isPriority = isPriority;
    }

    public String getIdinitiative() {
        return idinitiative;
    }

    public void setIdinitiative(String idinitiative) {
        this.idinitiative = idinitiative;
    }

    public String getIddeliverable() {
        return iddeliverable;
    }

    public void setIddeliverable(String iddeliverable) {
        this.iddeliverable = iddeliverable;
    }

    public String getIdInitiative() {
        return idinitiative;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getIdresponsibleuser() {
        return idresponsibleuser;
    }

    public void setIdresponsibleuser(String idresponsibleuser) {
        this.idresponsibleuser = idresponsibleuser;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliverable_isLate() {
        return deliverable_isLate;
    }

    public void setDeliverable_isLate(String deliverable_isLate) {
        this.deliverable_isLate = deliverable_isLate;
    }

    public String getPriorityComment() {
        return priorityComment;
    }

    public void setPriorityComment(String priorityComment) {
        this.priorityComment = priorityComment;
    }

    public String getPrioritizedBy() {
        return prioritizedBy;
    }

    public void setPrioritizedBy(String prioritizedBy) {
        this.prioritizedBy = prioritizedBy;
    }

    public String getDeliverableValue() {
        return deliverableValue;
    }

    public void setDeliverableValue(String deliverableValue) {
        this.deliverableValue = deliverableValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrentusername() {
        return currentusername;
    }

    public void setCurrentusername(String currentusername) {
        this.currentusername = currentusername;
    }

    //
    // HELPER METHOD
    //

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
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");

        try {
            dueDate = ft.parse(dueDateStr);
        } catch (ParseException e) {
            //TODO: needs exception treatment
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
}
