package org.firstinspires.ftc.teamcode.Robot;

public enum Alliance {
    BLUE("BLUE SCORING"),
    RED("RED SCORING");

    private String className;
    Alliance(String className){
        this.className = className;
    }

    public String getClassName(){
        return className;
    }
}
