/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.events;

import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.dto.Dto;
import se.backede.jeconomix.event.events.fields.ImportSummaryValues;
import se.backede.jeconomix.event.events.fields.ProgressEventValues;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class Events {

    private static final Events INSTANCE = new Events();
    private static final Dto progressDto = new Dto(ProgressEventValues.class);

    protected Events() {
    }

    public static final Events getInstance() {
        return INSTANCE;
    }

    public void fireProgressMaxValueEvent(Integer value) {
        progressDto.set(ProgressEventValues.MAX_VALUE, value);
        EventController.getInstance().notifyObservers(ProgressEvent.SET_MAX_VALUE, progressDto);
    }

    public void fireProgressIncreaseValueEvent(Integer value, String ItemName) {
        progressDto.set(ProgressEventValues.INCREASE_VALUE, value);
        progressDto.set(ProgressEventValues.ITEM_NAME, ItemName);
        EventController.getInstance().notifyObservers(ProgressEvent.INCREASE, progressDto);
    }

    public void fireProgressDoneEvent(int total, int duplicate, int invalid) {
        Dto dto = new Dto(ImportSummaryValues.class);
        dto.set(ImportSummaryValues.TOTAL_RECORDS, total);
        dto.set(ImportSummaryValues.DUPLICATE_RECORDS, duplicate);
        dto.set(ImportSummaryValues.INVALID_RECORDS, invalid);
        EventController.getInstance().notifyObservers(ProgressEvent.DONE, dto);
    }

    public void fireProgressDoneEvent() {
        EventController.getInstance().notifyObservers(ProgressEvent.DONE, null);
    }

    public void fireErrorEvent() {
        EventController.getInstance().notifyObservers(ProgressEvent.ERROR, null);
    }

}
