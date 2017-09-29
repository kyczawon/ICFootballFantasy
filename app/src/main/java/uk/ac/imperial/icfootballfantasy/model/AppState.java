package uk.ac.imperial.icfootballfantasy.model;

/**
 * Created by leszek on 9/29/17.
 */

public class AppState {
    private boolean isEditable;
    private boolean isTransfer;
    private String nextEditable;
    private String saveBy;
    private static AppState sAppState;

    public static AppState get() {
        if (sAppState == null) {
            sAppState   = new AppState();
        }
        return sAppState;
    }

    public static AppState get(boolean isEditable, boolean isTransfer, String nextEditable, String saveBy) {
        if (sAppState == null) {
            sAppState = new AppState(isEditable, isTransfer, nextEditable,saveBy);
        }
        return sAppState;
    }
    private AppState() {
    }

    private AppState(boolean isEditable, boolean isTransfer, String nextEditable, String saveBy) {
        this.isEditable = isEditable;
        this.isTransfer = isTransfer;
        this.nextEditable = nextEditable;
        this.saveBy = saveBy;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public String getNextEditable() {
        return nextEditable;
    }

    public String getSaveBy() {
        return saveBy;
    }
}
