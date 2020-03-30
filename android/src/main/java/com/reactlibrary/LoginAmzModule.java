
package com.reactlibrary;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeMap;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizationManager;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeRequest;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;
import com.amazon.identity.auth.device.api.authorization.ProfileScope;
import com.amazon.identity.auth.device.api.authorization.Scope;
import com.amazon.identity.auth.device.api.authorization.User;
import com.amazon.identity.auth.device.api.workflow.RequestContext;


import java.util.Map;
import java.util.HashMap;

import androidx.annotation.Nullable;

public class LoginAmzModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private RequestContext requestContext;
    private AuthorizeListener mAuthorizeListener;

    public LoginAmzModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        requestContext = RequestContext.create(reactContext);
    }

    @Override
    public String getName() {
        return "LoginAmz";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("EXAMPLE_CONSTANT", "example");

        return constants;
    }



    @ReactMethod
    public void loginAMZ() {
        logOutAMZ();
        //
        AuthorizationManager.authorize(
                new AuthorizeRequest.Builder(requestContext)
                        .addScopes(ProfileScope.profile(), ProfileScope.postalCode())
                        .build()
        );
        //
        requestContext.registerListener(new AuthorizeListener() {
            @Override
            public void onSuccess(AuthorizeResult authorizeResult) {
                if (authorizeResult.getAccessToken()!=null){
                    final WritableMap params = new WritableNativeMap();
                    params.putString("token", authorizeResult.getAccessToken());
                    params.putString("status", "Ok");
                    sendEvent(reactContext,"LOGINAMZ",params);
                } else {
                    final WritableMap params = new WritableNativeMap();
                    params.putString("status", "Error");
                    params.putString("token", "");
                    sendEvent(reactContext,"LOGINAMZ",params);
//                    fetchUserProfile();
                }
            }

            @Override
            public void onError(AuthError authError) {
                final WritableMap params = new WritableNativeMap();
                params.putString("token", "");
                params.putString("status", "Error");
                sendEvent(reactContext,"LOGINAMZ",params);
            }

            @Override
            public void onCancel(AuthCancellation authCancellation) {
                final WritableMap params = new WritableNativeMap();
                params.putString("token", "");
                params.putString("status", "Cancel");
                sendEvent(reactContext,"LOGINAMZ",params);
            }
        });
    };

    @ReactMethod
    public void exampleMethod() {

    }
    @ReactMethod
    public void logOutAMZ() {
        AuthorizationManager.signOut(reactContext, new Listener<Void, AuthError>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onError(AuthError authError) {

            }
        });
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}