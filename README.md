# react-native-login-amz

## Installation

`$ npm install react-native-login-amz --save`

or

`$ yarn add react-native-login-amz`

(RN <0.59)`$ react-native link react-native-login-amz`

- Check [this link](https://developer.amazon.com/docs/login-with-amazon/minitoc-lwa-ios.html) for IOS and [this link](https://developer.amazon.com/docs/login-with-amazon/minitoc-lwa-android.html) for Android.
- Make sure you have done step 3

### Add your api key

#### IOS

- Add your api key to `Info.plist` ([Instruction](https://developer.amazon.com/docs/login-with-amazon/create-ios-project.html#add-api-key)).

#### Android

- Create `api_key.txt` in `android/app/src/main/assets` and put you Amazon api key here ([Instruction ](https://developer.amazon.com/docs/login-with-amazon/create-android-project.html#add-api-key)).

## Usage

```javascript
import { useEffect } from "react";
import { Text, TouchableOpacity } from "react-native";
import LoginAmz from "react-native-login-amz";
const LoginAmzEventEmitter = new NativeEventEmitter(LoginAmz);

const Screen = () => {
  useEffect(() => {
    const handler = LoginAmzEventEmitter.addListener("LOGINAMZ", data => {
      console.log("AMZ LOGIN", data);
      if (data.status === "Ok") {
        //
      }
      if (data.status === "Error") {
        //
      }
      if (data.status === "Cancel") {
        //
      }
    });
    return () => {
      handler.remove();
    };
  }, []);
  return (
    <TouchableOpacity onPress={() => LoginAmz.loginAMZ()}>
      <Text>Login</Text>
    </TouchableOpacity>
  );
};
```
