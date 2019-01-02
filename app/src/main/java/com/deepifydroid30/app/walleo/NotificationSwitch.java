package com.deepifydroid30.app.walleo;

public class NotificationSwitch {
    static boolean swtich_status = true;
    public static void setStatus(boolean b){
        swtich_status = b;
    }

    public static boolean getStatus(){
        return swtich_status;
    }
}
