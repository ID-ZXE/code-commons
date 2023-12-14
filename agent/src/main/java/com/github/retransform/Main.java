package com.github.retransform;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws AgentLoadException, IOException,
            AgentInitializationException, AttachNotSupportedException {
        VirtualMachine virtualMachine = VirtualMachine.attach("40844");
        try {
            virtualMachine.loadAgent("/Users/zhanghang/Code/java/github/code-commons/agent/target/agent-1.0.0-jar-with-dependencies.jar");
        } finally {
            virtualMachine.detach();
        }
    }

}
