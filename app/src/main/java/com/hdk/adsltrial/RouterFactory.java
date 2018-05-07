package com.hdk.adsltrial;

import com.hdk.adsltrial.router.RouterCommands;

/**
 * Created by serag on 5/7/18.
 */

public class RouterFactory {

    public static RouterCommands getInstance() {
        return new RouterCommands("192.168.1.1", "!!Huawei", "@HuaweiHgw");
    }
}
