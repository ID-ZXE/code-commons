package com.github.zk.discovery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hangs.zhang
 * @date 2020/04/05 22:58
 * *****************
 * function:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDetails {

    private String listenAddress;

    private int listenPort;

    private String interfaceName;

}
