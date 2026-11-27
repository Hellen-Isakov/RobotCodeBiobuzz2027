package org.firstinspires.ftc.teamcode.Data;

public enum Alliance {
    BLUE("BLUE SCORING",1),
    RED("RED SCORING", 0);

    private String className;
    private int pipeline;
    Alliance(String className, int pipeline){
        this.className = className;
        this.pipeline = pipeline;
    }

    public String getClassName(){
        return className;
    }

    public int getPipeline() {
        return pipeline;
    }
}
