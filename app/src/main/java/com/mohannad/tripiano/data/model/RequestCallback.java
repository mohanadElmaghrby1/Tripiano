package com.mohannad.tripiano.data.model;

import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessFault;

public interface RequestCallback {

    /** success is called when the user successfully login or register .. */
    void success(BackendlessUser response);

    /** error is called when some error happen*/
    void error(BackendlessFault fault);
}
